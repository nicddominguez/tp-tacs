import {
    AuthApi,
    ProvinciasApi,
    GoogleAuthModel,
    NuevoJWTModel,
    UsuariosApi,
    AdminApi,
    PartidasApi,
    EstadoDeJuegoModel,
    CrearPartidaBody,
    PartidaModel,
    MunicipioEnJuegoModel,
    MoverGauchosBody,
    AtacarMunicipioBody,
    SimularAtacarMunicipioBody,
    RefreshAccessTokenBody, ActualizarEstadoPartida
} from './api';

export class BaseWololoApiClient {

    static ACCESS_TOKEN_KEY: 'ACCESS_TOKEN' = 'ACCESS_TOKEN'
    static REFRESH_TOKEN_KEY: 'REFRESH_TOKEN_KEY' = 'REFRESH_TOKEN_KEY'

    public getAccessToken(): string | undefined {
        return window.localStorage.getItem(BaseWololoApiClient.ACCESS_TOKEN_KEY) || undefined;
    }

    protected getRefreshToken(): string | undefined {
        return window.localStorage.getItem(BaseWololoApiClient.REFRESH_TOKEN_KEY) || undefined;
    }

    protected storeAccessToken(token: string) {
        window.localStorage.setItem(BaseWololoApiClient.ACCESS_TOKEN_KEY, token);
    }

    protected storeRefreshToken(token: string) {
        window.localStorage.setItem(BaseWololoApiClient.REFRESH_TOKEN_KEY, token);
    }

    public userIsLoggedIn() {
        return this.getRefreshToken() !== undefined;
    }

    public async refreshToken() {

        const body: RefreshAccessTokenBody = {
            refreshToken: this.getRefreshToken()
        }

        const result = await this.doRequest(
            async (options) => new AuthApi().refreshAccessToken(body, options)
        );

        this.storeAccessToken(result.token);

        return result;
    }

    private addHeaderToOptions(options: any, header: string, value: string) {
        if (!options.headers) {
            options.headers = {};
        }

        options.headers[header] = value;
    }

    protected async doRequest<TModel>(requestAction: (options: any) => Promise<TModel>,
        options: any = {}): Promise<TModel> {

        this.addHeaderToOptions(options, "Origin", window.location.origin);
        this.addHeaderToOptions(options, "Content-Type", "application/json");

        try {
            const result = await requestAction(options);
            return result;
        } catch (response) {
            if(response.status === 401) {
                this.refreshToken();
                return await requestAction(options);
            }
            throw response;
        }
    }

    protected async doAuthenticatedRequest<TModel>(requestAction: (options: any) => Promise<TModel>,
        options: any = {}): Promise<TModel> {

        this.addHeaderToOptions(options, "Authorization", `Bearer ${this.getAccessToken()}`)

        return this.doRequest(requestAction, options);
    }

}

export class WololoAuthApiClient extends BaseWololoApiClient {

    authApi: AuthApi = new AuthApi()

    private async doAuthRequest(requestAction: (options: any) => Promise<NuevoJWTModel>,
        options: any = {}): Promise<NuevoJWTModel> {

        const requestResult = await this.doRequest(requestAction, options);

        this.storeAccessToken(requestResult.token);
        if(requestResult.refreshToken) {
            this.storeRefreshToken(requestResult.refreshToken);
        }

        return requestResult;
    }

    private deleteAccessToken() {
        window.localStorage.removeItem(BaseWololoApiClient.ACCESS_TOKEN_KEY);
    }

    private deleteRefreshToken() {
        window.localStorage.removeItem(BaseWololoApiClient.REFRESH_TOKEN_KEY);
    }

    public async logUserIn(googleIdToken: string) {

        const auth: GoogleAuthModel = {
            idToken: googleIdToken
        };

        return await this.doAuthRequest(
            async (options) => await this.authApi.logIn(auth, options)
        );
    }

    public async signUserUp(googleIdToken: string) {

        const auth: GoogleAuthModel = {
            idToken: googleIdToken
        };

        return await this.doAuthRequest(
            async (options) => await this.authApi.singUp(auth, options)
        );
    }

    public logUserOut() {
        this.deleteAccessToken();
        this.deleteRefreshToken();
    }

}


export class WololoProvinciasApiClient extends BaseWololoApiClient {

    provinciasApi: ProvinciasApi = new ProvinciasApi()

    public async listarProvincias(tamanioPagina?: number, pagina?: number) {
        return this.doAuthenticatedRequest(
            async (options) => this.provinciasApi.listarProvincias(tamanioPagina, pagina, options)
        );
    }

}


export class WololoUsuariosApiClient extends BaseWololoApiClient {

    usuarioApi: UsuariosApi = new UsuariosApi()

    public async listarUsuarios(filter?: string, tamanioPagina?: number, pagina?: number) {
        return this.doAuthenticatedRequest(
            async (options) => this.usuarioApi.listarUsuarios(filter, tamanioPagina, pagina, options)
        );
    }

    public async obtenerUsuarioLogueado() {
        return this.doAuthenticatedRequest(
            async (options) => this.usuarioApi.obtenerUsuarioLogueado(options)
        );
    }

}


export class WololoAdminApiClient extends BaseWololoApiClient {

    adminApi: AdminApi = new AdminApi()

    public async getEstadisticas(fechaInicio?: string, fechaFin?: string) {
        return this.doAuthenticatedRequest(
            async (options) => this.adminApi.getEstadisticas(fechaInicio, fechaFin, options)
        );
    }

    public async getEstadisticasDeUsuario(idUsuario: number) {
        return this.doAuthenticatedRequest(
            async (options) => this.adminApi.getEstadisticasDeUsuario(idUsuario, options)
        );
    }

    public async getScoreboard(tamanioPagina?: number, pagina?: number) {
        return this.doAuthenticatedRequest(
            async (options) => this.adminApi.getScoreboard(tamanioPagina, pagina, options)
        );
    }

    public async pasarTurno(idPartida: number) {
        console.log('RUNNING')
        return this.doAuthenticatedRequest(
            async (options) => {
                console.log('DOING')
                this.adminApi.pasarTurnoAdmin(idPartida, options)
            }
        );
    }

}


export class PollingPartida {

    private idPartida: number
    private frecuenciaMs: number
    private handler: (response: Promise<PartidaModel>) => void
    private partidasApiClient: WololoPartidasApiClient
    private timeout?: number

    constructor(
            idPartida: number,
            frecuenciaMs: number,
            handler: (response: Promise<PartidaModel>) => void,
            partidasApiClient: WololoPartidasApiClient) {
        this.idPartida = idPartida;
        this.frecuenciaMs = frecuenciaMs;
        this.handler = handler;
        this.partidasApiClient = partidasApiClient;
    }

    private doPoll() {
        const partida = this.partidasApiClient.getPartida(this.idPartida);
        this.handler(partida);
        this.timeout = window.setTimeout(this.doPoll.bind(this), this.frecuenciaMs);
    }

    start() {
        this.timeout = window.setTimeout(this.doPoll.bind(this), this.frecuenciaMs);
    }

    stop() {
        if(this.timeout !== undefined) {
            clearTimeout(this.timeout);
            this.timeout = undefined;
        }
    }

}


export class WololoPartidasApiClient extends BaseWololoApiClient {

    partidasApi: PartidasApi = new PartidasApi()

    public async listarPartidas(
        fechaInicio?: string,
        fechaFin?: string,
        estado?: EstadoDeJuegoModel,
        ordenarPor?: string,
        tamanioPagina?: number,
        pagina?: number
    ) {
        return this.doAuthenticatedRequest(
            async (options) => this.partidasApi.listarPartidas(
                fechaInicio,
                fechaFin,
                estado,
                ordenarPor,
                tamanioPagina,
                pagina,
                options
            )
        )
    }

    public async getPartida(idPartida: number) {
        return this.doAuthenticatedRequest(
            async (options) => this.partidasApi.getPartida(idPartida, options)
        );
    }

    public pollPartida(idPartida: number, frecuenciaMs: number, handler: (response: Promise<PartidaModel>) => void) {
        return new PollingPartida(
            idPartida,
            frecuenciaMs,
            handler,
            this
        );
    }

    public async crearPartida(body: CrearPartidaBody) {
        return this.doAuthenticatedRequest(
            async (options) => this.partidasApi.crearPartida(body, options)
        )
    }

    public async actualizarEstadoPartida(idPartida: number, body: ActualizarEstadoPartida) {
        return this.doAuthenticatedRequest(
            async (options) => this.partidasApi.actualizarEstadoPartida(idPartida, body, options)
        )
    }

    public async actualizarMunicipio(idPartida: number, idMunicipio: number, body: MunicipioEnJuegoModel) {
        return this.doAuthenticatedRequest(
            async (options) => this.partidasApi.actualizarMunicipio(idPartida, idMunicipio, body, options)
        )
    }

    public async moverGauchos(idPartida: number, body: MoverGauchosBody) {
        return this.doAuthenticatedRequest(
            async (options) => this.partidasApi.moverGauchos(idPartida, body, options)
        )
    }

    public async atacarMunicipio(idPartida: number, body: AtacarMunicipioBody) {
        return this.doAuthenticatedRequest(
            async (options) => this.partidasApi.atacarMunicipio(idPartida, body, options)
        )
    }

    public async simularAtacarMunicipio(idPartida: number, body: SimularAtacarMunicipioBody) {
        return this.doAuthenticatedRequest(
            async (options) => this.partidasApi.simularAtacarMunicipio(idPartida, body, options)
        )
    }

}
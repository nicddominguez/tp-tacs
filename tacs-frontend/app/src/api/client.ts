import { Configuration } from './configuration';
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
    RefreshAccessTokenBody,
    ActualizarEstadoPartida,
    PartidasApiFetchParamCreator,
    BASE_PATH
} from './api';
import * as portableFetch from "portable-fetch";

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

        if(!body.refreshToken) {
            return;
        }

        const result = await this.doRequest(
            async (options) => new AuthApi().refreshAccessToken(body, options)
        );

        this.storeAccessToken(result.token);
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

    public async getEstadisticasDeUsuario(idUsuario: string) {
        return this.doAuthenticatedRequest(
            async (options) => this.adminApi.getEstadisticasDeUsuario(idUsuario, options)
        );
    }

    public async getScoreboard(tamanioPagina?: number, pagina?: number) {
        return this.doAuthenticatedRequest(
            async (options) => this.adminApi.getScoreboard(tamanioPagina, pagina, options)
        );
    }

    public async pasarTurno(idPartida: string) {
        return this.doAuthenticatedRequest(
            async (options) => {
                this.adminApi.pasarTurnoAdmin(idPartida, options)
            }
        );
    }

}


export class PollingPartida {

    private idPartida: string
    private frecuenciaMs: number
    private handler: (response: Promise<PartidaModel>) => void
    private lastEtag: string = ""
    private lastPartida: any;
    private configuration: Configuration
    private timeout?: number

    constructor(
            idPartida: string,
            frecuenciaMs: number,
            handler: (response: Promise<PartidaModel>) => void,
            configuration: Configuration) {
        this.idPartida = idPartida;
        this.frecuenciaMs = frecuenciaMs;
        this.handler = handler;
        this.configuration = configuration;
    }

    private doPoll() {
        const authHeader = window.localStorage.getItem(BaseWololoApiClient.ACCESS_TOKEN_KEY) || undefined;
        const options = {
            headers: {
                "Authorization": `Bearer ${authHeader}`,
                "Origin": window.location.origin,
                "If-None-Match": this.lastEtag
            }
        }

        const fetchArgs = PartidasApiFetchParamCreator(this.configuration).getPartida(this.idPartida, undefined, options);

        const partidaPromise = fetch(BASE_PATH + fetchArgs.url, fetchArgs.options).then((response: any) => {
            if (response.status >= 200 && response.status < 300) {
                this.lastEtag = response.headers.get('etag');
                return response.json();
            } else if (response.status === 304) {
                return new Promise((resolve, reject) => resolve(this.lastPartida));
            }
            throw response;
        });

        partidaPromise.then(partida => {
            this.lastPartida = partida;
        });

        this.handler(partidaPromise);

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

    public async getPartida(idPartida: string, campos?: string) {
        return this.doAuthenticatedRequest(
            async (options) => this.partidasApi.getPartida(idPartida, campos, options)
        );
    }

    public pollPartida(idPartida: string, frecuenciaMs: number, handler: (response: Promise<PartidaModel>) => void) {
        return new PollingPartida(
            idPartida,
            frecuenciaMs,
            handler,
            // @ts-ignore
            this.partidasApi.configuration
        );
    }

    public async crearPartida(body: CrearPartidaBody) {
        return this.doAuthenticatedRequest(
            async (options) => this.partidasApi.crearPartida(body, options)
        )
    }

    public async actualizarEstadoPartida(idPartida: string, body: ActualizarEstadoPartida) {
        return this.doAuthenticatedRequest(
            async (options) => this.partidasApi.actualizarEstadoPartida(idPartida, body, options)
        )
    }

    public async actualizarMunicipio(idPartida: string, idMunicipio: string, body: MunicipioEnJuegoModel) {
        return this.doAuthenticatedRequest(
            async (options) => this.partidasApi.actualizarMunicipio(idPartida, idMunicipio, body, options)
        )
    }

    public async moverGauchos(idPartida: string, body: MoverGauchosBody) {
        return this.doAuthenticatedRequest(
            async (options) => this.partidasApi.moverGauchos(idPartida, body, options)
        )
    }

    public async atacarMunicipio(idPartida: string, body: AtacarMunicipioBody) {
        return this.doAuthenticatedRequest(
            async (options) => this.partidasApi.atacarMunicipio(idPartida, body, options)
        )
    }

    public async simularAtacarMunicipio(idPartida: string, body: SimularAtacarMunicipioBody) {
        return this.doAuthenticatedRequest(
            async (options) => this.partidasApi.simularAtacarMunicipio(idPartida, body, options)
        )
    }

    public async pasarTurno(idPartida: string) {
        return this.doAuthenticatedRequest(
            async (options) => this.partidasApi.pasarTurno(idPartida, options)
        );
    }

}
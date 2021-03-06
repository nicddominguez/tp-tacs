import { Typography, CardMedia, Card } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Slider from '@material-ui/core/Slider';
import Snackbar from '@material-ui/core/Snackbar';
import { ActualizarEstadoPartida, AtacarMunicipioBody, EstadoDeJuegoModel, MoverGauchosBody, MunicipioEnJuegoModel, PartidaModel, PartidaSinInfoModel, UsuarioModel, ModoDeMunicipioModel } from 'api';
import { PollingPartida, WololoAdminApiClient, WololoPartidasApiClient } from 'api/client';
import { GeoJsonObject } from 'geojson';
import React from 'react';
import { getMapData } from '../../api/maps';
import GameMap from './GameMap';

enum EstadoJuego {
  SELECCION = 'SELECCION',
  ATACANDO = 'ATACANDO',
  MOVIENDO = 'MOVIENDO'
}

export interface PantallaDeJuegoProps {
  partidaSinInfo?: PartidaSinInfoModel
  usuarioLogueado?: UsuarioModel
}

interface PantallaDeJuegoState {
  actionDialogOpen: boolean
  winDialogOpen: boolean
  snackbarOpen: boolean
  snackbarMessage?: string
  onClickMunicipio: (municipio: MunicipioEnJuegoModel) => void
  onPasarTurno: () => void
  onRendirUsuario: () => void
  estadoJuego: EstadoJuego,
  municipioSeleccionado?: MunicipioEnJuegoModel
  municipioObjetivo?: MunicipioEnJuegoModel
  cantidadGauchosAMover?: number
  mapData?: GeoJsonObject | Array<GeoJsonObject> | undefined
  partidaConInfo?: PartidaModel
  simulacionExitosa?: boolean
}

const partidasApiClient = new WololoPartidasApiClient();
const adminApiClient = new WololoAdminApiClient();

export default class PantallaDeJuego extends React.Component<PantallaDeJuegoProps, PantallaDeJuegoState> {

  private pollingPartida?: PollingPartida

  constructor(props: PantallaDeJuegoProps) {
    super(props);

    this.seleccionarMunicipio = this.seleccionarMunicipio.bind(this);
    this.cancelarPartida = this.cancelarPartida.bind(this);
    this.pasarTurno = this.pasarTurno.bind(this);

    this.state = {
      actionDialogOpen: false,
      winDialogOpen: false,
      snackbarOpen: false,
      estadoJuego: EstadoJuego.SELECCION,
      onClickMunicipio: this.seleccionarMunicipio,
      onPasarTurno: this.pasarTurno,
      onRendirUsuario: this.cancelarPartida,
      cantidadGauchosAMover: 1,
      mapData: undefined
    };

    this.handleDialogClose = this.handleDialogClose.bind(this);
    this.handleSnackbarClose = this.handleSnackbarClose.bind(this);
    this.handleCantidadDeGauchosUpdate = this.handleCantidadDeGauchosUpdate.bind(this);
    this.organizarAtaqueAMunicipio = this.organizarAtaqueAMunicipio.bind(this);
    this.solicitarAtaqueAMunicipio = this.solicitarAtaqueAMunicipio.bind(this);
    this.confirmarAtaqueAMunicipio = this.confirmarAtaqueAMunicipio.bind(this);
    this.organizarDesplazamientoGauchos = this.organizarDesplazamientoGauchos.bind(this);
    this.solicitarDesplazamientoGauchos = this.solicitarDesplazamientoGauchos.bind(this);
    this.confirmarDesplazamientoGauchos = this.confirmarDesplazamientoGauchos.bind(this);
    this.renderWinDialog = this.renderWinDialog.bind(this);
    this.renderBotonCambiarEstadoMunicipio = this.renderBotonCambiarEstadoMunicipio.bind(this);
    this.esElTurnoDelUsuario = this.esElTurnoDelUsuario.bind(this);
  }

  componentDidMount() {
    if (this.props.partidaSinInfo?.id !== undefined && this.props.partidaSinInfo?.provincia?.id) {
      // Obtener el mapa
      getMapData(this.props.partidaSinInfo.provincia.id)
        .then(mapData => {
          if (mapData) {
            this.setState({
              mapData: mapData
            })
          }
        });

      // Obtener la partida
      this.cargarPartida();

      // Configurar polling
      this.pollingPartida = partidasApiClient.pollPartida(
        this.props.partidaSinInfo.id,
        2000,
        (response: Promise<PartidaModel>) => {
          response
            .then(this.setPartida.bind(this))
            .catch(console.error);
        }
      );
      this.pollingPartida.start();
    }
  }

  componentWillUnmount() {
    if (this.pollingPartida) {
      this.pollingPartida.stop();
      this.pollingPartida = undefined;
    }
  }

  handleSnackbarClose() {
    this.setState({
      snackbarOpen: false
    });
  }

  handleDialogClose() {
    this.setState({
      actionDialogOpen: false,
      municipioSeleccionado: undefined,
      estadoJuego: EstadoJuego.SELECCION,
      onClickMunicipio: this.seleccionarMunicipio
    });
  }

  handleCantidadDeGauchosUpdate(event: any, nuevoValor: number | Array<number>) {
    this.setState({
      cantidadGauchosAMover: nuevoValor as number
    });
  }

  cargarPartida() {
    if (this.props.partidaSinInfo?.id !== undefined) {
      partidasApiClient.getPartida(this.props.partidaSinInfo.id)
        .then(this.setPartida.bind(this))
        .catch(console.error);
    }
  }

  setPartida(partida: PartidaModel) {
    const partidaTerminada = partida.idGanador !== null && partida.idGanador !== undefined;

    if (partidaTerminada) {
      this.pollingPartida?.stop();
    }

    this.setState({
      partidaConInfo: partida,
      winDialogOpen: partidaTerminada,
      onClickMunicipio: partidaTerminada ? () => { } : this.state.onClickMunicipio,
      onPasarTurno: partidaTerminada ? () => {} : this.state.onPasarTurno,
      onRendirUsuario: partidaTerminada ? () => {} : this.state.onRendirUsuario
    });
  }

  obtenerGanadorPartida() {
    const idGanador = this.state.partidaConInfo?.idGanador;
    if (idGanador !== undefined) {
      return this.state.partidaConInfo?.jugadores.find(
        jugador => jugador.id === idGanador
      );
    }
  }

  pasarTurno() {
    if (this.state.partidaConInfo?.id !== undefined) {
      partidasApiClient.pasarTurno(this.state.partidaConInfo?.id)
        .then(response => {
          this.cargarPartida();
        })
        .catch(console.error);
    }
  }

  cancelarPartida() {
    const actualizarEstadoPartida: ActualizarEstadoPartida = {
      estado: "Cancelada" as any as EstadoDeJuegoModel,
    };
    if (this.state.partidaConInfo != undefined) {
      partidasApiClient
        .actualizarEstadoPartida(this.state.partidaConInfo.id, actualizarEstadoPartida)
        .then((response) => console.log("Partida cancelada"))
        .catch(console.error);
    }
  }

  cambiarModoDeMunicipio(modo: ModoDeMunicipioModel) {

    if (!this.state.partidaConInfo || this.state.municipioSeleccionado?.id === undefined) {
      return;
    }

    const idPartida = this.state.partidaConInfo.id;

    const body: MunicipioEnJuegoModel = {
      id: this.state.municipioSeleccionado?.id,
      estaBloqueado: this.state.municipioSeleccionado?.estaBloqueado,
      modo: modo
    };

    partidasApiClient.actualizarMunicipio(idPartida, this.state.municipioSeleccionado?.id, body)
      .then(response => this.cargarPartida())
      .catch(console.error);

    this.setState({
      actionDialogOpen: false,
      snackbarOpen: true,
      snackbarMessage: `Cambiaste ${this.state.municipioSeleccionado.nombre} a ${modo}`,
      onClickMunicipio: this.seleccionarMunicipio,
      municipioSeleccionado: undefined,
      municipioObjetivo: undefined,
      estadoJuego: EstadoJuego.SELECCION
    });
  }

  actualizarMunicipiosEnPartida(municipiosActualizados: Array<MunicipioEnJuegoModel | undefined>) {

    const partida = this.state.partidaConInfo;

    if (!partida?.informacionDeJuego?.municipios) {
      return;
    }

    const municipiosFinales = partida.informacionDeJuego.municipios.map(municipio => {
      const versionActualizadaDeMunicipio = municipiosActualizados
        .find(municipioActualizado => municipioActualizado?.id === municipio.id);
      return versionActualizadaDeMunicipio || municipio;
    });

    partida.informacionDeJuego.municipios = municipiosFinales;

    this.setState({
      partidaConInfo: partida
    });

  }

  seleccionarMunicipio(municipioSeleccionado: MunicipioEnJuegoModel) {
    if (municipioSeleccionado.duenio?.id === undefined || this.props.usuarioLogueado?.id === undefined) {
      return;
    }

    if (municipioSeleccionado.duenio.id !== this.props.usuarioLogueado.id) {
      return;
    }

    this.setState({
      actionDialogOpen: true,
      municipioSeleccionado: municipioSeleccionado
    });
  }

  organizarAtaqueAMunicipio() {
    this.setState({
      actionDialogOpen: false,
      snackbarOpen: true,
      snackbarMessage: 'Seleccioná el municipio a atacar',
      onClickMunicipio: this.solicitarAtaqueAMunicipio
    });
  }

  solicitarAtaqueAMunicipio(municipioObjetivo: MunicipioEnJuegoModel) {
    if (municipioObjetivo.duenio?.id === undefined || this.props.usuarioLogueado?.id === undefined) {
      return;
    }

    if (municipioObjetivo.duenio.id === this.props.usuarioLogueado.id) {
      console.log('No se puede atacar a un municipio propio');
      return;
    }

    if (!this.state.partidaConInfo) {
      return;
    }

    const bodyAtaque: AtacarMunicipioBody = {
      idMunicipioAtacante: this.state.municipioSeleccionado?.id,
      idMunicipioObjetivo: municipioObjetivo?.id
    }

    partidasApiClient.simularAtacarMunicipio(this.state.partidaConInfo.id, bodyAtaque)
      .then(response => {
        this.setState({
          actionDialogOpen: true,
          snackbarOpen: false,
          municipioObjetivo: municipioObjetivo,
          estadoJuego: EstadoJuego.ATACANDO,
          simulacionExitosa: response.exitoso
        });
      })
      .catch(console.error);

  }

  confirmarAtaqueAMunicipio() {
    const municipioOrigen = this.state.municipioSeleccionado;
    const municipioObjetivo = this.state.municipioObjetivo;

    if (!this.state.partidaConInfo) {
      return;
    }

    const bodyAtaque: AtacarMunicipioBody = {
      idMunicipioAtacante: municipioOrigen?.id,
      idMunicipioObjetivo: municipioObjetivo?.id
    }

    partidasApiClient.atacarMunicipio(this.state.partidaConInfo.id, bodyAtaque)
      .then(response => {
        console.log(response.municipioAtacante);
        console.log(response.municipioAtacado);
        this.actualizarMunicipiosEnPartida([response.municipioAtacado, response.municipioAtacante]);
        this.setState({
          actionDialogOpen: false,
          snackbarOpen: true,
          snackbarMessage: `Atacaste a ${municipioObjetivo?.nombre} desde ${municipioOrigen?.nombre}`,
          onClickMunicipio: this.seleccionarMunicipio,
          municipioSeleccionado: undefined,
          municipioObjetivo: undefined,
          estadoJuego: EstadoJuego.SELECCION
        });
      })
      .catch(console.error);

  }

  organizarDesplazamientoGauchos() {
    this.setState({
      actionDialogOpen: false,
      snackbarOpen: true,
      snackbarMessage: 'A donde querés mover los gauchos?',
      onClickMunicipio: this.solicitarDesplazamientoGauchos
    });
  }

  solicitarDesplazamientoGauchos(municipioDestino: MunicipioEnJuegoModel) {

    if (municipioDestino.duenio?.id === undefined || this.props.usuarioLogueado?.id === undefined) {
      return;
    }

    if (municipioDestino.duenio.id !== this.props.usuarioLogueado.id) {
      console.log('No se pueden mover gauchos a un municipio que no es mio');
      return;
    }

    this.setState({
      actionDialogOpen: true,
      snackbarOpen: false,
      municipioObjetivo: municipioDestino,
      estadoJuego: EstadoJuego.MOVIENDO
    })
  }

  confirmarDesplazamientoGauchos() {
    const municipioOrigen = this.state.municipioSeleccionado;
    const municipioDestino = this.state.municipioObjetivo;

    if (!this.state.partidaConInfo) {
      return;
    }

    const bodyMovimiento: MoverGauchosBody = {
      cantidad: this.state.cantidadGauchosAMover,
      idMunicipioOrigen: municipioOrigen?.id,
      idMunicipioDestino: municipioDestino?.id
    }

    partidasApiClient.moverGauchos(this.state.partidaConInfo.id, bodyMovimiento)
      .then(response => {
        console.log(response.municipioOrigen)
        console.log(response.municipioDestino)
        this.actualizarMunicipiosEnPartida([response.municipioDestino, response.municipioOrigen]);
        this.setState({
          actionDialogOpen: false,
          snackbarOpen: true,
          snackbarMessage: `Moviste ${this.state.cantidadGauchosAMover} gauchos a ${municipioDestino?.nombre} desde ${municipioOrigen?.nombre}`,
          onClickMunicipio: this.seleccionarMunicipio,
          municipioSeleccionado: undefined,
          municipioObjetivo: undefined,
          estadoJuego: EstadoJuego.SELECCION,
          cantidadGauchosAMover: 1
        });
      })
      .catch(console.error);
  }

  esElTurnoDelUsuario() {
    if (
      this.state.partidaConInfo?.informacionDeJuego?.idUsuarioProximoTurno ===
      undefined
    ) {
      return false;
    }

    if (this.props.usuarioLogueado?.id === undefined) {
      return false;
    }

    return (
      this.state.partidaConInfo?.informacionDeJuego?.idUsuarioProximoTurno ===
      this.props.usuarioLogueado?.id
    );
  }

  renderSnackbar() {
    return (
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'center'
        }}
        open={this.state.snackbarOpen}
        onClose={this.handleSnackbarClose}
        message={this.state.snackbarMessage}
        autoHideDuration={5000}
      />
    )
  }

  renderBotonCambiarEstadoMunicipio() {
    if (this.state.municipioSeleccionado?.modo === ModoDeMunicipioModel.Defensa) {
      return (
        <Button
          onClick={() => this.cambiarModoDeMunicipio(ModoDeMunicipioModel.Produccion)}

        >
          Cambiar a Producción
        </Button>
      )
    } else {
      return (
        <Button
          onClick={() => this.cambiarModoDeMunicipio(ModoDeMunicipioModel.Defensa)}
        >
          Cambiar a Defensa
        </Button>
      )
    }
  }

  renderActionDialog() {
    const municipio = this.state.municipioSeleccionado;
    const accionesEstanDeshabilitadas = municipio?.estaBloqueado || municipio?.gauchos === 0;
    switch (this.state.estadoJuego) {
      case EstadoJuego.SELECCION:
        return (
          <div>
            <DialogTitle>{municipio?.nombre}</DialogTitle>
            <DialogContent>
              <Typography>Cantidad de gauchos: {municipio?.gauchos}</Typography>
              <Typography>Modo: {municipio?.modo}</Typography>
              <Typography>Producción: {municipio?.produccionDeGauchos}</Typography>
              <Typography>Defensa: {municipio?.puntosDeDefensa}</Typography>
              <Typography>Altura: {municipio?.altura} m.s.n.m.</Typography>
              {municipio?.estaBloqueado ? <Typography color='error'>Bloqueado</Typography> : undefined}
              <Card>
                <CardMedia
                  image={municipio?.urlImagen}
                  title={municipio?.nombre}
                  style={{height: '150px'}}
                />
              </Card>
            </DialogContent>
            <DialogActions style={{display: this.esElTurnoDelUsuario() ? "block" : "none"}}>
              <Button onClick={this.handleDialogClose}>Cancelar</Button>
              {this.renderBotonCambiarEstadoMunicipio()}
              <Button
                onClick={this.organizarDesplazamientoGauchos}
                disabled={accionesEstanDeshabilitadas}
              >
                Desplazar gauchos
              </Button>
              <Button
                onClick={this.organizarAtaqueAMunicipio}
                disabled={accionesEstanDeshabilitadas}
              >
                Organizar ataque
              </Button>
            </DialogActions>
          </div>
        )
      case EstadoJuego.ATACANDO:
        return (
          <div>
            <DialogTitle>Resumen de ataque</DialogTitle>
            <DialogContent>
              <DialogContentText>
                Vas a atacar a {municipio?.nombre} desde {municipio?.nombre}
              </DialogContentText>
              <DialogContentText>
                {this.state.simulacionExitosa ? 'Vas a ganar' : 'Vas a perder'}
              </DialogContentText>
            </DialogContent>
            <DialogActions style={{display: this.esElTurnoDelUsuario() ? "block" : "none"}}>
              <Button onClick={this.handleDialogClose}>Cancelar</Button>
              <Button onClick={this.confirmarAtaqueAMunicipio}>Confirmar</Button>
            </DialogActions>
          </div>
        )
      case EstadoJuego.MOVIENDO:
        return (
          <div>
            <DialogTitle>Resumen de movimiento</DialogTitle>
            <DialogContent>
              <DialogContentText>
                Vas a mover gauchos a {this.state.municipioObjetivo?.nombre} desde {this.state.municipioSeleccionado?.nombre}
              </DialogContentText>
              <Slider
                value={this.state.cantidadGauchosAMover}
                onChange={this.handleCantidadDeGauchosUpdate}
                min={1}
                max={this.state.municipioSeleccionado?.gauchos || 100}
              />
              <Typography>{this.state.cantidadGauchosAMover}</Typography>
            </DialogContent>
            <DialogActions style={{display: this.esElTurnoDelUsuario() ? "block" : "none"}}>
              <Button onClick={this.handleDialogClose}>Cancelar</Button>
              <Button onClick={this.confirmarDesplazamientoGauchos}>Confirmar</Button>
            </DialogActions>
          </div>
        )
    }
  }

  renderWinDialog() {
    return (
      <div>
        <DialogTitle>Partida terminada</DialogTitle>
        <DialogContent>
          <DialogContentText>Ganador: {this.obtenerGanadorPartida()?.nombreDeUsuario}</DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => this.setState({ winDialogOpen: false })}>Cerrar</Button>
        </DialogActions>
      </div>
    )
  }

  render() {
    return (
      <div>
        {this.state.mapData && this.state.partidaConInfo?.informacionDeJuego && <GameMap
          mapData={this.state.mapData}
          partida={this.state.partidaConInfo}
          onClickMunicipio={this.state.onClickMunicipio}
          usuarioLogueado={this.props.usuarioLogueado}
          onPasarTurno={this.state.onPasarTurno}
          onRendirUsuario={this.state.onRendirUsuario}
        />}
        <Dialog
          open={this.state.actionDialogOpen}
          onClose={this.handleDialogClose}
        >
          {this.renderActionDialog()}
        </Dialog>
        {this.renderSnackbar()}
        <Dialog
          open={this.state.winDialogOpen}
        >
          {this.renderWinDialog()}
        </Dialog>
      </div>
    )
  }

}
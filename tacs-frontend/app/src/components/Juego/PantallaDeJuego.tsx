import React from 'react';
import GameMap from './GameMap';
import { GeoJsonObject } from 'geojson';
import { PartidaModel, MunicipioEnJuegoModel, PartidaSinInfoModel, DatosDeJuegoModel } from 'api';
import Dialog from '@material-ui/core/Dialog';
import DialogTitle from '@material-ui/core/DialogTitle';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import Button from '@material-ui/core/Button';
import Snackbar from '@material-ui/core/Snackbar';
import Slider from '@material-ui/core/Slider';
import { getMapData } from '../../api/maps';
import { WololoPartidasApiClient } from 'api/client';

enum EstadoJuego {
  SELECCION = 'SELECCION',
  ATACANDO = 'ATACANDO',
  MOVIENDO = 'MOVIENDO'
}

export interface PantallaDeJuegoProps {
  partidaSinInfo?: PartidaSinInfoModel
  partida?: PartidaModel
  mapData: GeoJsonObject | Array<GeoJsonObject>
}

interface PantallaDeJuegoState {
  dialogOpen: boolean
  snackbarOpen: boolean
  snackbarMessage?: string
  onClickMunicipio: (municipio: MunicipioEnJuegoModel) => void
  estadoJuego: EstadoJuego,
  municipioSeleccionado?: MunicipioEnJuegoModel
  municipioObjetivo?: MunicipioEnJuegoModel
  cantidadGauchosAMover?: number
  mapData?: GeoJsonObject | Array<GeoJsonObject> | undefined
  infoPartida?: DatosDeJuegoModel
}

const partidasApiClient = new WololoPartidasApiClient();

export default class PantallaDeJuego extends React.Component<PantallaDeJuegoProps, PantallaDeJuegoState> {

  constructor(props: PantallaDeJuegoProps) {
    super(props);

    this.seleccionarMunicipio = this.seleccionarMunicipio.bind(this);

    this.state = {
      dialogOpen: false,
      snackbarOpen: false,
      estadoJuego: EstadoJuego.SELECCION,
      onClickMunicipio: this.seleccionarMunicipio,
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
  }

  componentDidMount() {
    if(this.props.partidaSinInfo?.id !== undefined && this.props.partidaSinInfo?.provincia?.id) {
      getMapData(this.props.partidaSinInfo?.provincia.id)
        .then(mapData => {
          console.log(mapData);
          if(mapData) {
            this.setState({
              mapData: mapData
            })
          }
        });
      partidasApiClient.getPartida(this.props.partidaSinInfo?.id)
        .then(partida => {
          this.setState({
            infoPartida: partida.informacionDeJuego
          })
        })
        .catch(console.error);
    }
  }

  handleSnackbarClose() {
    this.setState({
      snackbarOpen: false
    });
  }

  handleDialogClose() {
    this.setState({
      dialogOpen: false
    });
  }

  handleCantidadDeGauchosUpdate(event: any, nuevoValor: number | Array<number>) {
    this.setState({
      cantidadGauchosAMover: nuevoValor as number
    });
  }

  seleccionarMunicipio(municipioSeleccionado: MunicipioEnJuegoModel) {
    console.log(municipioSeleccionado);
    this.setState({
      dialogOpen: true,
      municipioSeleccionado: municipioSeleccionado
    });
  }

  organizarAtaqueAMunicipio() {
    this.setState({
      dialogOpen: false,
      snackbarOpen: true,
      snackbarMessage: 'Seleccioná el municipio a atacar',
      onClickMunicipio: this.solicitarAtaqueAMunicipio
    });
  }

  solicitarAtaqueAMunicipio(municipioObjetivo: MunicipioEnJuegoModel) {
    this.setState({
      dialogOpen: true,
      snackbarOpen: false,
      municipioObjetivo: municipioObjetivo,
      estadoJuego: EstadoJuego.ATACANDO
    });
  }

  confirmarAtaqueAMunicipio() {
    const municipioOrigen = this.state.municipioSeleccionado;
    const municipioObjetivo = this.state.municipioObjetivo;
    this.setState({
      dialogOpen: false,
      snackbarOpen: true,
      snackbarMessage: `Atacaste a ${municipioObjetivo?.nombre} desde ${municipioOrigen?.nombre}`,
      onClickMunicipio: this.seleccionarMunicipio,
      municipioSeleccionado: undefined,
      municipioObjetivo: undefined,
      estadoJuego: EstadoJuego.SELECCION
    });
  }

  organizarDesplazamientoGauchos() {
    this.setState({
      dialogOpen: false,
      snackbarOpen: true,
      snackbarMessage: 'A donde querés mover los gauchos?',
      onClickMunicipio: this.solicitarDesplazamientoGauchos
    });
  }

  solicitarDesplazamientoGauchos(municipioDestino: MunicipioEnJuegoModel) {
    this.setState({
      dialogOpen: true,
      snackbarOpen: false,
      municipioObjetivo: municipioDestino,
      estadoJuego: EstadoJuego.MOVIENDO
    })
  }

  confirmarDesplazamientoGauchos() {
    const municipioOrigen = this.state.municipioSeleccionado;
    const municipioDestino = this.state.municipioObjetivo;
    this.setState({
      dialogOpen: false,
      snackbarOpen: true,
      snackbarMessage: `Moviste ${this.state.cantidadGauchosAMover} gauchos a ${municipioDestino?.nombre} desde ${municipioOrigen?.nombre}`,
      onClickMunicipio: this.seleccionarMunicipio,
      municipioSeleccionado: undefined,
      municipioObjetivo: undefined,
      estadoJuego: EstadoJuego.SELECCION,
      cantidadGauchosAMover: 1
    });
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

  renderDialog() {
      switch(this.state.estadoJuego) {
        case EstadoJuego.SELECCION:
          return (
            <div>
              <DialogTitle>{this.state.municipioSeleccionado?.nombre}</DialogTitle>
              <DialogContent>
                <DialogContentText>Acá se puede poner lo que queramos de info del municipio</DialogContentText>
              </DialogContent>
              <DialogActions>
                <Button onClick={this.handleDialogClose}>Cerrar</Button>
                <Button onClick={this.organizarDesplazamientoGauchos}>Desplazar gauchos</Button>
                <Button onClick={this.organizarAtaqueAMunicipio} >Organizar ataque</Button>
              </DialogActions>
            </div>
          )
        case EstadoJuego.ATACANDO:
          return (
            <div>
              <DialogTitle>Resumen de ataque</DialogTitle>
              <DialogContent>
                <DialogContentText>
                  Vas a atacar a {this.state.municipioObjetivo?.nombre} desde {this.state.municipioSeleccionado?.nombre}
                </DialogContentText>
              </DialogContent>
              <DialogActions>
                {/* TODO: Si cierran acá, quedamos en el medio de un ataque. Deberiamos volver a seleccionar. */}
                <Button onClick={this.handleDialogClose}>Cerrar</Button>
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
              </DialogContent>
              <DialogActions>
                <Button onClick={this.handleDialogClose}>Cerrar</Button>
                <Button onClick={this.confirmarDesplazamientoGauchos}>Confirmar</Button>
              </DialogActions>
            </div>
          )
      }
  }

  render() {
    return (
      <div>
        {this.state.mapData && this.state.infoPartida && <GameMap
          mapData={this.state.mapData}
          jugadores={this.props.partida?.jugadores}
          datosDeJuego={this.state.infoPartida}
          onClickMunicipio={this.state.onClickMunicipio}
        />}
        <Dialog
          open={this.state.dialogOpen}
          onClose={this.handleDialogClose}
        >
          {this.renderDialog()}
        </Dialog>
        {this.renderSnackbar()}
      </div>
    )
  }

}
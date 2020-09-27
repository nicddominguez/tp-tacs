import { Card, Grid } from "@material-ui/core";
import Button from "@material-ui/core/Button";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";
import PersonIcon from "@material-ui/icons/Person";
import { MunicipioEnJuegoModel, PartidaModel, UsuarioModel } from "api/api";
import iconoParaMunicipio from "components/Juego/MapIcons";
import { Feature, GeoJsonObject, Geometry } from "geojson";
import { LatLng, Layer, LeafletMouseEvent, PathOptions } from "leaflet";
import { default as React } from "react";
import { GeoJSON, Map, Marker, Popup, TileLayer } from "react-leaflet";
import Control from "react-leaflet-control";
import "./map.css";

export interface GameMapProps {
  mapData?: GeoJsonObject | Array<GeoJsonObject>;
  onClickMunicipio?: (municipio: MunicipioEnJuegoModel) => void;
  partida?: PartidaModel;
  usuarioLogueado?: UsuarioModel;
  onPasarTurno?: () => void;
  onTerminarPartida?: () => void;
  onRendirUsuario?: () => void;
}

interface GameMapState {
  startLatitude: number;
  startLongitude: number;
  startZoom: number;
  playerColors: { [k: number]: string };
}

const GAME_MAP_COLORS = ["red", "blue", "green", "yellow"];

export default class GameMap extends React.Component<
  GameMapProps,
  GameMapState
> {
  state: GameMapState;

  constructor(props: GameMapProps) {
    super(props);

    let playerColors: { [k: number]: string } = {};
    this.props.partida?.jugadores?.forEach((player, index) => {
      playerColors[player.id] = GAME_MAP_COLORS[index];
    });

    this.state = {
      startLatitude: -37.1315537735949,
      startLongitude: -65.4466546606951,
      startZoom: 5,
      playerColors: playerColors,
    };

    this.onEachFeature = this.onEachFeature.bind(this);
    this.onClickArea = this.onClickArea.bind(this);
    this.featureStyle = this.featureStyle.bind(this);
    this.registerClickMunicipio = this.registerClickMunicipio.bind(this);
    this.renderPopupMunicipio = this.renderPopupMunicipio.bind(this);
  }

  featureStyle(feature?: Feature<Geometry, any>): PathOptions {
    const nombreMunicipio: string = feature?.properties.nombre;
    const municipioEnJuego = this.props.partida?.informacionDeJuego?.municipios?.find(
      (municipio) => municipio.nombre === nombreMunicipio
    );

    const idDuenio = municipioEnJuego?.duenio?.id;
    return {
      fillColor:
        idDuenio !== undefined ? this.state.playerColors[idDuenio] : "grey",
      fillOpacity: idDuenio !== undefined ? 0.5 : 0.2,
      color: "black",
      weight: 2,
      opacity: 0.3,
    };
  }

  onEachFeature(feature: Feature<Geometry, any>, layer: Layer) {
    layer.on({
      click: this.onClickArea,
    });
  }

  onClickArea(event: LeafletMouseEvent) {
    const layer = event.target;
    const nombre: string = layer.feature.properties.nombre;
    this.props.partida?.informacionDeJuego?.municipios
      ?.filter((municipio) => municipio.nombre === nombre)
      .forEach(this.registerClickMunicipio);
  }

  registerClickMunicipio(municipio: MunicipioEnJuegoModel) {
    if (this.props.onClickMunicipio) {
      this.props.onClickMunicipio(municipio);
    }
  }

  esElTurnoDelUsuario() {
    if (
      this.props.partida?.informacionDeJuego?.idUsuarioProximoTurno ===
      undefined
    ) {
      return false;
    }

    if (this.props.usuarioLogueado?.id === undefined) {
      return false;
    }

    return (
      this.props.partida?.informacionDeJuego?.idUsuarioProximoTurno ===
      this.props.usuarioLogueado?.id
    );
  }

  nombreUsuario(id: number | undefined) {
    const usuario:
      | UsuarioModel
      | undefined = this.props.partida?.jugadores.find(
      (jugador) => jugador.id == id
    );
    return usuario?.nombreDeUsuario;
  }

  renderPopupMunicipio(municipio: MunicipioEnJuegoModel) {
    if (municipio.ubicacion) {
      return (
        <Marker
          key={municipio.id}
          position={
            new LatLng(municipio.ubicacion.lat, municipio.ubicacion.lon)
          }
          onMouseOver={(e: LeafletMouseEvent) => {
            e.target.openPopup();
          }}
          onMouseOut={(e: LeafletMouseEvent) => {
            e.target.closePopup();
          }}
          onclick={() => this.registerClickMunicipio(municipio)}
          icon={iconoParaMunicipio(municipio, this.props.usuarioLogueado)}
        >
          <Popup>
            {/* Acá se debería poder usar material sin problema! */}
            <p>{municipio.nombre}</p>
            <p>{municipio.gauchos}</p>
          </Popup>
        </Marker>
      );
    }
    return undefined;
  }

  render() {
    const position: LatLng = new LatLng(
      this.state.startLatitude,
      this.state.startLongitude
    );
    return (
      <Map center={position} zoom={this.state.startZoom}>
        <TileLayer
          attribution='&amp;copy <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        {this.props.mapData && (
          <GeoJSON
            data={this.props.mapData}
            onEachFeature={this.onEachFeature}
            style={this.featureStyle}
          />
        )}

        {/* Mostramos un marker con popup */}
        {this.props.partida?.informacionDeJuego?.municipios?.map(
          this.renderPopupMunicipio
        )}

        {/* TODO: Acá hay que mostrar de quién es el turno actual. Se saca de la partida */}
        {/* TODO: El botón de pasar turno se debería mostrar solo cuando es mi turno */}
        {/* TODO: Poner botón de terminar partida */}
        <Control position="bottomleft">
          <Grid container direction="column" spacing={1}>
            <Grid item>
              <Button
                color={this.esElTurnoDelUsuario() ? "default" : "secondary"}
                style={{ backgroundColor: "white" }}
                fullWidth
              >
                {this.esElTurnoDelUsuario()
                  ? "Tu turno"
                  : `Turno de ${this.nombreUsuario(
                      this.props.partida?.informacionDeJuego
                        ?.idUsuarioProximoTurno
                    )}`}
              </Button>
            </Grid>
            <Grid item>
              <Button
                onClick={
                  this.props.onPasarTurno ? this.props.onPasarTurno : () => {}
                }
                color="primary"
                variant="contained"
                disabled={!this.esElTurnoDelUsuario()}
                fullWidth
              >
                Pasar turno
              </Button>
            </Grid>
          </Grid>
        </Control>

        {/* Para mostrar los jugadores y sus colores */}
        <Control position="bottomright">
          <Grid container direction="column" spacing={1}>
            <Grid item>
              <Card>
                <List component="nav" aria-label="main mailbox folders">
                  {this.props.partida?.jugadores.map((jugador) => {
                    return (
                      <ListItem button>
                        <ListItemIcon>
                          <PersonIcon
                            style={{
                              color: this.state.playerColors[jugador.id],
                            }}
                          />
                        </ListItemIcon>
                        <ListItemText
                          primary={
                            this.props.usuarioLogueado?.id == jugador.id
                              ? "Yo"
                              : jugador.nombreDeUsuario
                          }
                        />
                      </ListItem>
                    );
                  })}
                </List>
              </Card>
            </Grid>
            <Grid item>
              <Button
                onClick={
                  this.props.onRendirUsuario
                    ? this.props.onRendirUsuario
                    : () => {}
                }
                color="secondary"
                variant="contained"
                fullWidth
              >
                Rendirse
              </Button>
            </Grid>
          </Grid>
        </Control>
      </Map>
    );
  }
}

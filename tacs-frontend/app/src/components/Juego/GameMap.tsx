import React from 'react';
import ReactDomServer from 'react-dom/server';
import './map.css';
import { Feature, Geometry, GeoJsonObject } from 'geojson';
import { LatLng, Layer, PathOptions, LeafletMouseEvent } from 'leaflet';
import { Map, GeoJSON, TileLayer } from 'react-leaflet';
import Control from 'react-leaflet-control';
import { UsuarioModel, MunicipioEnJuegoModel, PartidaModel } from 'api/api';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';

export interface GameMapProps {
    mapData?: GeoJsonObject | Array<GeoJsonObject>
    onClickMunicipio?: (municipio: MunicipioEnJuegoModel) => void
    partida?: PartidaModel
    usuarioLogueado?: UsuarioModel
    onPasarTurno?: () => void
    onTerminarPartida?: () => void
}

interface GameMapState {
    startLatitude: number,
    startLongitude: number,
    startZoom: number,
    playerColors: { [k: number]: string }
}

const GAME_MAP_COLORS = ['red', 'brown', 'green', 'yellow'];


export default class GameMap extends React.Component<GameMapProps, GameMapState> {
    state: GameMapState

    constructor(props: GameMapProps) {
        super(props);

        let playerColors: { [k: number]: string } = {};
        this.props.partida?.jugadores?.forEach((player, index) => {
            playerColors[player.id] = GAME_MAP_COLORS[index]
        });

        this.state = {
            startLatitude: -37.1315537735949,
            startLongitude: -65.4466546606951,
            startZoom: 5,
            playerColors: playerColors
        }

        this.onEachFeature = this.onEachFeature.bind(this);
        this.onClickArea = this.onClickArea.bind(this);
        this.featureStyle = this.featureStyle.bind(this);
    }

    featureStyle(feature?: Feature<Geometry, any>): PathOptions {
        const nombreMunicipio: string = feature?.properties.nombre;
        const municipioEnJuego = this.props.partida?.informacionDeJuego
            ?.municipios
            ?.find(municipio => municipio.nombre === nombreMunicipio);

        const idDuenio = municipioEnJuego?.duenio?.id;
        return {
            fillColor: idDuenio !== undefined ? this.state.playerColors[idDuenio] : "grey",
            fillOpacity: idDuenio !== undefined ? 0.5 : 0.2
        };
    }

    onEachFeature(feature: Feature<Geometry, any>, layer: Layer) {
        const nombreMunicipio: string = feature.properties.nombre;
        const municipioEnJuego = this.props.partida?.informacionDeJuego
            ?.municipios
            ?.find(municipio => municipio.nombre === nombreMunicipio);

        let htmlString = '';

        if(municipioEnJuego) {
            htmlString = ReactDomServer.renderToString(
                <div>
                    <p>{municipioEnJuego?.nombre}</p>
                    <p>{municipioEnJuego?.gauchos}</p>
                </div>
            );
            layer.bindPopup(htmlString);
            layer.closePopup();
        }

        layer.on({
            click: this.onClickArea,
            mouseover: () => layer.openPopup(),
            mouseout: () => layer.closePopup()
        });
    }

    onClickArea(event: LeafletMouseEvent) {
        const layer = event.target;
        const nombre: string = layer.feature.properties.nombre;
        this.props.partida?.informacionDeJuego
            ?.municipios
            ?.filter(municipio => municipio.nombre === nombre)
            .forEach(municipio => {
                if (this.props.onClickMunicipio) {
                    this.props.onClickMunicipio(municipio);
                }
            });
    }

    esElTurnoDelUsuario() {
        if (this.props.partida?.informacionDeJuego?.idUsuarioProximoTurno === undefined) {
            return false;
        }

        if (this.props.usuarioLogueado?.id === undefined) {
            return false;
        }

        return this.props.partida?.informacionDeJuego?.idUsuarioProximoTurno === this.props.usuarioLogueado?.id;
    }

    render() {
        const position: LatLng = new LatLng(this.state.startLatitude, this.state.startLongitude);
        return (
            <Map center={position} zoom={this.state.startZoom} >
                <TileLayer
                    attribution='&amp;copy <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                {this.props.mapData &&
                    <GeoJSON
                        data={this.props.mapData}
                        onEachFeature={this.onEachFeature}
                        style={this.featureStyle}
                    />
                }

                {/* TODO: Acá hay que mostrar de quién es el turno actual. Se saca de la partida */}
                {/* TODO: El botón de pasar turno se debería mostrar solo cuando es mi turno */}
                {/* TODO: Poner botón de terminar partida */}
                <Control position='bottomleft'>
                    <Card>
                        <Button
                            onClick={this.props.onPasarTurno ? this.props.onPasarTurno : () => { }}
                            color='primary'
                        >
                            Pasar turno
                        </Button>
                    </Card>
                </Control>
                <Control position='bottomright'>
                    <Card>
                        {this.props.partida?.jugadores.map(jugador => {
                            return (
                                <div>
                                    <span>{this.state.playerColors[jugador.id]} </span>
                                    <span>{jugador.nombreDeUsuario}</span>
                                </div>
                            );
                        })}
                    </Card>
                </Control>
            </Map>
        )
    }

}
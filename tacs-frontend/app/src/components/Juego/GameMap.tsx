import React from 'react';
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
        layer.on({
            click: this.onClickArea
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
            </Map>
        )
    }

}
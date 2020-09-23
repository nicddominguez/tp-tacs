import React from 'react';
import ReactDomServer from 'react-dom/server';
import './map.css';
import { Feature, Geometry, GeoJsonObject } from 'geojson';
import { LatLng, Layer, PathOptions, LeafletMouseEvent } from 'leaflet';
import { Map, GeoJSON, TileLayer } from 'react-leaflet';
import Chaco from 'assets/maps/chaco';
import { UsuarioModel, DatosDeJuegoModel, MunicipioEnJuegoModel } from 'api/api';

export interface GameMapProps {
    mapData?: GeoJsonObject | Array<GeoJsonObject>
    onClickMunicipio?: (municipio: MunicipioEnJuegoModel) => void
    jugadores?: Array<UsuarioModel>
    datosDeJuego?: DatosDeJuegoModel
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
        this.props.jugadores?.forEach((player, index) => {
            playerColors[player.id] = GAME_MAP_COLORS[index]
        });

        this.state = {
            startLatitude: -25.2137925689732,
            startLongitude: -61.337579887251,
            startZoom: 7,
            playerColors: playerColors
        }

        this.onEachFeature = this.onEachFeature.bind(this);
        this.onClickArea = this.onClickArea.bind(this);
        this.featureStyle = this.featureStyle.bind(this);
    }

    featureStyle(feature?: Feature<Geometry, any>): PathOptions {
        const nombreMunicipio: string = feature?.properties.nombre;
        const municipioEnJuego = this.props.datosDeJuego
            ?.municipios
            ?.find(municipio => municipio.nombre === nombreMunicipio);
        const idDuenio = municipioEnJuego?.duenio?.id;

        return {
            fillColor: idDuenio ? this.state.playerColors[idDuenio] : "grey",
            fillOpacity: idDuenio ? 0.5 : 0.2
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
        this.props.datosDeJuego
            ?.municipios
            ?.filter(municipio => municipio.nombre === nombre)
            .forEach(municipio => {
                if(this.props.onClickMunicipio) {
                    this.props.onClickMunicipio(municipio);
                }
            });
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
            </Map>
        )
    }

}
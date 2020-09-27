import React, { Fragment } from 'react';
import { Icon, BaseIconOptions, LatLng } from 'leaflet';
import { Marker } from 'react-leaflet';
import ImagenGaucho from '../../assets/img/gaucho.webp';
import { MunicipioEnJuegoModel } from 'api';

function buildIcon(img: string, size: [number, number]) {
  const options: BaseIconOptions = {
    iconUrl: img,
    iconSize: size
  };
  return new Icon(options);
}

function iconoGaucho() {
  return buildIcon(ImagenGaucho, [100, 95]);
}

export function markersParaMovimiento(municipios?: Array<MunicipioEnJuegoModel>, municipioOrigen?: MunicipioEnJuegoModel) {

  if(!municipios || !municipioOrigen) {
    return <Fragment />;
  }

  const markers = municipios
    .filter(municipio => municipio.id !== municipioOrigen.id)
    .filter(municipio => municipio.duenio?.id === municipioOrigen.duenio?.id)
    .map(municipio => {
        if(municipio.ubicacion) {
          return (
            <Marker
              icon={iconoGaucho()}
              position={new LatLng(municipio.ubicacion.lat, municipio.ubicacion.lon)}
            />
          )
        }
      }
    );

  return (
    <Fragment>
      {markers}
    </Fragment>
  );
}
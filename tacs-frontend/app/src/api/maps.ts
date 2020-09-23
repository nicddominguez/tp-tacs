import * as fetch from "portable-fetch";
import { GeoJsonObject } from 'geojson';

export function getMapData(idProvincia: number): Promise<GeoJsonObject | void> {
    const request: Promise<Response> = fetch(`${window.location.origin}/mapas/${idProvincia}`,);
    return request
        .then(r => r.json())
        .then(body => JSON.parse(body) as GeoJsonObject)
        .catch(console.error);
}

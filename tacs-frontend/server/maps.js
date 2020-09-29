const fs = require('fs');
const path = require('path');

const mapFiles = new Map([
    [54, 'mapas/misiones.json'],
    [74, 'mapas/san-luis.json'],
    [70, 'mapas/san-juan.json'],
    [30, 'mapas/entre-rios.json'],
    [78, 'mapas/santa-cruz.json'],
    [62, 'mapas/rio-negro.json'],
    [26, 'mapas/chubut.json'],
    [14, 'mapas/cordoba.json'],
    [50, 'mapas/mendoza.json'],
    [46, 'mapas/la-rioja.json'],
    [10, 'mapas/catamarca.json'],
    [42, 'mapas/la-pampa.json'],
    [86, 'mapas/santiago-del-estero.json'],
    [18, 'mapas/corrientes.json'],
    [82, 'mapas/santa-fe.json'],
    [90, 'mapas/tucuman.json'],
    [58, 'mapas/neuquen.json'],
    [66, 'mapas/salta.json'],
    [22, 'mapas/chaco.json'],
    [34, 'mapas/formosa.json'],
    [38, 'mapas/jujuy.json'],
    [2, 'mapas/caba.json'],
    [6, 'mapas/buenos-aires.json'],
    [94, 'mapas/tierra-del-fuego.json']
]);

let mapData = new Map();

function initializeMapData() {
    mapFiles.forEach((filePath, idProvincia) => {
        const fullPath = path.join(__dirname, filePath);
        const fileData = fs.readFileSync(fullPath, 'utf-8');
        mapData.set(idProvincia, fileData);
    });
}

function getMapData(idProvincia) {
    return mapData.get(idProvincia);
}

module.exports = {
    initializeMapData,
    getMapData
};
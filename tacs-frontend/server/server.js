const express = require('express');
const path = require('path');
const cors = require('cors');
const app = express();
const maps = require('./maps');

maps.initializeMapData();

app.use(cors());
app.use(express.static(path.join(__dirname, 'build')));

app.get(['/', '/app', '/app/*', '/login'], (req, res) => {
  res.sendFile(path.join(__dirname, 'build', 'index.html'));
});

app.get('/mapas/:idProvincia', (req, res) => {
  const idProvincia = Number.parseInt(req.params.idProvincia);
  const mapData = maps.getMapData(idProvincia);
  if(mapData) {
    res.json(mapData);
    res.end();
  } else {
    res.status(400);
    res.end();
  }
})

app.listen(process.env.PORT || 8081);

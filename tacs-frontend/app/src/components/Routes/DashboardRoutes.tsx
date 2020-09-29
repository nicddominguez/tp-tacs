import React from "react";
import BarChartIcon from "@material-ui/icons/BarChart";
import MapIcon from "@material-ui/icons/Map";
import ViewListIcon from "@material-ui/icons/ViewList";
import NuevaPartidaStepper from "../Partidas/NuevaPartidaStepper";
import Partidas from "../Partidas/Partidas";

export const dashboardRoutes = [
  {
    path: "/app/partidas",
    sidebar: "Partidas",
    main: () => <Partidas />,
    icon: () => <ViewListIcon />,
  },
  {
    path: "/app/nuevaPartida",
    exact: true,
    sidebar: "Nueva Partida",
    main: () => <NuevaPartidaStepper />,
    icon: () => <MapIcon />,
  },
  {
    path: "/app/estadisticas",
    sidebar: "Estadísticas",
    main: () => <h2>Estadisticas</h2>,
    icon: () => <BarChartIcon />,
  },
];

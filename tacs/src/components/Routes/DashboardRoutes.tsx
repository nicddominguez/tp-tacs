import BarChartIcon from "@material-ui/icons/BarChart";
import HomeIcon from "@material-ui/icons/Home";
import MapIcon from "@material-ui/icons/Map";
import ViewListIcon from "@material-ui/icons/ViewList";
import Home from "components/Dashboard/Home";
import NuevaPartidaStepper from "components/Partidas/NuevaPartidaStepper";
import Partidas from "components/Partidas/Partidas";
import React from "react";

export const dashboardRoutes = [
  {
    path: "/app",
    exact: true,
    sidebar: "Home",
    main: () => <Home />,
    icon: () => <HomeIcon />,
  },
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

import BarChartIcon from "@material-ui/icons/BarChart";
import MapIcon from "@material-ui/icons/Map";
import ViewListIcon from "@material-ui/icons/ViewList";
import React from "react";

export const dashboardRoutes = [
  {
    path: "/app/partidas",
    sidebar: "Partidas",
    icon: () => <ViewListIcon />,
  },
  {
    path: "/app/nuevaPartida",
    sidebar: "Nueva Partida",
    icon: () => <MapIcon />,
  },
  {
    path: "/app/estadisticas",
    sidebar: "Estadísticas",
    icon: () => <BarChartIcon />,
  },
];

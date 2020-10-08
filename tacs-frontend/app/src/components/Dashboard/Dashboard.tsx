import AppBar from "@material-ui/core/AppBar";
import CssBaseline from "@material-ui/core/CssBaseline";
import Divider from "@material-ui/core/Divider";
import Drawer from "@material-ui/core/Drawer";
import IconButton from "@material-ui/core/IconButton";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";
import { makeStyles } from "@material-ui/core/styles";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import ChevronLeftIcon from "@material-ui/icons/ChevronLeft";
import ExitToAppIcon from "@material-ui/icons/ExitToApp";
import MenuIcon from "@material-ui/icons/Menu";
import clsx from "clsx";
import Estadisticas from "components/Estadisticas/Estadisticas";
import EstadisticasPartidas from "components/Estadisticas/EstadisticasPartidas";
import EstadisticasUsuarios from "components/Estadisticas/EstadisticasUsuarios";
import PantallaDeJuego from "components/Juego/PantallaDeJuego";
import NuevaPartidaStepper from "components/Partidas/NuevaPartidaStepper";
import Partidas from "components/Partidas/Partidas";
import React, { useEffect } from "react";
import { Link, Route, useHistory } from "react-router-dom";
import { PartidaSinInfoModel, UsuarioModel } from "../../api/api";
import { WololoAuthApiClient, WololoUsuariosApiClient } from "../../api/client";
import { dashboardRoutes as routes } from "../Routes/DashboardRoutes";

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
  root: {
    display: "flex",
  },
  toolbar: {
    paddingRight: 24,
  },
  toolbarIcon: {
    display: "flex",
    alignItems: "center",
    justifyContent: "flex-end",
    padding: "0 8px",
    ...theme.mixins.toolbar,
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(["width", "margin"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  appBarShift: {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(["width", "margin"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  menuButton: {
    marginRight: 36,
  },
  menuButtonHidden: {
    display: "none",
  },
  title: {
    flexGrow: 1,
  },
  drawerPaper: {
    position: "relative",
    whiteSpace: "nowrap",
    width: drawerWidth,
    transition: theme.transitions.create("width", {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  drawerPaperClose: {
    overflowX: "hidden",
    transition: theme.transitions.create("width", {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    width: theme.spacing(0),
    [theme.breakpoints.up("sm")]: {
      width: theme.spacing(0),
    },
  },
  appBarSpacer: theme.mixins.toolbar,
  content: {
    backgroundColor:
      theme.palette.type === "light"
        ? theme.palette.grey[100]
        : theme.palette.grey[900],
    flexGrow: 1,
    height: "100vh",
    overflow: "auto",
  },
}));

export interface DashboardProps {
  flagLoggedOut: () => void;
}

const authApiClient = new WololoAuthApiClient();
const usuariosApiClient = new WololoUsuariosApiClient();

export default function Dashboard(props: DashboardProps) {
  const classes = useStyles();

  const [open, setOpen] = React.useState(true);
  const [partidaSinInfo, setPartidaSinInfo] = React.useState(
    undefined as PartidaSinInfoModel | undefined
  );
  const [usuarioLogueado, setUsuarioLogueado] = React.useState(
    undefined as UsuarioModel | undefined
  );
  const history = useHistory();

  useEffect(() => {
    if (!usuarioLogueado) {
      usuariosApiClient
        .obtenerUsuarioLogueado()
        .then(setUsuarioLogueado)
        .catch(console.error);
    }
  }, []);

  const doLogOut = () => {
    authApiClient.logUserOut();
    props.flagLoggedOut();
  };

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  const jugarPartida = (partida: PartidaSinInfoModel) => {
    setPartidaSinInfo(partida);
    history.push("/app/jugar");
  };

  return (
    <div className={classes.root}>
      <CssBaseline />
      <AppBar
        position="absolute"
        className={clsx(classes.appBar, open && classes.appBarShift)}
      >
        <Toolbar className={classes.toolbar}>
          <IconButton
            edge="start"
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            className={clsx(
              classes.menuButton,
              open && classes.menuButtonHidden
            )}
          >
            <MenuIcon />
          </IconButton>
          <Typography
            component="h1"
            variant="h5"
            color="inherit"
            noWrap
            className={classes.title}
          >
            Wololo Dashboard
          </Typography>
          {usuarioLogueado && (
            <Typography component="h2" variant="h6" color="inherit" noWrap>
              {usuarioLogueado.nombreDeUsuario}
            </Typography>
          )}
        </Toolbar>
      </AppBar>
      <Drawer
        variant="persistent"
        classes={{
          paper: clsx(classes.drawerPaper, !open && classes.drawerPaperClose),
        }}
        open={open}
      >
        <div className={classes.toolbarIcon}>
          <IconButton onClick={handleDrawerClose}>
            <ChevronLeftIcon />
          </IconButton>
        </div>
        <Divider />
        <List>
          {routes.map((route) => (
            <Link
              to={route.path}
              style={{ textDecoration: "none", color: "#444" }}
            >
              <ListItem button>
                <ListItemIcon>{route.icon()}</ListItemIcon>
                <ListItemText primary={route.sidebar} />
              </ListItem>
            </Link>
          ))}
          <ListItem button onClick={doLogOut}>
            <ListItemIcon>
              <ExitToAppIcon />
            </ListItemIcon>
            <ListItemText primary="Cerrar Sesión" />
          </ListItem>
        </List>
      </Drawer>
      <main className={classes.content}>
        <div className={classes.appBarSpacer} />
        <Route
          key="/app/jugar"
          path="/app/jugar"
          exact={true}
          component={() => (
            <PantallaDeJuego
              partidaSinInfo={partidaSinInfo}
              usuarioLogueado={usuarioLogueado}
            />
          )}
        />
        <Route
          key="/app/partidas"
          path="/app/partidas"
          exact
          component={() => <Partidas onClickJugar={jugarPartida} />}
        />
        <Route
          key="/app/nuevaPartida"
          path="/app/nuevaPartida"
          exact
          component={() => <NuevaPartidaStepper />}
        />
        <Route
          key="/app/estadisticas"
          path="/app/estadisticas"
          exact
          component={() => <Estadisticas />}
        />
        <Route
          key="/app/estadisticas/usuarios"
          path="/app/estadisticas/usuarios"
          exact
          component={() => <EstadisticasUsuarios />}
        />
        <Route
          key="/app/estadisticas/partidas"
          path="/app/estadisticas/partidas"
          exact
          component={() => <EstadisticasPartidas />}
        />
        <Route
          key="/app/estadisticas/scoreboard"
          path="/app/estadisticas/scoreboard"
          exact
          component={() => <h2>Scoreboard</h2>}
        />
      </main>
    </div>
  );
}

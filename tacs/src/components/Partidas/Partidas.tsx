import DateFnsUtils from "@date-io/date-fns";
import Chip from "@material-ui/core/Chip";
import Collapse from "@material-ui/core/Collapse";
import Container from "@material-ui/core/Container";
import FormControl from "@material-ui/core/FormControl";
import Grid from "@material-ui/core/Grid";
import IconButton from "@material-ui/core/IconButton";
import Input from "@material-ui/core/Input";
import InputLabel from "@material-ui/core/InputLabel";
import MenuItem from "@material-ui/core/MenuItem";
import Paper from "@material-ui/core/Paper";
import Select from "@material-ui/core/Select";
import { createStyles, makeStyles, Theme } from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableFooter from "@material-ui/core/TableFooter";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Typography from "@material-ui/core/Typography";
import KeyboardArrowDownIcon from "@material-ui/icons/KeyboardArrowDown";
import KeyboardArrowUpIcon from "@material-ui/icons/KeyboardArrowUp";
import {
  KeyboardDatePicker,
  MuiPickersUtilsProvider,
} from "@material-ui/pickers";
import { EstadoDeJuegoModel, PartidaModel } from "api";
import { WololoPartidasApiClient } from "api/client";
import "date-fns";
import React, { useEffect } from "react";
import NuevaPartidaFabButton from "./NuevaPartidaFabButton";
import CustomTablePagination from "./TablePagination";

const useRowStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      "& > *": {
        borderBottom: "unset",
      },
    },
    paper: {
      padding: theme.spacing(2),
    },
    subDetalle: {
      paddingLeft: "20px",
    },
  })
);

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    container: {
      paddingTop: theme.spacing(2),
      paddingLeft: theme.spacing(1),
      paddingRight: theme.spacing(1),
      paddingBottom: theme.spacing(15),
    },
    paper: {
      padding: theme.spacing(2),
    },
    formControl: {
      margin: theme.spacing(1),
      minWidth: 120,
    },
    chips: {
      display: "flex",
      flexWrap: "wrap",
    },
    chip: {
      margin: 2,
    },
  })
);

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250,
    },
  },
};

function Row(props: { partida: PartidaModel }) {
  const { partida } = props;
  const [open, setOpen] = React.useState(false);
  const classes = useRowStyles();

  return (
    <React.Fragment>
      <TableRow className={classes.root}>
        <TableCell>
          <IconButton
            aria-label="expand row"
            size="small"
            onClick={() => setOpen(!open)}
          >
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell scope="row">{partida.id}</TableCell>
        <TableCell align="center">{partida.fecha}</TableCell>
        <TableCell align="center">{partida.estado}</TableCell>
        <TableCell align="center">{partida.provincia.nombre}</TableCell>
        <TableCell align="center">{partida.modoDeJuego}</TableCell>
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Grid container style={{ padding: "0px 20px 20px 20px" }}>
              <Grid item xs={12}>
                <Typography variant="h6">Más detalles</Typography>
              </Grid>
              <Grid item xs={6}>
                <Typography variant="body1" gutterBottom>
                  <div>Jugadores</div>
                  {partida.jugadores.map((jugador) => (
                    <div className={classes.subDetalle}>
                      <Typography variant="body1">
                        {jugador.nombreDeUsuario}
                      </Typography>
                    </div>
                  ))}
                </Typography>
              </Grid>
              <Grid item xs={6}>
                <Typography variant="body1">
                  Ganador:{" "}
                  {partida.estado == EstadoDeJuegoModel.EnProgreso
                    ? "-"
                    : partida.idGanador}
                </Typography>
                <Typography variant="body1" gutterBottom>
                  Cantidad de municipios: {partida.cantidadMunicipios}
                </Typography>
              </Grid>
            </Grid>
          </Collapse>
        </TableCell>
      </TableRow>
    </React.Fragment>
  );
}

export default function Partidas() {
  const partidasApi = new WololoPartidasApiClient();

  const classes = useStyles();
  const [primerOrden, setPrimerOrden] = React.useState("");
  const [segundoOrden, setSegundoOrden] = React.useState("");
  const [fechaDesde, setFechaDesde] = React.useState<Date | null>(
    new Date("2014-08-18T21:11:54")
  );
  const [fechaHasta, setFechaHasta] = React.useState<Date | null>(
    new Date("2014-08-18T21:11:54")
  );
  const [estado, setEstado] = React.useState<EstadoDeJuegoModel>();
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [partidas, setPartidas] = React.useState<PartidaModel[]>();

  const handlePrimerOrden = (event: React.ChangeEvent<{ value: unknown }>) => {
    setPrimerOrden(event.target.value as string);
  };

  const handleSegundoOrden = (event: React.ChangeEvent<{ value: unknown }>) => {
    setSegundoOrden(event.target.value as string);
  };

  const handleFechaDesde = (date: Date | null) => {
    setFechaDesde(date);
  };

  const handleFechaHasta = (date: Date | null) => {
    setFechaHasta(date);
  };

  const handleEstado = (event: React.ChangeEvent<{ value: unknown }>) => {
    setEstado(event.target.value as EstadoDeJuegoModel);
  };

  useEffect(() => {
    partidasApi
      .listarPartidas(
        undefined,
        undefined,
        undefined,
        undefined,
        rowsPerPage,
        page
      )
      .then((listarPartidasResponse) =>
        setPartidas(listarPartidasResponse.partidas)
      );
  });

  const handleChangePage = (
    event: React.MouseEvent<HTMLButtonElement> | null,
    newPage: number
  ) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };
  return (
    <Container maxWidth="lg" className={classes.container}>
      <Grid container spacing={3} className={classes.paper}>
        {/* TODO: Hacer que xs sea 12 para mobile */}
        <Grid item xs={6}>
          <Paper className={classes.paper}>
            <Typography variant="h6">Ordenar</Typography>
            <FormControl className={classes.formControl}>
              <InputLabel id="primer-orden-label">Primero por</InputLabel>
              <Select
                labelId="primer-orden-label"
                id="primer-orden"
                value={primerOrden}
                onChange={handlePrimerOrden}
              >
                <MenuItem value={"fecha"}>Fecha</MenuItem>
                <MenuItem value={"estado"}>Estado</MenuItem>
              </Select>
            </FormControl>
            <FormControl className={classes.formControl}>
              <InputLabel id="segundo-orden-label">Luego por</InputLabel>
              <Select
                labelId="segundo-orden-label"
                id="segundo-orden"
                value={segundoOrden}
                onChange={handleSegundoOrden}
              >
                <MenuItem value={"fecha"}>Fecha</MenuItem>
                <MenuItem value={"estado"}>Estado</MenuItem>
              </Select>
            </FormControl>
          </Paper>
        </Grid>
        <Grid item xs={6}>
          <Paper className={classes.paper}>
            <Typography variant="h6">Filtrar</Typography>
            <MuiPickersUtilsProvider utils={DateFnsUtils}>
              <Grid container justify="space-around">
                <KeyboardDatePicker
                  disableToolbar
                  variant="inline"
                  format="MM/dd/yyyy"
                  margin="normal"
                  id="fecha-desde"
                  label="Fecha desde"
                  value={fechaDesde}
                  onChange={handleFechaDesde}
                  KeyboardButtonProps={{
                    "aria-label": "change date",
                  }}
                />
                <KeyboardDatePicker
                  disableToolbar
                  variant="inline"
                  format="MM/dd/yyyy"
                  margin="normal"
                  id="fecha-hasta"
                  label="Fecha hasta"
                  value={fechaHasta}
                  onChange={handleFechaHasta}
                  KeyboardButtonProps={{
                    "aria-label": "change date",
                  }}
                />
              </Grid>
            </MuiPickersUtilsProvider>
            <FormControl className={classes.formControl}>
              <InputLabel id="estado-label">Estado</InputLabel>
              <Select
                labelId="estado-label"
                id="estado"
                value={estado}
                onChange={handleEstado}
              >
                <MenuItem value={EstadoDeJuegoModel.EnProgreso}>
                  En progreso
                </MenuItem>
                <MenuItem value={EstadoDeJuegoModel.Terminada}>
                  Terminada
                </MenuItem>
                <MenuItem value={EstadoDeJuegoModel.Cancelada}>
                  Cancelada
                </MenuItem>
              </Select>
            </FormControl>
          </Paper>
        </Grid>
        <Grid item xs={12}>
          <TableContainer component={Paper}>
            <Table aria-label="collapsible table">
              <TableHead>
                <TableRow>
                  <TableCell />
                  <TableCell>ID Partida</TableCell>
                  <TableCell align="center">Fecha</TableCell>
                  <TableCell align="center">Estado</TableCell>
                  <TableCell align="center">Provincia</TableCell>
                  <TableCell align="center">Modo de juego</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {partidas?.map((partida) => (
                  <Row key={partida.id} partida={partida} />
                ))}
              </TableBody>
              <TableFooter>
                <TableRow>
                  <CustomTablePagination
                    rowsLength={partidas ? partidas.length : 0}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    onChangePage={handleChangePage}
                    onChangeRowsPerPage={handleChangeRowsPerPage}
                  />
                </TableRow>
              </TableFooter>
            </Table>
          </TableContainer>
        </Grid>
        <NuevaPartidaFabButton></NuevaPartidaFabButton>
      </Grid>
    </Container>
  );
}

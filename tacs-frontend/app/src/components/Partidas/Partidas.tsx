import DateFnsUtils from "@date-io/date-fns";
import Button from "@material-ui/core/Button";
import Collapse from "@material-ui/core/Collapse";
import Container from "@material-ui/core/Container";
import FormControl from "@material-ui/core/FormControl";
import Grid from "@material-ui/core/Grid";
import IconButton from "@material-ui/core/IconButton";
import InputLabel from "@material-ui/core/InputLabel";
import MenuItem from "@material-ui/core/MenuItem";
import Paper from "@material-ui/core/Paper";
import Select from "@material-ui/core/Select";
import {
  createStyles,
  makeStyles,
  Theme,
  withStyles,
} from "@material-ui/core/styles";
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
import { MaterialUiPickersDate } from "@material-ui/pickers/typings/date";
import { EstadoDeJuegoModel, PartidaSinInfoModel } from "api";
import { WololoPartidasApiClient } from "api/client";
import "date-fns";
import React from "react";
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

function Row(props: {
  partida: PartidaSinInfoModel;
  onClickJugar?: (partida: PartidaSinInfoModel) => void;
}) {
  const { partida } = props;
  const [open, setOpen] = React.useState(false);
  const classes = useRowStyles();

  const jugarAction = () => {
    if (props.onClickJugar) {
      props.onClickJugar(props.partida);
    }
  };

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
        <TableCell align="center">{partida.provincia?.nombre}</TableCell>
        <TableCell align="center">{partida.modoDeJuego}</TableCell>
        <TableCell align="center">
          <Button color="primary" onClick={jugarAction}>
            Jugar! ⚔️
          </Button>
        </TableCell>
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
                  {partida.jugadores?.map((jugador) => (
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

const useStyles = (theme: Theme) =>
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
    margin: {
      margin: theme.spacing(1),
    },
  });

interface Props {
  classes: any;
  onClickJugar?: (partida: PartidaSinInfoModel) => void;
}

interface State {
  primerOrden?: string;
  segundoOrden?: string;
  fechaInicio?: Date;
  fechaFin?: Date;
  estado?: EstadoDeJuegoModel;
  partidas?: PartidaSinInfoModel[];
  cantidadTotalDePartidas?: number;
  page: number;
  pageSize: number;
}

class Partidas extends React.Component<Props, State> {
  protected partidasApi = new WololoPartidasApiClient();

  constructor(props: Props) {
    super(props);
    this.state = {
      page: 0,
      pageSize: 5,
    };
  }

  dateToStringFormat = (date: Date | undefined) => {
    return date
      ? date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate()
      : undefined;
  };

  listarPartidas = () => {
    this.partidasApi
      .listarPartidas(
        this.dateToStringFormat(this.state.fechaInicio),
        this.dateToStringFormat(this.state.fechaFin),
        this.state.estado,
        this.state.primerOrden
          ? this.state.primerOrden +
              (this.state.segundoOrden ? "," + this.state.segundoOrden : "")
          : undefined,
        this.state.pageSize,
        this.state.page
      )
      .then((listarPartidasResponse) => {
        this.setState({
          partidas: listarPartidasResponse.partidas,
          cantidadTotalDePartidas:
            listarPartidasResponse.cantidadTotalDePartidas,
        });
      })
      .catch(console.error);
  };

  componentDidMount() {
    this.listarPartidas();
  }

  handlePrimerOrden = (event: React.ChangeEvent<{ value: unknown }>) => {
    const primerOrden = event.target.value as string;
    this.setState({ primerOrden: primerOrden }, this.listarPartidas);
  };

  handleSegundoOrden = (event: React.ChangeEvent<{ value: unknown }>) => {
    this.setState(
      { segundoOrden: event.target.value as string },
      this.listarPartidas
    );
  };

  handleFechaInicio = (
    date: MaterialUiPickersDate,
    value?: string | null | undefined
  ) => {
    this.setState({ fechaInicio: date as Date }, this.listarPartidas);
  };

  handleFechaFin = (
    date: MaterialUiPickersDate,
    value?: string | null | undefined
  ) => {
    this.setState({ fechaFin: date as Date }, this.listarPartidas);
  };

  handleEstado = (event: React.ChangeEvent<{ value: unknown }>) => {
    this.setState(
      { estado: event.target.value as EstadoDeJuegoModel },
      this.listarPartidas
    );
  };

  handlePage = (
    event: React.MouseEvent<HTMLButtonElement> | null,
    newPage: number
  ) => {
    this.setState({ page: newPage }, this.listarPartidas);
  };

  handlePageSize = (
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    this.setState(
      { pageSize: parseInt(event.target.value, 10) },
      this.listarPartidas
    );
    this.setState({ page: 0 });
  };

  handleRemoverOrdenar = (
    event: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    this.setState(
      {
        primerOrden: undefined,
        segundoOrden: undefined,
      },
      this.listarPartidas
    );
  };

  handleRemoverFiltrar = (
    event: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    this.setState(
      {
        fechaInicio: undefined,
        fechaFin: undefined,
        estado: undefined,
      },
      this.listarPartidas
    );
  };

  render() {
    const classes = this.props.classes;
    return (
      <Container maxWidth="lg" className={classes.container}>
        <Grid container spacing={3} className={classes.paper}>
          <Grid item xs={6}>
            <Paper className={classes.paper}>
              <Grid container justify="space-between" alignItems="center">
                <Typography variant="h6">Ordenar</Typography>
                <Button
                  size="small"
                  variant="contained"
                  className={classes.margin}
                  onClick={this.handleRemoverOrdenar}
                >
                  Remover
                </Button>
              </Grid>
              <FormControl className={classes.formControl}>
                <InputLabel id="primer-orden-label">Primero por</InputLabel>
                <Select
                  labelId="primer-orden-label"
                  id="primer-orden"
                  value={this.state.primerOrden || ""}
                  onChange={this.handlePrimerOrden}
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
                  value={this.state.segundoOrden || ""}
                  onChange={this.handleSegundoOrden}
                >
                  <MenuItem value={"fecha"}>Fecha</MenuItem>
                  <MenuItem value={"estado"}>Estado</MenuItem>
                </Select>
              </FormControl>
            </Paper>
          </Grid>
          <Grid item xs={6}>
            <Paper className={classes.paper}>
              <Grid container justify="space-between" alignItems="center">
                <Typography variant="h6">Filtrar</Typography>
                <Button
                  size="small"
                  variant="contained"
                  className={classes.margin}
                  onClick={this.handleRemoverFiltrar}
                >
                  Remover
                </Button>
              </Grid>
              <MuiPickersUtilsProvider utils={DateFnsUtils}>
                <Grid container justify="space-around">
                  <KeyboardDatePicker
                    disableToolbar
                    variant="inline"
                    format="dd/MM/yyyy"
                    margin="normal"
                    id="fecha-inicio"
                    label="Fecha inicio"
                    value={this.state.fechaInicio}
                    onChange={this.handleFechaInicio}
                    KeyboardButtonProps={{
                      "aria-label": "change date",
                    }}
                  />
                  <KeyboardDatePicker
                    disableToolbar
                    variant="inline"
                    format="dd/MM/yyyy"
                    margin="normal"
                    id="fecha-fin"
                    label="Fecha fin"
                    value={this.state.fechaFin}
                    onChange={this.handleFechaFin}
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
                  value={this.state.estado || ""}
                  onChange={this.handleEstado}
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
                    <TableCell align="center">Acciones</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {this.state.partidas?.map((partida) => (
                    <Row
                      key={partida.id}
                      partida={partida}
                      onClickJugar={this.props.onClickJugar}
                    />
                  ))}
                </TableBody>
                <TableFooter>
                  <TableRow>
                    <CustomTablePagination
                      rowsLength={this.state.cantidadTotalDePartidas || 0}
                      rowsPerPage={this.state.pageSize}
                      page={this.state.page}
                      onChangePage={this.handlePage}
                      onChangeRowsPerPage={this.handlePageSize}
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
}

export default withStyles(useStyles)(Partidas);

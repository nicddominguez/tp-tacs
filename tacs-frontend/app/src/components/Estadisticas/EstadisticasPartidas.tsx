import DateFnsUtils from "@date-io/date-fns";
import { Button, Container, Grid, Paper, Typography } from "@material-ui/core";
import { createStyles, Theme, withStyles } from "@material-ui/core/styles";
import {
  KeyboardDatePicker,
  MuiPickersUtilsProvider
} from "@material-ui/pickers";
import { MaterialUiPickersDate } from "@material-ui/pickers/typings/date";
import { EstadisticasDeJuegoModel, PartidaSinInfoModel } from "api";
import { WololoAdminApiClient, WololoPartidasApiClient } from "api/client";
import React from "react";

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
    margin: {
      margin: theme.spacing(1),
    },
  });

interface Props {
  classes: any;
}

interface State {
  listadoPartidas?: Array<PartidaSinInfoModel>;
  estadisticasDePartida?: EstadisticasDeJuegoModel;
  fechaInicio?: Date;
  fechaFin?: Date;
}

class EstadisticasPartidas extends React.Component<Props, State> {
  partidasApiClient: WololoPartidasApiClient;
  adminApiClient: WololoAdminApiClient;

  constructor(props: Props) {
    super(props);

    this.state = {};

    this.partidasApiClient = new WololoPartidasApiClient();
    this.adminApiClient = new WololoAdminApiClient();
    this.dateToStringFormat = this.dateToStringFormat.bind(this);
    this.handleFechaInicio = this.handleFechaInicio.bind(this);
    this.handleFechaFin = this.handleFechaFin.bind(this);
    this.buscarEstadisticas = this.buscarEstadisticas.bind(this);
    this.handleRemoverFiltrar = this.handleRemoverFiltrar.bind(this);
  }

  dateToStringFormat(date: Date | undefined) {
    return date
      ? date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate()
      : undefined;
  }

  handleFechaInicio(
    date: MaterialUiPickersDate,
    value?: string | null | undefined
  ) {
    this.setState({ fechaInicio: date as Date }, this.buscarEstadisticas);
  }

  handleFechaFin(
    date: MaterialUiPickersDate,
    value?: string | null | undefined
  ) {
    this.setState({ fechaFin: date as Date }, this.buscarEstadisticas);
  }

  handleRemoverFiltrar(event: React.MouseEvent<HTMLButtonElement, MouseEvent>) {
    this.setState({
      fechaInicio: undefined,
      fechaFin: undefined,
      estadisticasDePartida: undefined,
    });
  }

  buscarEstadisticas() {
    this.adminApiClient
      .getEstadisticas(
        this.dateToStringFormat(this.state.fechaInicio),
        this.dateToStringFormat(this.state.fechaFin)
      )
      .then((estadisticasDePartida) => {
        this.setState({
          estadisticasDePartida: estadisticasDePartida,
        });
      })
      .catch(console.error);
  }

  render() {
    const classes = this.props.classes;
    return (
      <Container maxWidth="lg" className={classes.container}>
        <Grid container spacing={3} className={classes.paper}>
          <Grid item xs={4}>
            <Paper className={classes.paper}>
              <Grid container justify="space-between" alignItems="center">
                <Typography variant="h6">Filtrar por Fecha</Typography>
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
            </Paper>
          </Grid>
          {this.state.estadisticasDePartida ? (
            <Grid item xs={8}>
              <Grid container direction="column" spacing={3}>
                <Grid item>
                  <Paper className={classes.paper}>
                    <Typography
                      align="center"
                      variant="h6"
                      style={{ color: "#0dd456" }}
                    >
                      Partidas en Curso
                    </Typography>
                    <Typography
                      align="center"
                      variant="h4"
                      style={{ color: "#0dd456" }}
                    >
                      {this.state.estadisticasDePartida?.partidasEnCurso}
                    </Typography>
                  </Paper>
                </Grid>
                <Grid item>
                  <Paper className={classes.paper}>
                    <Typography
                      align="center"
                      variant="h6"
                      style={{ color: "#126ede" }}
                    >
                      Partidas Creadas
                    </Typography>
                    <Typography
                      align="center"
                      variant="h4"
                      style={{ color: "#126ede" }}
                    >
                      {this.state.estadisticasDePartida?.partidasCreadas}
                    </Typography>
                  </Paper>
                </Grid>
                <Grid item>
                  <Paper className={classes.paper}>
                    <Typography
                      align="center"
                      variant="h6"
                      style={{ color: "#e6b00e" }}
                    >
                      Partidas Terminadas
                    </Typography>
                    <Typography
                      align="center"
                      variant="h4"
                      style={{ color: "#e6b00e" }}
                    >
                      {this.state.estadisticasDePartida?.partidasTerminadas}
                    </Typography>
                  </Paper>
                </Grid>
                <Grid item>
                  <Paper className={classes.paper}>
                    <Typography
                      align="center"
                      variant="h6"
                      style={{ color: "#e6350e" }}
                    >
                      Partidas Canceladas
                    </Typography>
                    <Typography
                      align="center"
                      variant="h4"
                      style={{ color: "#e6350e" }}
                    >
                      {this.state.estadisticasDePartida?.partidasCanceladas}
                    </Typography>
                  </Paper>
                </Grid>
              </Grid>
            </Grid>
          ) : undefined}
        </Grid>
      </Container>
    );
  }
}

export default withStyles(useStyles)(EstadisticasPartidas);

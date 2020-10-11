import {
  Container,
  Grid,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableFooter,
  TableHead,
  TableRow,
} from "@material-ui/core";
import { createStyles, Theme, withStyles } from "@material-ui/core/styles";
import { EstadisticasDeUsuarioModel } from "api";
import { WololoAdminApiClient } from "api/client";
import CustomTablePagination from "components/Partidas/TablePagination";
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
  });

interface Props {
  classes: any;
}

interface State {
  scoreboard?: Array<EstadisticasDeUsuarioModel>;
  cantidadTotalDeEstadisticas: number;
  page: number;
  pageSize: number;
}

class Scoreboard extends React.Component<Props, State> {
  adminApiClient: WololoAdminApiClient;

  constructor(props: Props) {
    super(props);

    this.adminApiClient = new WololoAdminApiClient();

    this.state = {
      cantidadTotalDeEstadisticas: 0,
      page: 0,
      pageSize: 10,
    };

    this.handlePage = this.handlePage.bind(this);
    this.handlePageSize = this.handlePageSize.bind(this);
    this.listarScoreboard = this.listarScoreboard.bind(this);
  }

  listarScoreboard() {
    this.adminApiClient
      .getScoreboard(this.state.pageSize, this.state.page)
      .then((scoreboardResponse) => {
        this.setState({
          scoreboard: scoreboardResponse.scoreboard,
          cantidadTotalDeEstadisticas: scoreboardResponse.cantidadJugadores || 0,
        });
      });
  }

  componentDidMount() {
    this.listarScoreboard();
  }

  handlePage(
    event: React.MouseEvent<HTMLButtonElement> | null,
    newPage: number
  ) {
    this.setState({ page: newPage }, this.listarScoreboard);
  }

  handlePageSize(
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) {
    this.setState(
      { pageSize: parseInt(event.target.value, 10) },
      this.listarScoreboard
    );
    this.setState({ page: 0 });
  }

  render() {
    const classes = this.props.classes;
    return (
      <Container maxWidth="lg" className={classes.container}>
        <Grid container spacing={3} className={classes.paper}>
          <Grid item xs={12}>
            <Paper className={classes.paper}>
              <TableContainer component={Paper}>
                <Table aria-label="collapsible table">
                  <TableHead>
                    <TableRow>
                      <TableCell align="center">Nombre del Jugador</TableCell>
                      <TableCell align="center">Partidas Jugadas</TableCell>
                      <TableCell align="center">Partidas Ganadas</TableCell>
                      <TableCell align="center">Racha actual</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {this.state.scoreboard?.map((estadisticaDeUsuario) => (
                      <TableRow>
                        <TableCell align="center">
                          {estadisticaDeUsuario.usuario?.nombreDeUsuario}
                        </TableCell>
                        <TableCell align="center">
                          {estadisticaDeUsuario.partidasJugadas}
                        </TableCell>
                        <TableCell align="center">
                          {estadisticaDeUsuario.partidasGanadas}
                        </TableCell>
                        <TableCell align="center">
                          {estadisticaDeUsuario.rachaActual}
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                  <TableFooter>
                    <TableRow>
                      <CustomTablePagination
                        rowsLength={this.state.cantidadTotalDeEstadisticas || 0}
                        rowsPerPage={this.state.pageSize}
                        page={this.state.page}
                        onChangePage={this.handlePage}
                        onChangeRowsPerPage={this.handlePageSize}
                      />
                    </TableRow>
                  </TableFooter>
                </Table>
              </TableContainer>
            </Paper>
          </Grid>
        </Grid>
      </Container>
    );
  }
}

export default withStyles(useStyles)(Scoreboard);

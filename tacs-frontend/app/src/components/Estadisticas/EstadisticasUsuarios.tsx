import {
  Button,
  Container,
  Grid,
  List,
  ListItem,

  ListItemText,
  Paper,
  TextField,
  Typography
} from "@material-ui/core";
import { createStyles, Theme, withStyles } from "@material-ui/core/styles";
import { EstadisticasDeUsuarioModel, UsuarioModel } from "api";
import { WololoAdminApiClient, WololoUsuariosApiClient } from "api/client";
import TablePaginationActions from "components/Partidas/TablePaginationActions";
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
  listadoUsuarios?: Array<UsuarioModel>;
  estadisticasDeUsuario?: EstadisticasDeUsuarioModel;
  cantidadTotalDeUsuarios: number;
  filtro: string;
  page: number;
  pageSize: number;
}

class EstadisticasUsuarios extends React.Component<Props, State> {
  usuariosApiClient: WololoUsuariosApiClient;
  adminApiClient: WololoAdminApiClient;

  constructor(props: Props) {
    super(props);

    this.usuariosApiClient = new WololoUsuariosApiClient();
    this.adminApiClient = new WololoAdminApiClient();

    this.state = {
      filtro: "",
      cantidadTotalDeUsuarios: 0,
      page: 0,
      pageSize: 8,
    };

    this.buscar = this.buscar.bind(this);
    this.handlePage = this.handlePage.bind(this);
    this.onChangeFilter = this.onChangeFilter.bind(this);
    this.handleUsuario = this.handleUsuario.bind(this);
  }

  buscar() {
    this.usuariosApiClient
      .listarUsuarios(
        this.state.filtro == "" ? undefined : this.state.filtro,
        this.state.pageSize,
        this.state.page
      )
      .then((response) => {
        this.setState({
          listadoUsuarios: response.usuarios || [],
          cantidadTotalDeUsuarios: response.cantidadTotalDeUsuarios || 0,
        });
      })
      .catch(console.error);
  }

  handlePage(
    event: React.MouseEvent<HTMLButtonElement> | null,
    newPage: number
  ) {
    this.setState({ page: newPage }, this.buscar);
  }

  onChangeFilter(event: React.ChangeEvent<HTMLInputElement>) {
    this.setState({
      filtro: event.target.value,
    });
  }

  handleUsuario(usuario: UsuarioModel) {
    return () => {
      this.adminApiClient
        .getEstadisticasDeUsuario(usuario.id)
        .then((estadisticasDeUsuario) => {
          this.setState({ estadisticasDeUsuario: estadisticasDeUsuario });
        })
        .catch(console.error);
    };
  }

  render() {
    const classes = this.props.classes;
    return (
      <Container maxWidth="lg" className={classes.container}>
        <Grid container spacing={3} className={classes.paper}>
          <Grid item xs={4}>
            <Paper className={classes.paper}>
              <TextField
                label="Filtro"
                value={this.state.filtro}
                onChange={this.onChangeFilter}
              />
              <Button color="primary" onClick={this.buscar}>
                Buscar
              </Button>
              <Paper>
                <List dense component="nav" role="list">
                  <ListItem key={-1}>
                    <ListItemText
                      id="listheader"
                      primary="Usuarios"
                      primaryTypographyProps={{ variant: "h6" }}
                    />
                  </ListItem>
                  {this.state.listadoUsuarios?.map((usuario: UsuarioModel) => (
                    <ListItem key={usuario.id} role="listitem" button>
                      <ListItemText
                        id={`${usuario.id}-${usuario.nombreDeUsuario}`}
                        primary={usuario.nombreDeUsuario}
                        onClick={this.handleUsuario(usuario)}
                      />
                    </ListItem>
                  ))}
                  <ListItem>
                    <TablePaginationActions
                      count={this.state.cantidadTotalDeUsuarios}
                      page={this.state.page}
                      rowsPerPage={this.state.pageSize}
                      onChangePage={this.handlePage}
                    />
                  </ListItem>
                </List>
              </Paper>
            </Paper>
          </Grid>
          {this.state.estadisticasDeUsuario ? (
            <Grid item xs={8}>
              <Grid container direction="column" spacing={3}>
                <Grid item>
                  <Paper className={classes.paper}>
                    <Typography
                      align="center"
                      variant="h6"
                      style={{ color: "#0dd456" }}
                    >
                      Partidas Ganadas
                    </Typography>
                    <Typography
                      align="center"
                      variant="h4"
                      style={{ color: "#0dd456" }}
                    >
                      {this.state.estadisticasDeUsuario?.partidasGanadas}
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
                      Partidas Jugadas
                    </Typography>
                    <Typography
                      align="center"
                      variant="h4"
                      style={{ color: "#126ede" }}
                    >
                      {this.state.estadisticasDeUsuario?.partidasJugadas}
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
                      Racha actual
                    </Typography>
                    <Typography
                      align="center"
                      variant="h4"
                      style={{ color: "#e6350e" }}
                    >
                      {this.state.estadisticasDeUsuario?.rachaActual}
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

export default withStyles(useStyles)(EstadisticasUsuarios);

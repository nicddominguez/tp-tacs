import { CircularProgress } from "@material-ui/core";
import Button from "@material-ui/core/Button";
import FormControl from "@material-ui/core/FormControl";
import Grid from "@material-ui/core/Grid";
import Input from "@material-ui/core/Input";
import InputLabel from "@material-ui/core/InputLabel";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";
import MenuItem from "@material-ui/core/MenuItem";
import Paper from "@material-ui/core/Paper";
import Select from "@material-ui/core/Select";
import Slider from "@material-ui/core/Slider";
import Step from "@material-ui/core/Step";
import StepContent from "@material-ui/core/StepContent";
import StepLabel from "@material-ui/core/StepLabel";
import Stepper from "@material-ui/core/Stepper";
import { createStyles, Theme, withStyles } from "@material-ui/core/styles";
import TextField from "@material-ui/core/TextField";
import Typography from "@material-ui/core/Typography";
import Add from "@material-ui/icons/Add";
import Cancel from "@material-ui/icons/Cancel";
import { default as React } from "react";
import { Redirect } from "react-router-dom";
import {
  CrearPartidaBody,
  ModoDeJuegoModel,
  ProvinciaModel,
  UsuarioModel,
} from "../../api/api";
import {
  WololoPartidasApiClient,
  WololoProvinciasApiClient,
  WololoUsuariosApiClient,
} from "../../api/client";
import CreacionPartidaModal from "./CreacionPartidaModal";
import TablePaginationActions from "./TablePaginationActions";

const styles = (theme: Theme) =>
  createStyles({
    root: {
      width: "100%",
    },
    button: {
      marginTop: theme.spacing(1),
      marginBottom: theme.spacing(1),
      marginRight: theme.spacing(1),
    },
    actionsContainer: {
      marginBottom: theme.spacing(2),
      display: "flex",
    },
    resetContainer: {
      padding: theme.spacing(3),
    },
    formControl: {
      margin: theme.spacing(1),
      minWidth: 120,
    },
    rootSlider: {
      width: 250,
    },
    input: {
      width: 42,
    },
    crearButtonContainer: {
      display: "flex",
      alignItems: "center"
    },
  });

const stepNames = [
  "Seleccionar una provincia",
  "Seleccionar cantidad de municipios",
  "Seleccionar modo de juego",
  "Buscar jugadores",
];

interface SelectorProvinciasProps {
  provincias: Array<ProvinciaModel>;
  idProvinciaSeleccionada: number;
  onChange: (idProvincia: number) => void;
  classes?: any;
}

interface SelectorProvinciaState {
  idProvincia: number;
}

class SelectorProvincia extends React.Component<
  SelectorProvinciasProps,
  SelectorProvinciaState
> {
  classes: any;

  constructor(props: SelectorProvinciasProps) {
    super(props);
    this.classes = this.props.classes;

    this.state = {
      idProvincia: props.idProvinciaSeleccionada,
    };

    this.handleSelectChange = this.handleSelectChange.bind(this);
  }

  handleSelectChange(event: React.ChangeEvent<{ value: unknown }>) {
    const idProvincia = event.target.value as number;
    if (idProvincia) {
      this.setState({
        idProvincia: idProvincia,
      });
      this.props.onChange(idProvincia);
    }
  }

  render() {
    return (
      <FormControl className={this.classes.formControl}>
        <InputLabel id="provincia-label">Provincia</InputLabel>
        <Select
          labelId="provincia-label"
          id="provincia"
          onChange={this.handleSelectChange}
          value={this.state.idProvincia}
        >
          {this.props.provincias.map((provincia) => (
            <MenuItem key={provincia.id} value={provincia.id}>
              {provincia.nombre}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    );
  }
}

const SelectorProvinciaStyled = withStyles(styles)(SelectorProvincia);

interface SelectorCantidadMunicipiosProps {
  cantidadMunicipiosSeleccionada: number | string | Array<number | string>;
  cantidadMunicipios: number;
  onChange: (cantidad: number | string | Array<number | string>) => void;
  classes?: any;
}

interface SelectorCantidadMunicipiosState {
  cantidadMunicipiosSeleccionada: string | number | (string | number)[];
  cantidadMunicipios: number;
  cantidadMunicipiosMinima: number;
}

class SelectorCantidadMunicipios extends React.Component<
  SelectorCantidadMunicipiosProps,
  SelectorCantidadMunicipiosState
> {
  classes: any;

  constructor(props: SelectorCantidadMunicipiosProps) {
    super(props);
    this.classes = this.props.classes;

    this.state = {
      cantidadMunicipiosSeleccionada: props.cantidadMunicipios,
      cantidadMunicipios: props.cantidadMunicipios,
      cantidadMunicipiosMinima: 4,
    };

    this.setCantidadMunicipiosSeleccionada = this.setCantidadMunicipiosSeleccionada.bind(
      this
    );
    this.handleSliderChange = this.handleSliderChange.bind(this);
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleBlur = this.handleBlur.bind(this);
  }

  setCantidadMunicipiosSeleccionada(newValue: number | number[] | string) {
    this.setState({
      cantidadMunicipiosSeleccionada: newValue,
    });
    this.props.onChange(newValue);
  }

  handleSliderChange(event: any, newValue: number | number[]) {
    this.setCantidadMunicipiosSeleccionada(newValue);
  }

  handleInputChange(event: React.ChangeEvent<HTMLInputElement>) {
    this.setCantidadMunicipiosSeleccionada(
      event.target.value === "" ? "" : Number(event.target.value)
    );
  }

  handleBlur() {
    if (this.state.cantidadMunicipiosSeleccionada < 0) {
      this.setCantidadMunicipiosSeleccionada(0);
    } else if (
      this.state.cantidadMunicipiosSeleccionada > this.state.cantidadMunicipios
    ) {
      this.setCantidadMunicipiosSeleccionada(this.state.cantidadMunicipios);
    }
  }

  render() {
    return (
      <div className={this.classes.rootSlider}>
        <Typography id="input-slider" gutterBottom>
          Cantidad de municipios
        </Typography>
        <Grid container spacing={2} alignItems="center">
          <Grid item xs>
            <Slider
              value={
                typeof this.state.cantidadMunicipiosSeleccionada === "number"
                  ? this.state.cantidadMunicipiosSeleccionada
                  : this.state.cantidadMunicipiosMinima
              }
              onChange={this.handleSliderChange}
              aria-labelledby="input-slider"
              min={this.state.cantidadMunicipiosMinima}
              max={this.state.cantidadMunicipios}
            />
          </Grid>
          <Grid item>
            <Input
              className={this.classes.input}
              value={
                typeof this.state.cantidadMunicipiosSeleccionada === "number"
                  ? this.state.cantidadMunicipiosSeleccionada
                  : this.state.cantidadMunicipiosMinima
              }
              margin="dense"
              onChange={this.handleInputChange}
              onBlur={this.handleBlur}
              inputProps={{
                step: 1,
                min: this.state.cantidadMunicipiosMinima,
                max: this.state.cantidadMunicipios,
                type: "number",
                "aria-labelledby": "input-slider",
              }}
            />
          </Grid>
        </Grid>
      </div>
    );
  }
}

const SelectorCantidadMunicipiosStyled = withStyles(styles)(
  SelectorCantidadMunicipios
);

interface SelectorUsuarioProps {
  classes?: any;
  onChange: (usuarios: Array<UsuarioModel>) => void;
  usuarioLogueado?: UsuarioModel;
}

interface SelectorUsuarioState {
  listadoUsuarios: Array<UsuarioModel>;
  cantidadTotalDeUsuarios: number;
  usuariosSeleccionados: Array<UsuarioModel>;
  filtro: string;
  page: number;
  pageSize: number;
}

class SelectorUsuarios extends React.Component<
  SelectorUsuarioProps,
  SelectorUsuarioState
> {
  usuariosApiClient: WololoUsuariosApiClient;

  constructor(props: SelectorUsuarioProps) {
    super(props);

    this.usuariosApiClient = new WololoUsuariosApiClient();

    this.state = {
      listadoUsuarios: [],
      cantidadTotalDeUsuarios: 0,
      usuariosSeleccionados: [],
      filtro: "",
      page: 0,
      pageSize: 4,
    };

    if (this.props.usuarioLogueado != undefined) {
      this.state.usuariosSeleccionados.push(this.props.usuarioLogueado);
    }

    this.onChangeFilter = this.onChangeFilter.bind(this);
    this.handleRemoveUser = this.handleRemoveUser.bind(this);
    this.handleAddUser = this.handleAddUser.bind(this);
    this.buscar = this.buscar.bind(this);
  }

  onChangeFilter(event: React.ChangeEvent<HTMLInputElement>) {
    this.setState({
      filtro: event.target.value,
    });
  }

  handleRemoveUser(usuarioARemover: UsuarioModel) {
    return () => {
      const nuevosUsuariosSeleccionados = this.state.usuariosSeleccionados.filter(
        (usuario) => usuario.id !== usuarioARemover.id
      );
      this.setState((prevState) => {
        return {
          usuariosSeleccionados: nuevosUsuariosSeleccionados,
        };
      });
      this.props.onChange(nuevosUsuariosSeleccionados);
    };
  }

  handleAddUser(usuarioAAgregar: UsuarioModel) {
    return () => {
      if(!this.state.usuariosSeleccionados.some(u=>u.id === usuarioAAgregar.id)){
        const nuevosUsuariosSeleccionados = [...this.state.usuariosSeleccionados, usuarioAAgregar];
        this.setState({
          usuariosSeleccionados: nuevosUsuariosSeleccionados,
        });
        this.props.onChange(nuevosUsuariosSeleccionados);
      }
    };
  }

  handlePage = (
    event: React.MouseEvent<HTMLButtonElement> | null,
    newPage: number
  ) => {
    this.setState({ page: newPage }, this.buscar);
  };

  buscar() {
    this.usuariosApiClient
      .listarUsuarios(
        this.state.filtro == "" ? undefined : this.state.filtro,
        this.state.pageSize + 1,
        this.state.page
      )
      .then((response) => {
        this.setState({
          listadoUsuarios:
            response.usuarios?.filter(
              (usuario: UsuarioModel) =>
                usuario.id != this.props.usuarioLogueado?.id
            ) || [],
          cantidadTotalDeUsuarios: response.cantidadTotalDeUsuarios || 0,
        });
      })
      .catch(console.error);
  }

  renderUsuariosSeleccionados() {
    return (
      <Paper>
        <List dense component="nav" role="list">
          <ListItem key={-1}>
            <ListItemText
              id="listheader"
              primary="Jugadores"
              primaryTypographyProps={{ variant: "h6" }}
            />
          </ListItem>
          {this.state.usuariosSeleccionados.map((usuario: UsuarioModel) => (
            <ListItem key={usuario.id} role="listitem" button>
              <ListItemText
                id={`${usuario.id}-${usuario.nombreDeUsuario}`}
                primary={usuario.nombreDeUsuario}
              />
              {usuario.id != this.props.usuarioLogueado?.id ? (
                <ListItemIcon onClick={this.handleRemoveUser(usuario)}>
                  <Cancel />
                </ListItemIcon>
              ) : undefined}
            </ListItem>
          ))}
          <ListItem />
        </List>
      </Paper>
    );
  }

  renderListadoUsuarios() {
    return (
      <Paper>
        <List dense component="nav" role="list">
          <ListItem key={-1}>
            <ListItemText
              id="listheader"
              primary="Usuarios"
              primaryTypographyProps={{ variant: "h6" }}
            />
          </ListItem>
          {this.state.listadoUsuarios.map((usuario: UsuarioModel) => (
            <ListItem key={usuario.id} role="listitem" button>
              <ListItemText
                id={`${usuario.id}-${usuario.nombreDeUsuario}`}
                primary={usuario.nombreDeUsuario}
              />
              <ListItemIcon onClick={this.handleAddUser(usuario)}>
                <Add />
              </ListItemIcon>
            </ListItem>
          ))}
          <ListItem>
            <TablePaginationActions
              count={this.state.cantidadTotalDeUsuarios - 1}
              page={this.state.page}
              rowsPerPage={this.state.pageSize}
              onChangePage={this.handlePage}
            />
          </ListItem>
        </List>
      </Paper>
    );
  }

  render() {
    return (
      <div>
        <TextField
          label="Filtro"
          value={this.state.filtro}
          onChange={this.onChangeFilter}
        />
        <Button color="primary" onClick={this.buscar}>
          Buscar
        </Button>
        <Grid container spacing={2} className={this.props.classes.root}>
          <Grid item>{this.renderListadoUsuarios()}</Grid>
          <Grid item>{this.renderUsuariosSeleccionados()}</Grid>
        </Grid>
      </div>
    );
  }
}

const SelectorUsuariosStyled = withStyles(styles)(SelectorUsuarios);

interface NuevaPartidaStepperState {
  activeStep: number;
  idProvincia: number;
  provincias: Array<ProvinciaModel>;
  cantidadMunicipiosSeleccionada: number | string | Array<number | string>;
  cantidadMunicipios: number;
  modoDeJuego: ModoDeJuegoModel;
  usuariosSeleccionados: Array<UsuarioModel>;
  creacionFallida: boolean;
  creacionExitosa: boolean;
  puedeRedireccionar: boolean;
  loading: boolean;
}

interface NuevaPartidaStepperProps {
  classes?: any;
  usuarioLogueado?: UsuarioModel;
}

export class NuevaPartidaStepper extends React.Component<
  NuevaPartidaStepperProps,
  NuevaPartidaStepperState
> {
  provinciasApiClient: WololoProvinciasApiClient;
  partidasApiClient: WololoPartidasApiClient;
  classes: any;

  constructor(props: NuevaPartidaStepperProps) {
    super(props);
    this.classes = props.classes;
    this.provinciasApiClient = new WololoProvinciasApiClient();
    this.partidasApiClient = new WololoPartidasApiClient();

    this.state = {
      activeStep: 0,
      idProvincia: 6,
      provincias: [],
      cantidadMunicipiosSeleccionada: 0,
      cantidadMunicipios: 0,
      modoDeJuego: ModoDeJuegoModel.Normal,
      usuariosSeleccionados: [],
      creacionFallida: false,
      creacionExitosa: false,
      puedeRedireccionar: false,
      loading: false,
    };

    this.handleBack = this.handleBack.bind(this);
    this.handleNext = this.handleNext.bind(this);
    this.setIdProvincia = this.setIdProvincia.bind(this);
    this.setCantidadMunicipios = this.setCantidadMunicipios.bind(this);
    this.setUsuariosSeleccionados = this.setUsuariosSeleccionados.bind(this);
    this.handleModoDeJuego = this.handleModoDeJuego.bind(this);
    this.handleFinish = this.handleFinish.bind(this);
    this.crearPartida = this.crearPartida.bind(this);
    this.cerrarModalExitoso = this.cerrarModalExitoso.bind(this);
    this.cerrarModalFallido = this.cerrarModalFallido.bind(this);
  }

  componentDidMount() {
    this.provinciasApiClient
      .listarProvincias(23)
      .then((response) => {
        this.setState({
          provincias: response.provincias || [],
          cantidadMunicipiosSeleccionada: this.cantidadMunicipios(
            response.provincias || []
          ),
        });
      })
      .catch(console.error);
  }

  handleNext() {
    this.setState((prevState) => {
      return {
        activeStep: prevState.activeStep + 1,
      };
    });
  }

  handleBack() {
    this.setState((prevState) => {
      return {
        activeStep: prevState.activeStep - 1,
      };
    });
    if (this.state.activeStep === stepNames.length - 1) {
      this.crearPartida();
    }
  }

  setIdProvincia(id: number) {
    this.setState(
      {
        idProvincia: id,
      },
      () =>
        this.setCantidadMunicipios(
          this.cantidadMunicipios(this.state.provincias)
        )
    );
  }

  setCantidadMunicipios(cantidad: number | string | Array<number | string>) {
    this.setState({
      cantidadMunicipiosSeleccionada: cantidad,
    });
  }

  cantidadMunicipios = (provincias: ProvinciaModel[]) => {
    const provincia: ProvinciaModel | undefined = provincias.find(
      (provincia) => provincia.id == this.state.idProvincia
    );
    return provincia?.cantidadMunicipios || 0;
  };

  handleModoDeJuego(event: React.ChangeEvent<{ value: unknown }>) {
    this.setState({
      modoDeJuego: event.target.value as ModoDeJuegoModel,
    });
  }

  setUsuariosSeleccionados(usuarios: Array<UsuarioModel>) {
    this.setState({
      usuariosSeleccionados: usuarios,
    });
  }

  handleFinish() {
    this.crearPartida();
  }

  cerrarModalExitoso() {
    this.setState({ puedeRedireccionar: true });
  }

  cerrarModalFallido() {
    this.setState({ creacionFallida: false });
  }

  crearPartida() {
    this.setState({loading: true});
    const body: CrearPartidaBody = {
      cantidadMunicipios: this.state.cantidadMunicipiosSeleccionada as number,
      idJugadores: this.state.usuariosSeleccionados.map(
        (usuario) => usuario.id
      ),
      idProvincia: this.state.idProvincia,
      modoDeJuego: this.state.modoDeJuego,
    };
    this.partidasApiClient
      .crearPartida(body)
      .then((response) => {
        console.log("Partida creada");
        this.setState({ creacionExitosa: true, loading: false });
      })
      .catch((response) => this.setState({ creacionFallida: true, loading: false }));
  }

  renderActiveStep() {
    switch (this.state.activeStep) {
      case 0:
        return (
          <SelectorProvinciaStyled
            onChange={this.setIdProvincia}
            idProvinciaSeleccionada={this.state.idProvincia}
            provincias={this.state.provincias}
          />
        );
      case 1:
        return (
          <SelectorCantidadMunicipiosStyled
            onChange={this.setCantidadMunicipios}
            cantidadMunicipiosSeleccionada={
              this.state.cantidadMunicipiosSeleccionada
            }
            cantidadMunicipios={this.cantidadMunicipios(this.state.provincias)}
          />
        );
      case 2:
        return (
          <FormControl className={this.classes.formControl}>
            <InputLabel id="modoDeJuego-label">Modo de juego</InputLabel>
            <Select
              labelId="modoDeJuego-label"
              id="modoDeJuego"
              value={this.state.modoDeJuego}
              onChange={this.handleModoDeJuego}
            >
              <MenuItem value={ModoDeJuegoModel.Rapido}>Rápido</MenuItem>
              <MenuItem value={ModoDeJuegoModel.Normal}>Normal</MenuItem>
              <MenuItem value={ModoDeJuegoModel.Extendido}>Extendido</MenuItem>
            </Select>
          </FormControl>
        );
      case 3:
        return (
          <SelectorUsuariosStyled
            onChange={this.setUsuariosSeleccionados}
            usuarioLogueado={this.props.usuarioLogueado}
          />
        );
      default:
        return "Unknown step";
    }
  }

  render() {
    if (this.state.puedeRedireccionar) {
      return <Redirect to="/app/partidas" />;
    }
    return (
      <div className={this.classes.root}>
        <Stepper activeStep={this.state.activeStep} orientation="vertical">
          {stepNames.map((label, index) => (
            <Step key={label}>
              <StepLabel>{label}</StepLabel>
              <StepContent>
                <Typography>{this.renderActiveStep()}</Typography>
                <div className={this.classes.actionsContainer}>
                    <Button
                      disabled={this.state.activeStep === 0}
                      onClick={this.handleBack}
                      className={this.classes.button}
                    >
                      Back
                    </Button>
                    {this.state.activeStep !== stepNames.length - 1 ? (
                      <Button
                        variant="contained"
                        color="primary"
                        onClick={this.handleNext}
                        className={this.classes.button}
                      >
                        Siguiente
                      </Button>
                    ) : (
                      <div className={this.classes.crearButtonContainer}>
                        <Button
                          variant="contained"
                          color="primary"
                          onClick={this.handleFinish}
                          className={this.classes.button}
                          disabled={
                            this.state.usuariosSeleccionados.length < 2 ||
                            this.state.usuariosSeleccionados.length > 4
                          }
                        >
                          Crear
                        </Button>
                        {this.state.loading && <CircularProgress size={20} />}
                      </div>
                    )}
                </div>
              </StepContent>
            </Step>
          ))}
        </Stepper>
        <CreacionPartidaModal
          message="Creación exitosa!"
          open={this.state.creacionExitosa}
          onClose={this.cerrarModalExitoso}
        />
        <CreacionPartidaModal
          message="Algo fallo al crear la partida :("
          open={this.state.creacionFallida}
          onClose={this.cerrarModalFallido}
        />
      </div>
    );
  }
}

export default withStyles(styles)(NuevaPartidaStepper);

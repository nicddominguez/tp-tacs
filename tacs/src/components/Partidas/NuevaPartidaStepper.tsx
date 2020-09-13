import Button from "@material-ui/core/Button";
import FormControl from "@material-ui/core/FormControl";
import Grid from "@material-ui/core/Grid";
import Input from "@material-ui/core/Input";
import InputLabel from "@material-ui/core/InputLabel";
import MenuItem from "@material-ui/core/MenuItem";
import Select from "@material-ui/core/Select";
import Slider from "@material-ui/core/Slider";
import Step from "@material-ui/core/Step";
import StepContent from "@material-ui/core/StepContent";
import StepLabel from "@material-ui/core/StepLabel";
import Stepper from "@material-ui/core/Stepper";
import { createStyles, makeStyles, Theme } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import {withStyles} from '@material-ui/core/styles'
import { default as React } from "react";
import { ProvinciaModel } from "api/api";
import { WololoProvinciasApiClient } from "api/client";

const styles = (theme: Theme) =>
  createStyles({
    root: {
      width: "100%",
    },
    button: {
      marginTop: theme.spacing(1),
      marginRight: theme.spacing(1),
    },
    actionsContainer: {
      marginBottom: theme.spacing(2),
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
  }
);

const stepNames = [
  "Seleccionar una provincia",
  "Seleccionar cantidad de municipios",
  "Buscar jugadores",
];


interface SelectorProvinciasProps {
  provincias: Array<ProvinciaModel>
  idProvinciaSeleccionada: number
  onChange: (idProvincia: number) => void
  classes?: any
}

interface SelectorProvinciaState {
  idProvincia: number
}


class SelectorProvincia extends React.Component<SelectorProvinciasProps, SelectorProvinciaState> {

  classes: any

  constructor(props: SelectorProvinciasProps) {
    super(props);
    this.classes = this.props.classes;

    this.state = {
      idProvincia: props.idProvinciaSeleccionada
    };

    this.handleSelectChange = this.handleSelectChange.bind(this);
  }

  handleSelectChange(event: React.ChangeEvent<{ value: unknown }>) {
    const idProvincia = event.target.value as number;
    if (idProvincia) {
      this.setState({
        idProvincia: idProvincia
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
        {this.props.provincias.map(provincia => (
          <MenuItem key={provincia.id} value={provincia.id}>{provincia.nombre}</MenuItem>
        ))}
      </Select>
    </FormControl>
    )
  }

}

const SelectorProvinciaStyled = withStyles(styles)(SelectorProvincia);


interface SelectorCantidadMunicipiosProps {
  cantidadMunicipios: number | string | Array<number | string>
  onChange: (cantidad: number | string | Array<number | string>) => void
  classes?: any
}


class SelectorCantidadMunicipios extends React.Component<SelectorCantidadMunicipiosProps, { cantidadMunicipios: string | number | (string | number)[] }> {

  classes: any

  constructor(props: SelectorCantidadMunicipiosProps) {
    super(props);
    this.classes = this.props.classes;

    this.state = {
      cantidadMunicipios: props.cantidadMunicipios
    };

    this.setCantidadMunicipios = this.setCantidadMunicipios.bind(this);
    this.handleSliderChange = this.handleSliderChange.bind(this);
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleBlur = this.handleBlur.bind(this);
  }

  setCantidadMunicipios(newValue: number | number[] | string) {
    this.setState({
      cantidadMunicipios: newValue
    });
    this.props.onChange(newValue);
  }

  handleSliderChange(event: any, newValue: number | number[]) {
    this.setCantidadMunicipios(newValue);
  }

  handleInputChange(event: React.ChangeEvent<HTMLInputElement>) {
    this.setCantidadMunicipios(
      event.target.value === "" ? "" : Number(event.target.value)
    );
  }

  handleBlur() {
    if (this.state.cantidadMunicipios < 0) {
      this.setCantidadMunicipios(0);
    } else if (this.state.cantidadMunicipios > 100) {
      this.setCantidadMunicipios(100);
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
            value={typeof this.state.cantidadMunicipios === "number" ? this.state.cantidadMunicipios : 12}
            onChange={this.handleSliderChange}
            aria-labelledby="input-slider"
            min={12}
            max={50}
          />
        </Grid>
        <Grid item>
          <Input
            className={this.classes.input}
            value={typeof this.state.cantidadMunicipios === "number" ? this.state.cantidadMunicipios : 12}
            margin="dense"
            onChange={this.handleInputChange}
            onBlur={this.handleBlur}
            inputProps={{
              step: 1,
              min: 12,
              max: 100,
              type: "number",
              "aria-labelledby": "input-slider",
            }}
          />
        </Grid>
      </Grid>
    </div>
    )
  }

}


const SelectorCantidadMunicipiosStyled = withStyles(styles)(SelectorCantidadMunicipios);


interface NuevaPartidaStepperState {
  activeStep: number
  idProvincia: number
  provincias: Array<ProvinciaModel>
  cantidadMunicipios: number | string | Array<number | string>
  modoDeJuego: string
}


interface NuevaPartidaStepperProps {
  classes?: any
}


export class NuevaPartidaStepper extends React.Component<NuevaPartidaStepperProps, NuevaPartidaStepperState> {

  provinciasApiClient: WololoProvinciasApiClient
  classes: any

  constructor(props: NuevaPartidaStepperProps) {
    super(props);
    this.classes = props.classes;
    this.provinciasApiClient = new WololoProvinciasApiClient();

    this.state = {
      activeStep: 0,
      idProvincia: 1,
      provincias: [],
      cantidadMunicipios: 30,
      modoDeJuego: ""
    };

    this.setProvincias = this.setProvincias.bind(this);
    this.handleBack = this.handleBack.bind(this);
    this.handleNext = this.handleNext.bind(this);
    this.setIdProvincia = this.setIdProvincia.bind(this);
    this.setCantidadMunicipios = this.setCantidadMunicipios.bind(this);
  }

  setProvincias(provincias: Array<ProvinciaModel>) {
    this.setState({
      provincias: provincias
    });
  }

  componentDidMount() {
    this.provinciasApiClient.listarProvincias(23)
        .then(response => {
          this.setProvincias(response.provincias || []);
        })
        .catch(console.error);
  }

  handleNext() {
    this.setState(prevState => {
      return {
        activeStep: prevState.activeStep + 1
      }
    });
  }

  handleBack() {
    this.setState(prevState => {
      return {
        activeStep: prevState.activeStep - 1
      }
    });
  }

  setIdProvincia(id: number) {
    this.setState({
      idProvincia: id
    });
  }

  setCantidadMunicipios(cantidad: number | string | Array<number | string>) {
    this.setState({
      cantidadMunicipios: cantidad
    })
  }

  handleModoDeJuego(event: React.ChangeEvent<{ value: unknown }>) {
    this.setState({
      modoDeJuego: event.target.value as string
    });
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
            cantidadMunicipios={this.state.cantidadMunicipios}
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
              onChange={handleModoDeJuego}
            >
              <MenuItem value={"RAPIDO"}>Rápido</MenuItem>
              <MenuItem value={"NORMAL"}>Normal</MenuItem>
              <MenuItem value={"EXTENDIDO"}>Extendido</MenuItem>
            </Select>
          </FormControl>
        );
      case 3:
        return "Buscando jugadores...";
      default:
        return "Unknown step";
    }
  }

  render() {
    return (
      <div className={this.classes.root}>
      <Stepper activeStep={this.state.activeStep} orientation="vertical">
        {stepNames.map((label, index) => (
          <Step key={label}>
            <StepLabel>{label}</StepLabel>
            <StepContent>
              <Typography>{this.renderActiveStep()}</Typography>
              <div className={this.classes.actionsContainer}>
                <div>
                  <Button
                    disabled={this.state.activeStep === 0}
                    onClick={this.handleBack}
                    className={this.classes.button}
                  >
                    Back
                  </Button>
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={this.handleNext}
                    className={this.classes.button}
                  >
                    {this.state.activeStep === stepNames.length - 1 ? "Finish" : "Next"}
                  </Button>
                </div>
              </div>
            </StepContent>
          </Step>
        ))}
      </Stepper>
    </div>
    )
  }

}


export default withStyles(styles)(NuevaPartidaStepper);
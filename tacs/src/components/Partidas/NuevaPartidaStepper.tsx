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
import VolumeUp from "@material-ui/icons/VolumeUp";
import { default as React } from "react";

const useStyles = makeStyles((theme: Theme) =>
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
  })
);

function getSteps() {
  return [
    "Seleccionar una provincia",
    "Seleccionar cantidad de municipios",
    "Buscar jugadores",
  ];
}

export default function NuevaPartidaStepper() {
  const classes = useStyles();
  const steps = getSteps();
  const [activeStep, setActiveStep] = React.useState(0);
  const [provincia, setProvincia] = React.useState("");
  const [cantidadDeMunicipios, setCantidadDeMunicipios] = React.useState<
    number | string | Array<number | string>
  >(30);

  const handleNext = () => {
    setActiveStep((prevActiveStep) => prevActiveStep + 1);
  };

  const handleBack = () => {
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
  };

  const handleProvincia = (event: React.ChangeEvent<{ value: unknown }>) => {
    setProvincia(event.target.value as string);
  };

  const handleSliderChange = (event: any, newValue: number | number[]) => {
    setCantidadDeMunicipios(newValue);
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCantidadDeMunicipios(
      event.target.value === "" ? "" : Number(event.target.value)
    );
  };

  const handleBlur = () => {
    if (cantidadDeMunicipios < 0) {
      setCantidadDeMunicipios(0);
    } else if (cantidadDeMunicipios > 100) {
      setCantidadDeMunicipios(100);
    }
  };

  const getStepContent = (step: number) => {
    switch (step) {
      case 0:
        return (
          <FormControl className={classes.formControl}>
            <InputLabel id="provincia-label">Provincia</InputLabel>
            <Select
              labelId="provincia-label"
              id="provincia"
              value={provincia}
              onChange={handleProvincia}
            >
              <MenuItem value={10}>Buenos Aires</MenuItem>
              <MenuItem value={20}>Santa Fe</MenuItem>
              <MenuItem value={30}>La Pampa</MenuItem>
              <MenuItem value={40}>Cordoba</MenuItem>
              <MenuItem value={50}>Entre Rios</MenuItem>
              <MenuItem value={60}>Rio Negro</MenuItem>
            </Select>
          </FormControl>
        );
      case 1:
        return (
          <div className={classes.rootSlider}>
            <Typography id="input-slider" gutterBottom>
              Cantidad de municipios
            </Typography>
            <Grid container spacing={2} alignItems="center">
              <Grid item xs>
                <Slider
                  value={
                    typeof cantidadDeMunicipios === "number"
                      ? cantidadDeMunicipios
                      : 0
                  }
                  onChange={handleSliderChange}
                  aria-labelledby="input-slider"
                  min={12}
                  max={50}
                />
              </Grid>
              <Grid item>
                <Input
                  className={classes.input}
                  value={cantidadDeMunicipios}
                  margin="dense"
                  onChange={handleInputChange}
                  onBlur={handleBlur}
                  inputProps={{
                    step: 1,
                    min: 12,
                    max: 50,
                    type: "number",
                    "aria-labelledby": "input-slider",
                  }}
                />
              </Grid>
            </Grid>
          </div>
        );
      case 2:
        return "Buscando jugadores...";
      default:
        return "Unknown step";
    }
  };

  return (
    <div className={classes.root}>
      <Stepper activeStep={activeStep} orientation="vertical">
        {steps.map((label, index) => (
          <Step key={label}>
            <StepLabel>{label}</StepLabel>
            <StepContent>
              <Typography>{getStepContent(index)}</Typography>
              <div className={classes.actionsContainer}>
                <div>
                  <Button
                    disabled={activeStep === 0}
                    onClick={handleBack}
                    className={classes.button}
                  >
                    Back
                  </Button>
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={handleNext}
                    className={classes.button}
                  >
                    {activeStep === steps.length - 1 ? "Finish" : "Next"}
                  </Button>
                </div>
              </div>
            </StepContent>
          </Step>
        ))}
      </Stepper>
    </div>
  );
}

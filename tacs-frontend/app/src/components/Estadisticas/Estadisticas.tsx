import { Button, Container, Grid, Paper, Typography } from "@material-ui/core";
import { createStyles, Theme, withStyles } from "@material-ui/core/styles";
import React from "react";
import { Link } from "react-router-dom";

const useStyles = (theme: Theme) =>
  createStyles({
    container: {
      paddingTop: theme.spacing(4),
      paddingLeft: theme.spacing(40),
      paddingRight: theme.spacing(40),
      paddingBottom: theme.spacing(5),
    },
    paper: {
      padding: theme.spacing(2),
    },
    header: {
      paddingBottom: theme.spacing(1),
    },
    link: {
      textDecoration: "none",
    },
  });

interface Props {
  classes: any;
}

interface State {}

class Estadisticas extends React.Component<Props, State> {
  render() {
    const classes = this.props.classes;
    return (
      <Container maxWidth="lg" className={classes.container}>
        <Paper className={classes.paper}>
          <Grid
            container
            direction="column"
            spacing={3}
            className={classes.paper}
          >
            <Typography align="center" variant="h5" className={classes.header}>
              Estadísticas
            </Typography>
            <Grid item xs={12}>
              <Link to="/app/estadisticas/usuarios" className={classes.link}>
                <Button
                  size="small"
                  variant="contained"
                  className={classes.margin}
                  color="primary"
                  fullWidth
                >
                  Estadísticas de Usuarios
                </Button>
              </Link>
            </Grid>
            <Grid item xs={12}>
              <Link to="/app/estadisticas/partidas" className={classes.link}>
                <Button
                  size="small"
                  variant="contained"
                  className={classes.margin}
                  color="secondary"
                  fullWidth
                >
                  Estadísticas de Partidas
                </Button>
              </Link>
            </Grid>
            <Grid item xs={12}>
              <Link to="/app/estadisticas/scoreboard" className={classes.link}>
                <Button
                  size="small"
                  variant="contained"
                  className={classes.margin}
                  fullWidth
                >
                  Scoreboard
                </Button>
              </Link>
            </Grid>
          </Grid>
        </Paper>
      </Container>
    );
  }
}

export default withStyles(useStyles)(Estadisticas);

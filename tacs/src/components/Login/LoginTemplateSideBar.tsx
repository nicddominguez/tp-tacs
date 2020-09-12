import { Button } from "@material-ui/core";
import Avatar from "@material-ui/core/Avatar";
import CssBaseline from "@material-ui/core/CssBaseline";
import Grid from "@material-ui/core/Grid";
import Paper from "@material-ui/core/Paper";
import { makeStyles } from "@material-ui/core/styles";
import LockOpenIcon from "@material-ui/icons/LockOpen";
import mapFromArgentina from "assets/img/argentina.png";
import logo from "assets/img/logo2.png";
import LoginButton from "components/Login/LoginButton";
import SignUpButton from "components/Login/SignUpButton";
import React from "react";
import { UsuarioModel } from "api/api";

const useStyles = makeStyles((theme) => ({
  root: {
    height: "100vh",
  },
  image: {
    // TODO: Usar una collection propia
    backgroundImage: "url(https://source.unsplash.com/collection/10417678)",
    backgroundRepeat: "no-repeat",
    backgroundColor:
      theme.palette.type === "light"
        ? theme.palette.grey[50]
        : theme.palette.grey[900],
    backgroundSize: "cover",
    backgroundPosition: "center",
  },
  paper: {
    margin: theme.spacing(8, 4),
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    opacity: 1,
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.text.disabled,
  },
}));

export interface LoginTemplateSideBarProps {
  flagLoggedIn: (user: UsuarioModel) => void;
}

export default function LoginTemplateSideBar(props: LoginTemplateSideBarProps) {
  const classes = useStyles();

  return (
    <Grid container component="main" className={classes.root}>
      <CssBaseline />
      <Grid item xs={false} sm={4} md={8} className={classes.image} />
      <Grid
        item
        xs={12}
        sm={8}
        md={4}
        component={Paper}
        elevation={6}
        square
        style={{
          boxShadow: "0px 0px 129px 74px rgba(0,0,0,0.8)",
          background:
            "url(" +
            mapFromArgentina +
            ") center no-repeat rgba(255, 255, 255)",
          backgroundSize: "100%",
        }}
      >
        <div className={classes.paper}>
          <div style={{ margin: "50px" }}>
            <img src={logo} alt="Wololo logo"></img>
          </div>
          <div style={{ marginBottom: "10px" }}>
            <Avatar className={classes.avatar}>
              <LockOpenIcon></LockOpenIcon>
            </Avatar>
          </div>
          <div style={{ marginBottom: "10px", width: "100%" }}>
            <LoginButton flagLoggedIn={props.flagLoggedIn} />
          </div>
          <div style={{ marginBottom: "10px", width: "100%" }}>
            <SignUpButton flagLoggedIn={props.flagLoggedIn} />
          </div>
          <div style={{ width: "100%" }}>
            <Button
              variant="outlined"
              fullWidth
              href="https://github.com/mesaglio/tp-tacs/"
              style={{ textDecoration: "none" }}
              target="_blank"
            >
              Seguinos en Github
            </Button>
          </div>
        </div>
      </Grid>
    </Grid>
  );
}

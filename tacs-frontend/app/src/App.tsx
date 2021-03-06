import LoginTemplateSideBar from "./components/Login/LoginTemplateSideBar";
import React from "react";
import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom";
import Dashboard from "./components/Dashboard/Dashboard";
import { BaseWololoApiClient } from "./api/client";
import { UsuarioModel } from "./api/api";

export default function App() {

  const baseApiClient = new BaseWololoApiClient();
  const [isLoggedIn, setLoggedIn] = React.useState(baseApiClient.userIsLoggedIn());
  const [currentUser, setCurrentUser] = React.useState(undefined as UsuarioModel | undefined);

  const setUserLoggedIn = (user: UsuarioModel) => {
    setLoggedIn(true);
    setCurrentUser(user);
  };
  const setUserLoggedOut = () => setLoggedIn(false);

  return (
    <Router>
      <Switch>
        <Route path="/login">
          <LoginTemplateSideBar
            flagLoggedIn={setUserLoggedIn} />
        </Route>
        <Route path="/app">
          <Dashboard
            flagLoggedOut={setUserLoggedOut}
          />
        </Route>
      </Switch>
      {isLoggedIn ? <Redirect to="/app/partidas" /> : <Redirect to="/login" />}
    </Router>
  );
}

import React from "react";
import {
  GoogleLogin,
  GoogleLoginResponse,
  GoogleLoginResponseOffline
} from "react-google-login";

import { WololoAuthApiClient } from "api/client";
import { NuevoJWTModel, UsuarioModel } from "api/api";

const CLIENT_ID =
  "475854452552-js6cnac0fjktpav4dcp570860l7etar8.apps.googleusercontent.com";

export interface AuthButtonProps {
  flagLoggedIn: (user: UsuarioModel) => void;
  onLoginFailure: () => void;
};

export default abstract class AuthButton extends React.Component<AuthButtonProps, {}> {

  protected authApiClient = new WololoAuthApiClient();

  constructor(props: AuthButtonProps) {
    super(props);

    this.handleGoogleLogin = this.handleGoogleLogin.bind(this);
    this.handleGoogleLoginFailure = this.handleGoogleLoginFailure.bind(this);
  }

  handleGoogleLogin(response: GoogleLoginResponse | GoogleLoginResponseOffline) {
    const loginResponse = response as GoogleLoginResponse;
    const idToken = loginResponse.tokenObj.id_token;

    this.doBackendAuthentication(idToken)
      .then(nuevoJwtModel => this.props.flagLoggedIn(nuevoJwtModel.usuario))
      .catch(_ => this.props.onLoginFailure());

  }

  handleGoogleLoginFailure(response?: GoogleLoginResponse) {
    this.props.onLoginFailure();
  }

  abstract doBackendAuthentication(idToken: string): Promise<NuevoJWTModel>

  abstract renderButton(renderProps: {onClick: () => void, disabled?: boolean}): JSX.Element

  render() {
    return (
      <React.Fragment>
          <GoogleLogin
            clientId={CLIENT_ID}
            buttonText="Login"
            onSuccess={this.handleGoogleLogin}
            onFailure={this.handleGoogleLoginFailure}
            cookiePolicy={"single_host_origin"}
            responseType="code,token"
            render={this.renderButton}
          />
      </React.Fragment>
    );
  }
}

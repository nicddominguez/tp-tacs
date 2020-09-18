import React from 'react';
import { Button } from '@material-ui/core';

import AuthButton from './AuthButton';
import { NuevoJWTModel } from "api/api";


export default class SignUpButton extends AuthButton {

    renderButton(renderProps: { onClick: () => void, disabled?: boolean }): JSX.Element {
        return (
            <Button
                disabled={renderProps.disabled}
                onClick={renderProps.onClick}
                type="submit"
                fullWidth
                variant="contained"
                color="secondary"
              >
                Registrate con Google
              </Button>
        )
    }

    doBackendAuthentication(idToken: string): Promise<NuevoJWTModel> {
        return this.authApiClient.signUserUp(idToken);
    }

}
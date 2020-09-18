import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Modal from '@material-ui/core/Modal';
import Backdrop from '@material-ui/core/Backdrop';
import Fade from '@material-ui/core/Fade';
import { Card, CardContent, Typography, CardActions, Button } from '@material-ui/core';

const useStyles = makeStyles((theme) => ({
  modal: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  paper: {
    backgroundColor: theme.palette.background.paper,
    boxShadow: theme.shadows[5],
  },
  title: {
    fontSize: 20,
  },
  root: {
    minWidth: 275,
  },
}));

export default function AuthFailureModal(props: {open: boolean, setOpen: (status: boolean) => void}) {
  const classes = useStyles();

  const handleClose = () => {
    props.setOpen(false);
  };

  return (
    <div>
      <Modal
        aria-labelledby="transition-modal-title"
        aria-describedby="transition-modal-description"
        className={classes.modal}
        open={props.open}
        onClose={handleClose}
        closeAfterTransition
        BackdropComponent={Backdrop}
        BackdropProps={{
          timeout: 500,
        }}
      >
        <Fade in={props.open}>
          <div className={classes.paper}>
            <Card className={classes.root} variant="outlined">
                <CardContent>
                    <Typography className={classes.title} color="textSecondary" gutterBottom>
                        ¡Algo salió mal! 😟
                    </Typography>
                    <Typography variant="body2" component="p">
                        Volvé a intentar más tarde
                    </Typography>
                </CardContent>
                <CardActions>
                    <Button size="small" onClick={handleClose}>Cerrar</Button>
                </CardActions>
            </Card>
          </div>
        </Fade>
      </Modal>
    </div>
  );
}
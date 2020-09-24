import {
  Button, Card,


  CardActions, CardContent,
  Typography
} from "@material-ui/core";
import Backdrop from "@material-ui/core/Backdrop";
import Fade from "@material-ui/core/Fade";
import Modal from "@material-ui/core/Modal";
import { makeStyles } from "@material-ui/core/styles";
import React from "react";

const useStyles = makeStyles((theme) => ({
  modal: {
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
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

export default function CreacionPartidaModal(props: {
  message: string;
  open: boolean;
  onClose: () => void;
}) {
  const classes = useStyles();

  const handleClose = () => {
    props.onClose();
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
                <Typography variant="body1">{props.message}</Typography>
              </CardContent>
              <CardActions>
                <Button size="small" onClick={handleClose}>
                  Cerrar
                </Button>
              </CardActions>
            </Card>
          </div>
        </Fade>
      </Modal>
    </div>
  );
}

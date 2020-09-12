import Divider from "@material-ui/core/Divider";
import Drawer from "@material-ui/core/Drawer";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";
import { makeStyles } from "@material-ui/core/styles";
import CloseIcon from "@material-ui/icons/Close";
import clsx from "clsx";
import React from "react";
import NuevaPartidaStepper from "./NuevaPartidaStepper";

const useStyles = makeStyles({
  list: {
    width: 250,
  },
  fullList: {
    width: "auto",
  },
});

interface Props {
  open: boolean;
  toggleDrawer: (
    open: boolean
  ) => (event: React.KeyboardEvent | React.MouseEvent) => void;
}

export default function NuevaPartidaDrawer({ open, toggleDrawer }: Props) {
  const classes = useStyles();

  return (
    <Drawer anchor={"bottom"} open={open} onClose={toggleDrawer(false)}>
      <div
        className={clsx(classes.list, [classes.fullList])}
        role="presentation"
        onKeyDown={toggleDrawer(false)}
      >
        <NuevaPartidaStepper></NuevaPartidaStepper>
        <Divider />
        <List>
          <ListItem button key={"Cancelar"} onClick={toggleDrawer(false)}>
            <ListItemIcon>
              <CloseIcon />
            </ListItemIcon>
            <ListItemText primary={"Cancelar"} />
          </ListItem>
        </List>
      </div>
    </Drawer>
  );
}

import React from "react";
import { makeStyles } from "@material-ui/core/styles";

const useStyles = makeStyles({
  container: {
    border: "0px",
    backgroundColor: "green",
    pointerEvents: "none",
    zIndex: 42342424,
    position: "fixed",
    bottom: "0px",
    width: "410px",
    height: "700px",
    overflow: "hidden",
    opacity: 1,
    maxWidth: "100%",
    right: "0px",
    maxHeight: "100%",
  },
  body: {
    backgroundColor: "red",
    position: "relative",
    top: "100px",
    height: "100%",
  },
});

export default function Chat() {
  const classes = useStyles();
  return (
    <div className={classes.container}>
      <div className={classes.body}></div>
    </div>
  );
}

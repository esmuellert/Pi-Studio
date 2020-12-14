import { Grid, makeStyles, Button } from "@material-ui/core";
import React from "react";

const useStyles = makeStyles({
  img: {
    maxWidth: "100%",
    maxHeight: "100%",
    height: "auto",
  },
  box: {
    padding: "10px",
  },
});
export default function Images(props) {
  const classes = useStyles();
  return (
    <Grid container spacing={3} justify="center" alignItems="center">
      {props.images.map((image) => (
        <Grid item sm={6} xs={12} container key={image}>
          <Grid item xs={12} className={classes.box}>
            {" "}
            <img
              className={classes.img}
              src={process.env.REACT_APP_S3_URL + image + ".png"}
              alt={image}
            />
          </Grid>
          <Grid item container xs={6} justify="center" alignItems="center">
            <Button variant="contained" color="primary" component="label">
              <input
                name={image}
                type="file"
                hidden
              />
              替换
            </Button>
          </Grid>
          <Grid item container xs={6} justify="center" alignItems="center">
            <Button variant="contained" color="secondary" id={image} onClick={props.onDeleteImage}>
              删除
            </Button>
          </Grid>
        </Grid>
      ))}
    </Grid>
  );
}

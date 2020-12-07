import React, { useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import { MessageList } from "react-chat-elements";
import "react-chat-elements/dist/main.css";

const useStyles = makeStyles({
  container: {
    border: "0px",
    backgroundColor: "transparent",
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
    display: "flex",
    flexDirection: "column",
  },
  bodyWrapper: {
    position: "relative",
    height: "100%",
    display: "flex",
    margin: "100px 25px 20px 20px",
    flexDirection: "column",
  },
  head: {},
  body: {
    background: "rgb(234, 238, 243)",
    padding: "20px",
    height: "100%",
    display: "flex",
    borderRadius:"10px",
  },
  textArea: {},
});

export default function Chat() {
  const classes = useStyles();
  const [messages, setMessages] = useState([
    {
      position: "right",
      type: "text",
      text: "Lorem ipsum dolor sit amet, consectetur adipisicing elit",
      date: new Date("2020-12-04T12:03:09.737"),
    },
    {
      position: "right",
      type: "text",
      text: "Lorem ipsum dolor sit amet, consectetur adipisicing elit",
      date: new Date("2020-12-04T12:03:09.737"),
    },
    {
      position: "left",
      type: "text",
      text: "Lorem ipsum dolor sit amet, consectetur adipisicing elit",
      date: new Date("2020-12-07T12:03:09.737"),
    },
    {
      position: "right",
      type: "text",
      text: "Lorem ipsum dolor sit amet, consectetur adipisicing elit",
      date: new Date("2020-12-06T12:03:09.737"),
    },
  ]);
  return (
    <div className={classes.container}>
      <div className={classes.bodyWrapper}>
        <div className={classes.body}>
          {" "}
          <MessageList
            className="message-list"
            lockable={true}
            toBottomHeight={"100%"}
            dataSource={messages}
          />
        </div>
      </div>
    </div>
  );
}

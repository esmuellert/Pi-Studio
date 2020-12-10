import React, {  } from "react";
import { makeStyles } from "@material-ui/core/styles";
import { MessageList } from "react-chat-elements";
import "react-chat-elements/dist/main.css";
import Avatar from "@material-ui/core/Avatar";
import chatPng from "./chat.png";
import { Typography, Button, TextField } from "@material-ui/core";
import CloseIcon from "@material-ui/icons/Close";
import SendIcon from "@material-ui/icons/Send";
const useStyles = makeStyles({
  container: {
    border: "0px",
    backgroundColor: "transparent",
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
    margin: "80px 25px 20px 20px",
    boxShadow: "0 4px 16px rgba(0,0,0,.25)",
    borderRadius: "15px",
    overflow: "hidden",
    display: "flex",
    flexDirection: "column",
  },
  head: {
    display: "flex",
    background: "rgb(255, 255, 255)",
    padding: "0 20px",
    flex: "0 0 80px",
    alignItems: "center",
  },
  avatarWrapper: {
    marginRight: "22px",
  },
  closeWrapper: {
    position: "absolute",
    top: "18px",
    right: "18px",
  },
  closeButton: {
    padding: 0,
    minWidth: 0,
  },
  body: {
    background: "rgb(234, 238, 243)",
    padding: "10px",
    flex: "1 1 auto",
    display: "flex",
    overflowY: "auto",
  },
  messageList: {
    width: "100%",
  },
  textWrapper: {
    display: "flex",
    background: "rgb(255, 255, 255)",
    flex: "0 0 60px",
    alignItems: "center",
    borderTop: "1px solid",
    borderTopColor: "rgb(234,234,234)",
  },
  textArea: { padding: "0 0 5px 20px", flex: "1 0 auto" },
});

export default function Chat(props) {
  const classes = useStyles();

  return (
    <div className={classes.container}>
      <div className={classes.bodyWrapper}>
        <div className={classes.head}>
          <div className={classes.closeWrapper}>
            <Button className={classes.closeButton} onClick={props.onClose}>
              <CloseIcon />
            </Button>
          </div>
          <div className={classes.avatarWrapper}>
            <Avatar variant="square" alt="Chat Icon" src={chatPng} />
          </div>

          <Typography variant="h6">Order {props.id}</Typography>
        </div>
        <div className={classes.body}>
          <MessageList
            className={classes.messageList}
            lockable={true}
            dataSource={props.messages}
            toBottomHeight="0"
          />
        </div>
        <div className={classes.textWrapper}>
          <div className={classes.textArea}>
            {" "}
            <TextField
              id="standard-basic"
              label="Message"
              fullWidth
              multiline
              onChange={props.onMessageTextChange}
              value={props.messageText}
              onKeyDown={props.onEnterDown}
            />
          </div>

          <Button onClick={props.onSendMessage}>
            <SendIcon />
          </Button>
        </div>
      </div>
    </div>
  );
}

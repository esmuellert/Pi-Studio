import React, { useState } from "react";
import clsx from "clsx";
import { makeStyles } from "@material-ui/core/styles";
import CssBaseline from "@material-ui/core/CssBaseline";
import Drawer from "@material-ui/core/Drawer";
import Box from "@material-ui/core/Box";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import List from "@material-ui/core/List";
import Typography from "@material-ui/core/Typography";
import Divider from "@material-ui/core/Divider";
import IconButton from "@material-ui/core/IconButton";
import Badge from "@material-ui/core/Badge";
import Container from "@material-ui/core/Container";
import Grid from "@material-ui/core/Grid";
import Paper from "@material-ui/core/Paper";
import MenuIcon from "@material-ui/icons/Menu";
import ChevronLeftIcon from "@material-ui/icons/ChevronLeft";
import NotificationsIcon from "@material-ui/icons/Notifications";
import { mainListItems, secondaryListItems } from "../common/listItems";
import OrdersList from "./OrdersList";
import Copyright from "../common/Copyright";
import Chat from "../chat/Chat";
import axios from "axios";
import { formatMessage } from "../common/utils";

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
  root: {
    display: "flex",
  },
  toolbar: {
    paddingRight: 24, // keep right padding when drawer closed
  },
  toolbarIcon: {
    display: "flex",
    alignItems: "center",
    justifyContent: "flex-end",
    padding: "0 8px",
    ...theme.mixins.toolbar,
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(["width", "margin"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  appBarShift: {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(["width", "margin"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  menuButton: {
    marginRight: 36,
  },
  menuButtonHidden: {
    display: "none",
  },
  title: {
    flexGrow: 1,
  },
  drawerPaper: {
    position: "relative",
    whiteSpace: "nowrap",
    width: drawerWidth,
    transition: theme.transitions.create("width", {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  drawerPaperClose: {
    overflowX: "hidden",
    transition: theme.transitions.create("width", {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    width: theme.spacing(7),
    [theme.breakpoints.up("sm")]: {
      width: theme.spacing(9),
    },
  },
  appBarSpacer: theme.mixins.toolbar,
  content: {
    flexGrow: 1,
    height: "100vh",
    overflow: "auto",
  },
  container: {
    paddingTop: theme.spacing(4),
    paddingBottom: theme.spacing(4),
  },
  paper: {
    padding: theme.spacing(2),
    display: "flex",
    overflow: "auto",
    flexDirection: "column",
  },
  fixedHeight: {
    height: 240,
  },
}));

export default function Orders() {
  const classes = useStyles();
  const [drawOpen, setDrawOpen] = React.useState(false);
  const handleDrawerOpen = () => {
    setDrawOpen(true);
  };
  const handleDrawerClose = () => {
    setDrawOpen(false);
  };

  const [chatOrderNumber, setChatOrderNumber] = useState();
  const handleOpenChat = (event) => {
    axios
      .get(`${process.env.REACT_APP_BACKEND_URL}/message/${event.currentTarget.id}`, {
        headers: {
          Authorization: localStorage.getItem("token"),
        },
      })
      .then((response) => {
        setMessages(formatMessage(response.data));
      })
      .catch((error) => {
        console.log(error);
      });
    setChatOrderNumber(event.currentTarget.id);
    setDisplayChatWidget(true);
    console.log(event.currentTarget);
  };

  const handleCloseChat = () => {
    setMessages([]);
    setDisplayChatWidget(false);
  };

  const handleSendMessage = () => {
    axios
      .post(
        `${process.env.REACT_APP_BACKEND_URL}/message`,
        {
          orderNumber: chatOrderNumber,
          message: messageText,
        },
        {
          headers: {
            Authorization: localStorage.getItem("token"),
          },
        }
      )
      .then(() => {
        let messageList = messages.map((x) => x);
        messageList.push({
          position: "right",
          type: "text",
          text: messageText,
          date: new Date(),
        });
        setMessages(messageList);
        setMessageText("");
      })
      .catch((error) => {
        console.log(error);
        alert("发送失败，请重试！");
      });
  };

  const [displayChatWidget, setDisplayChatWidget] = useState(false);
  const [messages, setMessages] = useState([
    {
      position: "right",
      type: "text",
      text: "Lor",
      date: new Date("2020-12-04T12:03:09.737"),
    },
    {
      position: "right",
      type: "text",
      text: "Lor",
      date: new Date("2020-12-04T12:03:09.737"),
    },
    {
      position: "right",
      type: "text",
      text: "Lor",
      date: new Date("2020-12-04T12:03:09.737"),
    },
    {
      position: "right",
      type: "text",
      text: "Lor",
      date: new Date("2020-12-04T12:03:09.737"),
    },
    {
      position: "right",
      type: "text",
      text: "Lor",
      date: new Date("2020-12-04T12:03:09.737"),
    },
    {
      position: "right",
      type: "text",
      text: "Lor",
      date: new Date("2020-12-04T12:03:09.737"),
    },
    {
      position: "right",
      type: "text",
      text: "Lor",
      date: new Date("2020-12-04T12:03:09.737"),
    },
    {
      position: "right",
      type: "text",
      text: "Lor",
      date: new Date("2020-12-04T12:03:09.737"),
    },
    {
      position: "right",
      type: "text",
      text: "Lor",
      date: new Date("2020-12-04T12:03:09.737"),
    },
    {
      position: "left",
      type: "text",
      text: "Lor",
      date: new Date("2020-12-07T12:03:09.737"),
    },
    {
      position: "right",
      type: "text",
      text: "Lor",
      date: new Date("2020-12-06T12:03:09.737"),
    },
    {
      position: "left",
      type: "text",
      text: "Lor",
      date: new Date("2020-12-07T12:03:09.737"),
    },
    {
      position: "right",
      type: "text",
      text: "Lor",
      date: new Date("2020-12-06T12:03:09.737"),
    },
    {
      position: "left",
      type: "text",
      text: "Lor",
      date: new Date("2020-12-07T12:03:09.737"),
    },
    {
      position: "right",
      type: "text",
      text: "Lor",
      date: new Date("2020-12-06T12:03:09.737"),
    },
    {
      position: "left",
      type: "text",
      text: "Lor",
      date: new Date("2020-12-07T12:03:09.737"),
    },
    {
      position: "right",
      type: "text",
      text: "Lor",
      date: new Date("2020-12-06T12:03:09.737"),
    },
  ]);
  const [messageText, setMessageText] = useState("");
  const handleMessageTextChange = (event) => {
    setMessageText(event.target.value);
  };

  const handleEnterDown = (event) => {
    if (event.keyCode === 13) {
      event.preventDefault();
      handleSendMessage();
    }
  };
  return (
    <div className={classes.root}>
      <CssBaseline />
      <AppBar
        position="absolute"
        className={clsx(classes.appBar, drawOpen && classes.appBarShift)}
      >
        <Toolbar className={classes.toolbar}>
          <IconButton
            edge="start"
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawerOpen}
            className={clsx(
              classes.menuButton,
              drawOpen && classes.menuButtonHidden
            )}
          >
            <MenuIcon />
          </IconButton>
          <Typography
            component="h1"
            variant="h6"
            color="inherit"
            noWrap
            className={classes.title}
          >
            Orders
          </Typography>
          <IconButton color="inherit">
            <Badge badgeContent={4} color="secondary">
              <NotificationsIcon />
            </Badge>
          </IconButton>
        </Toolbar>
      </AppBar>
      <Drawer
        variant="permanent"
        classes={{
          paper: clsx(
            classes.drawerPaper,
            !drawOpen && classes.drawerPaperClose
          ),
        }}
        open={drawOpen}
      >
        <div className={classes.toolbarIcon}>
          <IconButton onClick={handleDrawerClose}>
            <ChevronLeftIcon />
          </IconButton>
        </div>
        <Divider />
        <List>{mainListItems}</List>
        <Divider />
        <List>{secondaryListItems}</List>
      </Drawer>
      <main className={classes.content}>
        <div className={classes.appBarSpacer} />
        <Container maxWidth="lg" className={classes.container}>
          <Grid container spacing={3}>
            {/* Recent Orders */}
            <Grid item xs={12}>
              <Paper className={classes.paper}>
                <OrdersList onClickChatIcon={handleOpenChat} />
              </Paper>
            </Grid>
          </Grid>
          <Box pt={4}>
            <Copyright />
          </Box>
        </Container>
      </main>
      {displayChatWidget ? (
        <Chat
          id={chatOrderNumber}
          onClose={handleCloseChat}
          messages={messages}
          onSendMessage={handleSendMessage}
          onMessageTextChange={handleMessageTextChange}
          messageText={messageText}
          onEnterDown={handleEnterDown}
        />
      ) : null}
    </div>
  );
}

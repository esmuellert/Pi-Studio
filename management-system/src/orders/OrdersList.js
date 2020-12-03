import React, { useState, useEffect } from "react";
import Link from "@material-ui/core/Link";
import { makeStyles } from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Title from "../common/Title";
import axios from "axios";
import { url } from "../common/utils";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemIcon from "@material-ui/core/ListItemIcon";
import ListItemText from "@material-ui/core/ListItemText";
import Collapse from "@material-ui/core/Collapse";
import InboxIcon from "@material-ui/icons/MoveToInbox";
import DraftsIcon from "@material-ui/icons/Drafts";
import SendIcon from "@material-ui/icons/Send";
import ExpandLess from "@material-ui/icons/ExpandLess";
import ExpandMore from "@material-ui/icons/ExpandMore";
import StarBorder from "@material-ui/icons/StarBorder";

// Generate Order Data
function createData(
  id,
  orderDate,
  status,
  wechatId,
  phone,
  type,
  schedule,
  lastMessageTime
) {
  return {
    id,
    orderDate,
    status,
    wechatId,
    phone,
    type,
    schedule,
    lastMessageTime,
  };
}

function preventDefault(event) {
  event.preventDefault();
}

const useStyles = makeStyles((theme) => ({
  seeMore: {
    marginTop: theme.spacing(3),
  },
}));

export default function Orders() {
  const classes = useStyles();
  const [rows, setRows] = useState([]);
  const [open, setOpen] = React.useState(false);
  useEffect(() => {
    let ignore = false;
    async function request() {
      await axios
        .get(`${url}/order`, {
          headers: {
            Authorization: localStorage.getItem("token"),
          },
        })
        .then((response) => {
          if (!ignore) {
            setRows(response.data);
            console.log(response.data);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    }
    request();
    return () => {
      ignore = true;
    };
  }, []);
  const handleClick = () => {
    setOpen(!open);
  };
  return (
    <React.Fragment>
      <Title>Recent Orders</Title>
      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>订单号</TableCell>
            <TableCell>下单时间</TableCell>
            <TableCell>订单状态</TableCell>
            <TableCell>微信号</TableCell>
            <TableCell>电话</TableCell>
            <TableCell>拍摄类型</TableCell>
            <TableCell>预约时间</TableCell>
            <TableCell>最后留言时间</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow key={row.orderNumber}>
              <TableCell>{row.orderNumber}</TableCell>
              <TableCell>{row.orderedTime}</TableCell>
              <TableCell>{row.orderStatus}</TableCell>
              <TableCell>{row.wechatId}</TableCell>
              <TableCell>{row.phoneNumber}</TableCell>
              <TableCell>{row.type}</TableCell>
              <TableCell>
                <ListItem button onClick={handleClick}>
                  <ListItemText primary="Inbox" />
                  {open ? <ExpandLess /> : <ExpandMore />}
                </ListItem>
                <Collapse in={open} timeout="auto" unmountOnExit>
                  <List component="div" disablePadding>
                    <ListItem button className={classes.nested}>
                      <ListItemText primary="Starred" />
                    </ListItem>
                  </List>
                </Collapse>
              </TableCell>
              <TableCell>{row.lastMessageTime}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
      <div className={classes.seeMore}>
        <Link color="primary" href="#" onClick={preventDefault}>
          See more orders
        </Link>
      </div>
    </React.Fragment>
  );
}

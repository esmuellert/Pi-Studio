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
import { url, formatFullTime, convertStatus } from "../common/utils";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import Collapse from "@material-ui/core/Collapse";
import ExpandLess from "@material-ui/icons/ExpandLess";
import ExpandMore from "@material-ui/icons/ExpandMore";

// Generate Order Data

function preventDefault(event) {
  event.preventDefault();
}

const useStyles = makeStyles((theme) => ({
  seeMore: {
    marginTop: theme.spacing(3),
  },
}));

export default function OrdersList() {
  const classes = useStyles();
  const [rows, setRows] = useState([]);
  const [open, setOpen] = useState({});
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
            response.data = response.data.map((data) => {
              data.orderedTime = formatFullTime(data.orderedTime);
              data.schedule = data.schedule.map((schedule) => {
                schedule = formatFullTime(schedule);
                return schedule;
              });
              data.orderStatus = convertStatus(data.orderStatus)
              return data;
            });
            let opens = {};
            response.data.forEach((element) => {
              opens[element.orderNumber] = false;
            });
            setOpen(opens);
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
  const handleClick = (e) => {
    let opens = Object.assign({}, open);
    opens[e.currentTarget.id] = !opens[e.currentTarget.id];
    setOpen(opens);

  };
  return (
    <React.Fragment>
      <Title>Recent Orders</Title>
      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell align="center">订单号</TableCell>
            <TableCell align="center">下单时间</TableCell>
            <TableCell align="center">订单状态</TableCell>
            <TableCell align="center">微信号</TableCell>
            <TableCell align="center">电话</TableCell>
            <TableCell align="center">拍摄类型</TableCell>
            <TableCell align="center">预约时间</TableCell>
            <TableCell align="center">最后留言时间</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow key={row.orderNumber}>
              <TableCell align="center">{row.orderNumber}</TableCell>
              <TableCell align="center">{row.orderedTime}</TableCell>
              <TableCell align="center">{row.orderStatus}</TableCell>
              <TableCell align="center">{row.wechatId}</TableCell>
              <TableCell align="center">{row.phoneNumber}</TableCell>
              <TableCell align="center">{row.type}</TableCell>
              {row.schedule.length > 1 ? (
                <TableCell align="center">
                  <ListItem button onClick={handleClick} id={row.orderNumber}>
                    <ListItemText primary={row.schedule[0]} />
                    {open[row.orderNumber] ? <ExpandLess /> : <ExpandMore />}
                  </ListItem>
                  <Collapse
                    in={open[row.orderNumber]}
                    timeout="auto"
                    unmountOnExit
                  >
                    <List component="div" disablePadding>
                      {row.schedule.map((schedule) => (
                        <ListItem button className={classes.nested} key={schedule}>
                          <ListItemText primary={schedule} />
                        </ListItem>
                      ))}
                    </List>
                  </Collapse>
                </TableCell>
              ) : (
                <TableCell align="center">
                  {" "}
                  <ListItem button onClick={handleClick} id={row.orderNumber}>
                    <ListItemText primary={row.schedule[0]} />
                  </ListItem>
                </TableCell>
              )}
              <TableCell align="center">{row.lastMessageTime}</TableCell>
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

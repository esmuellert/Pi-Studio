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
import MenuItem from "@material-ui/core/MenuItem";
import FormControl from "@material-ui/core/FormControl";
import Select from "@material-ui/core/Select";
import Input from "@material-ui/core/Input";
import ChatIcon from "@material-ui/icons/Chat";
import DoneIcon from "@material-ui/icons/Done";
import { Button } from "@material-ui/core";
import PhotoCameraIcon from "@material-ui/icons/PhotoCamera";
import ImageIcon from "@material-ui/icons/Image";
import PublishIcon from "@material-ui/icons/Publish";
import PlayArrowIcon from "@material-ui/icons/PlayArrow";
// Generate Order Data

function preventDefault(event) {
  event.preventDefault();
}

const useStyles = makeStyles((theme) => ({
  seeMore: {
    marginTop: theme.spacing(3),
  },
  icon: { display: "none" },
}));

export default function OrdersList() {
  const classes = useStyles();
  const [rows, setRows] = useState([]);
  const [schedule, setSchedule] = useState({});
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
            let schedules = {};
            response.data.forEach((element) => {
              schedules[element.orderNumber] = element.schedule[0];
            });
            setSchedule(schedules);
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

  const handleScheduleChange = (event) => {
    let schedules = Object.assign({}, schedule);
    schedules[event.currentTarget.id] = event.target.value;
    setSchedule(schedules);
  };

  const handleStatusChange = (event) => {
    console.log(event.currentTarget);
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
            <TableCell align="center">操作</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow key={row.orderNumber}>
              <TableCell align="center">{row.orderNumber}</TableCell>
              <TableCell align="center">
                {formatFullTime(row.orderedTime)}
              </TableCell>
              <TableCell align="center">
                {convertStatus(row.orderStatus)}
              </TableCell>
              <TableCell align="center">{row.wechatId}</TableCell>
              <TableCell align="center">{row.phoneNumber}</TableCell>
              <TableCell align="center">{row.type}</TableCell>

              {(() => {
                if (row.schedule.length > 1 || row.orderStatus !== "PLACED") {
                  return (
                    <TableCell align="center">
                      <FormControl>
                        <Select
                          value={schedule[row.orderNumber]}
                          onChange={handleScheduleChange}
                        >
                          {row.schedule.map((schedule) => (
                            <MenuItem
                              id={row.orderNumber}
                              key={schedule}
                              value={schedule}
                            >
                              {formatFullTime(schedule)}
                            </MenuItem>
                          ))}
                        </Select>
                      </FormControl>{" "}
                    </TableCell>
                  );
                } else {
                  return (
                    <TableCell align="center">
                      <Select
                        input={<Input disableUnderline />}
                        value={formatFullTime(row.schedule[0])}
                        classes={{
                          icon: classes.icon,
                        }}
                      >
                        <MenuItem value={formatFullTime(row.schedule[0])}>
                          {formatFullTime(row.schedule[0])}
                        </MenuItem>
                      </Select>
                    </TableCell>
                  );
                }
              })()}

              <TableCell align="center">
                {" "}
                {(() => {
                  switch (row.orderStatus) {
                    case "PLACED":
                      return (
                        <Button onClick={handleStatusChange}>
                          <PlayArrowIcon color="primary" />
                        </Button>
                      );
                    case "RECEIVED":
                      return (
                        <Button onClick={handleStatusChange}>
                          <PhotoCameraIcon color="primary" />
                        </Button>
                      );
                    case "IMAGING":
                      return (
                        <Button onClick={handleStatusChange}>
                          <ImageIcon color="primary" />
                        </Button>
                      );
                    case "PROCESSING":
                      return (
                        <Button onClick={handleStatusChange}>
                          <DoneIcon color="primary" />
                        </Button>
                      );
                    default:
                      return null;
                  }
                })()}{" "}
                {row.orderStatus === "PROCESSING" ? (
                  <Button>
                    <PublishIcon color="primary" />
                  </Button>
                ) : null}
                <Button>
                  <ChatIcon color="primary" />
                </Button>
              </TableCell>
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

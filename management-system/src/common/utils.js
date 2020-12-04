const url = "http://192.168.1.5:8080";

const formatFullTime = (time) => {
  const date = new Date(time);
  const year = date.getFullYear();
  let month = date.getMonth() + 1;
  let day = date.getDate();
  const hour = date.getHours();
  const minute = date.getMinutes();
  return (
    year +
    "-" +
    addFrontZero(month) +
    "-" +
    addFrontZero(day) +
    " " +
    addFrontZero(hour) +
    ":" +
    addFrontZero(minute)
  );
};

const addFrontZero = (number) => {
  if (number < 10) {
    return "0" + number;
  }
  return number;
};

const convertStatus = (status) => {
  switch (status) {
    case "PLACED":
      return "已下单";
    case "RECEIVED":
      return "待拍摄";
    case "IMAGING":
      return "拍摄中";
    case "PROCESSING":
      return "后期处理";
    case "FINISHED":
      return "已完成";
    default:
      break;
  }
};
export { url, formatFullTime, convertStatus };

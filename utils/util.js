const formatFullTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}
const formatDate = date => {
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  let day = date.getDate();
  if (day < 10) {
    day = '0' + day;
  }
  return year + '-' + month + '-' + day;
}
const formatTime = date => {
  const hour = date.getHours();
  let minute = date.getMinutes();
  if (minute < 10) {
    minute = '0' + minute;
  }
  return hour + ':' + minute;
}


const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

const nextMonth = date => {
  const year = date.getFullYear();
  const month = date.getMonth();
  const day = date.getDate();
  if (month == 11) {
    return new Date(year + 1, 0, day);
  }
    return new Date(year, month + 1, day);
}

const generateTimes = date => {
  let times = [];
  const year = date.getFullYear();
  const month = date.getMonth();
  const day = date.getDate();
  for (let i = 8; i < 24; i++) {
    times.push(formatTime(new Date(year, month, day, i, 0)));
    times.push(formatTime(new Date(year, month, day, i, 30)));
  }
  return times;
}

module.exports = {
  formatTime: formatTime,
  formatDate: formatDate,
  nextMonth: nextMonth,
  generateTimes: generateTimes
}

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

const randomString = (length = Math.floor(Math.random() * 40) + 10, chars = "2020年美国总统选举（英语：2020 United States presidential election）于2020年11月3日举行，此次是美国第59届总统选举，同时众议院全部435个席位及参议院其中33个议席也会进行改选，以产生第117届美国国会。在选举举行后，选举人团会在2020年12月14日正式投票并确定选举结果。现任总统唐纳德·特朗普与副总统迈克·彭斯顺利在共和党内几乎没有任何竞选对手的情况下直接得到执政党提名参选。另一大党民主党则在经历了美国总统选举史上人数最多的党内初选后，由前副总统乔·拜登击败主要的竞争对手伯尼·桑德斯后正式被党内提名为总统候选人，由女性国会参议员卡玛拉·哈里斯担任他的副手，使其成为美国作为总统竞选搭档的第一位非裔和亚裔美国人。除此之外，还有一些代表其他政党和独立的候选人参与本届总统选举。此次总统选举的获胜者将于2021年1月20日宣誓就职。") => {
  var result = '';
  for (var i = length; i > 0; --i) result += chars[Math.floor(Math.random() * chars.length)];
  return result;
}

module.exports = {
  formatTime: formatTime,
  formatDate: formatDate,
  nextMonth: nextMonth,
  generateTimes: generateTimes,
  formatFullTime: formatFullTime,
  randomString: randomString
}
const {
  formatDate,
  formatTime,
  nextMonth,
  generateTimes
} = require("../../utils/util");

// pages/index/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  onInput: function (e) {
    const field = e.currentTarget.dataset.id;
    this.setData({
      [`${field}`]: e.detail.value,
    })
  },

  onTypeChange: function (e) {
    this.setData({
      typeIndex: e.detail.value,
      // [`formData.type`]:e.detail.value
    })
  },

  onDateChange: function (e) {
    this.setData({
      date: e.detail.value,
      // [`formData.date`]:e.detail.value
    })
  },

  onTimeChange: function (e) {
    // const time = this.data.time[e.detail.value];
    this.setData({
      timeIndex: e.detail.value,
      // [`formData.time`]: `${time}`
    });
  },

  onDeleteSchedule: function (e) {
    this.data.schedule.splice(e.target.dataset.id, 1);
    this.setData({
      schedule: this.data.schedule
    })
  },

  onScheduleDateChange: function (e) {
    let i = e.target.dataset.id;
    this.setData({
      ['schedule[' + i + '].date']: e.detail.value
    });
  },

  onScheduleTimeChange: function (e) {
    let i = e.target.dataset.id;
    this.setData({
      ['schedule[' + i + '].timeIndex']: e.detail.value
    });
    console.log(this.data)
    console.log(e);
  },

  onAddSchedule: function (e) {
    this.data.schedule.push({
      date: this.data.startDate,
      timeIndex: 0,
      length: this.data.schedule.length
    });
    this.setData({
      schedule: this.data.schedule
    });
  },

  submitForm: function (e) {
    let schedule = [];
    schedule.push(this.data.date + ' ' + this.data.time[this.data.timeIndex]);
    this.data.schedule.forEach(element => {
      schedule.push(element.date + ' ' + this.data.time[element.timeIndex]);
    });

    this.setData({
      'formData.type': this.data.type[this.data.typeIndex],
      'formData.schedule': `${schedule}`,
      'formData.mobile': this.data.mobile,
      'formData.notes': this.data.notes
    });
    console.log(this.data);
    console.log(this.data.formData);
    wx.showToast({
      title: '预约信息已提交！'
    })
    this.onLoad();
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      mobile:"",

      type: ['证件照', '写真'],
      typeIndex: 0,

      schedule: [],
      date: formatDate(new Date()),
      startDate: formatDate(new Date()),
      endDate: formatDate(nextMonth(new Date())),
      time: generateTimes(new Date()),
      timeIndex: 0,

      notes:""
    });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})
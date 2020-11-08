const {
  formatFullTime,
  formatTime,
  randomString
} = require("../../utils/util");

// pages/order/order.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    messages: [{
      type: "received",
      content: randomString(200),
      time: formatFullTime(new Date(2020, 3, 12, 14, 52, 42)),
      index: "m0"
    }, {
      type: "messages-time",
      content: "2020-12-01",
      time: "",
      index: "m1"
    }, {
      type: "sent",
      content: randomString(200),
      time: formatFullTime(new Date(2020, 2, 15, 16, 31, 52)),
      index: "m2"
    }],
    scrollTop: "m0",
    images: [],
    hasImage: false
  },

  onScrollToUpper: function (e) {
    const top = 'm' + (this.data.messages.length - 1);
    for (let i = 0; i < 5; i++) {
      this.data.messages.push({
        type: "sent",
        content: randomString(),
        time: formatFullTime(new Date()),
        index: 'm' + this.data.messages.length
      });
      this.data.messages.push({
        type: "received",
        content: randomString(),
        time: formatFullTime(new Date()),
        index: 'm' + this.data.messages.length
      });
    }


    this.setData({
      messages: this.data.messages,
      scrollTop: `${top}`
    })
  },

  onDownloadImage: function (e) {
    this.setData({
      hasImage: true,
      images: ["../../images/美签.jpg", "../../images/大图.jpg"]
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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
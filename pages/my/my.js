//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    orders: [{
      status: "已完成",
      time: "2020年11月4日（星期五） 15：30",
      type: "证件照"
    }, {
      status: "待拍摄",
      time: "2020年12月4日（星期五） 10：30",
      type: "写真"
    }]
  },
  //事件处理函数
  bindViewTap: function () {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },

  onTapOrder: function(e) {
    wx.navigateTo({
      url: '../order/order',
    })
  },

  onLoad: function () {

  },
})
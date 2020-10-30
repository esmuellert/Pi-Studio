const { formatDate } = require("../../utils/util.js");

Component({
  data: {
    date: formatDate(new Date()),
    type: ["证件照", "写真", "外拍"],
    typeIndex: 0,
    formData: {

    },
    rules: [{
      name: 'qq',
      rules: {
        required: true,
        message: 'qq必填'
      },
    }, {
      name: 'mobile',
      rules: [{
        required: true,
        message: 'mobile必填'
      }, {
        mobile: true,
        message: 'mobile格式不对'
      }],
    }]
  },
  methods: {
    bindTypeChange: function (e) {
      console.log('picker type 发生选择改变，携带值为', e.detail.value);

      this.setData({
        typeIndex: e.detail.value,
        [`formData.type`]: e.detail.value
      })
    },
    bindDateChange: function (e) {
      this.setData({
        date: e.detail.value,
        [`formData.date`]: e.detail.value
      })
    },
    formInputChange(e) {
      const {
        field
      } = e.currentTarget.dataset
      this.setData({
        [`formData.${field}`]: e.detail.value
      })
    },
    submitForm() {
      this.selectComponent('#form').validate((valid, errors) => {
        console.log('valid', valid, errors)
        if (!valid) {
          const firstError = Object.keys(errors)
          if (firstError.length) {
            this.setData({
              error: errors[firstError[0]].message
            })

          }
        } else {

          wx.showToast({
            title: '校验通过'
          })
          if (!("type" in this.data.formData)) {
            this.data.formData.type = this.data.typeIndex;
          }
          if (!("date" in this.data.formData)) {
            this.data.formData.date = this.data.date;
          }          
          console.log(this.data.formData);
        }
      })
    }

  }
});
// @ts-nocheck
import promptAction from '@ohos.promptAction'

/**
 * 全局通用普通文字提示
 * @param message 文字内容
 * @param duration 提示显示时间
 */
export const MessageBox = (message: string, duration:number = 2000) => {
  promptAction.showToast({
    message,
    duration
  })
}

/**
 * 对话框
 * @param title 标题
 * @param message 内容
 * @param 是否显示取消按钮
 */
export const MessageDialog = (title:string = "提示", message:string, cancelBtn?: Boolean = true) => {
  return new Promise<Boolean>((result, reject) => {
    let btns = [{
      text: '取消',
      color: '#000000',
      value: "cancel"
    },
      {
        text: '确定',
        color: '#FF6F61',
        value: 'confirm'
      }]

    if(!cancelBtn) {
      btns.splice(0, 1);
    }

    promptAction.showDialog({
      title,
      message,
      buttons: btns,
    }, (err, data) => {
      if(!err) {
        if(btns[data.index].value == "confirm") {
          result(true)
        } else {
          reject(false)
        }
      }
    })
  })
}
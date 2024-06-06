/**
 * 登录页面
 * @author zhang
 * @date 2024年5月20日
 */
import LoginForm from './LoginForm/LoginForm'
import router from '@ohos.router';

@Entry
@Component
struct Login {
  @State
  showLoginForm: boolean = true; // 是否打开登录表单

  build(){
    Stack({ alignContent: Alignment.TopStart }){
      Image($r("app.media.background_image_of_login_page")).width("100%").height("100%")
      Column(){
        Row().width(100).height(100).backgroundColor(Color.Gray).margin({ bottom: 10 })
        Text("学习生活、分享美好").fontColor("#ffffff").fontSize(16).fontWeight(FontWeight.Bold)
      }.width("100%").position({ x: 0, y: "20%" }).justifyContent(FlexAlign.Center)

      Column(){
        Row(){
          Text("登录").fontColor("#ffffff").fontSize(16).fontWeight(FontWeight.Bold)
        }.width("80%").backgroundColor("rgba(255, 42, 67, 1)").height(46).border({ radius: 23 }).justifyContent(FlexAlign.Center).margin({ bottom: 26 })
        .onClick(() => {
          this.showLoginForm = true
        })
        Row(){
          Text("先去逛逛").fontColor("#ffffff").fontSize(16).fontWeight(FontWeight.Bold)
        }.width("80%").height(46).backgroundColor("rgba(255,255,255,0.2)").border({ radius: 23 }).justifyContent(FlexAlign.Center)
        .onClick(() => {
          router.pushUrl({ url: "pages/Index" })
        })
      }.width("100%").position({ x: 0, y: "64%" })

      if(this.showLoginForm) {
        LoginForm({ close: this.loginFormClose.bind(this) })
      }
    }
  }

  loginFormClose(state: boolean) {
    this.showLoginForm = state
  }
}
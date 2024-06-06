@Component
export default struct  LoginForm {
  private loginAccountController: TextInputController = new TextInputController();
  private passwordController: TextInputController = new TextInputController();

  @State
  domY:number = 100;

  close: (state: boolean) => void;


  aboutToAppear(){
    setTimeout(() => {
      this.domY = 0;
    },300)
  }

  build(){
    Column(){
      Stack({ alignContent: Alignment.TopStart }){
        Image($r("app.media.close")).width(22).height(22).position({ x: 15, y: 15 }).onClick(() => {
          this.domY = 100;
          setTimeout(() => {
            this.close(false);
          }, 300)
        })
        Text("星星号登录").fontColor("#000000").fontSize(24).fontWeight(FontWeight.Bold).position({ x: 0, y: 100 }).textAlign(TextAlign.Center).width("100%")

        Column(){
          // @ts-ignore
          Stack(){
            this.loginFormItemUI(this.loginAccountController, "请输入星星号")
            Image($r("app.media.login_account_icon")).position({ x: 10, y: 25 }).width(18).height(18).markAnchor({ y: 9 })
          }.margin({ bottom: 18 })
          Stack(){
            this.loginFormItemUI(this.passwordController, "请输入密码")
            Image($r("app.media.password_icon")).position({ x: 10, y: 25 }).width(18).height(18).markAnchor({ y: 9 })
          }.margin({ bottom: 28 })
          // @ts-ignore
          Row(){
            Text("登录").fontColor("#ffffff").fontSize(18).fontColor(FontWeight.Normal)
            // @ts-ignore
          }.width("100%").justifyContent(FlexAlign.Center).alignItems(HorizontalAlign.Center).backgroundColor("rgba(255, 42, 67, 1)").height(55).border({ radius: 30 })

          Row(){
            Row().width(14).height(14).border({ width: 1, color: "#D1D1D6", radius: 7 }).margin({ right: 6 })
            Row(){
              Text("我已阅读并同意").fontSize(12).fontColor("#787B86")
              Text("《用户协议》").fontSize(12).fontColor("#000000")
              Text("《隐私政策》").fontSize(12).fontColor("#000000")
              // @ts-ignore
            }.alignItems(HorizontalAlign.Start).justifyContent(FlexAlign.Start)
            // @ts-ignore
          }.alignItems(HorizontalAlign.Start).justifyContent(FlexAlign.Start).width("100%").margin({ top: 10 }).padding({ right: 6, left: 6 })

          Row(){
            Text("没有账号? ").fontColor("#b0b0b0").fontSize(14).textAlign(TextAlign.Center).margin({ right: 2 })
            Text("点击注册").fontColor("rgba(253, 193, 0, 1)").fontSize(14).textAlign(TextAlign.Center).decoration({ type: TextDecorationType.Underline })
            // @ts-ignore
          }.alignItems(HorizontalAlign.Center).margin({ top: 32 })
        }.padding({ right: 46, left: 46 }).width("100%").position({ x: 0, y: 210 })

        Column(){
          Stack({ alignContent: Alignment.Center }){
            Row().width("100%").height(1).backgroundColor("rgba(241, 241, 241, 1)")
            Text("其他方式登录").backgroundColor("#ffffff").fontColor("rgba(172, 172, 172, 1)").fontSize(14).padding({ right: 10, left: 10 })
          }

          Row(){
            Image($r("app.media.wechat_login_icon")).width(55).height(55)
            Image($r("app.media.qq_login_icon")).width(55).height(55)
            // @ts-ignore
          }.justifyContent(FlexAlign.SpaceAround).alignItems(HorizontalAlign.Center).width("100%").margin({ top: 30 })
        }.position({ x: 0, y: 540 }).width("100%").padding({ right: 46, left: 46 })
      }.width("100%").height("100%")
    }.width("100%").height("100%").backgroundColor("#ffffff").position({ x: 0, y: this.domY + "%" }).animation({
      duration: 500,
      playMode: PlayMode.Normal,
      curve: Curve.Ease
    })
  }

  @Builder loginFormItemUI(controller: TextInputController, placeholder: string, key?: string) {
    Stack(){
      // @ts-ignore
      TextInput({ placeholder: placeholder, controller }).backgroundColor("#F6F5FC").border({ radius: 6 }).height(50).padding({ left: 38 })
    }
  }
}
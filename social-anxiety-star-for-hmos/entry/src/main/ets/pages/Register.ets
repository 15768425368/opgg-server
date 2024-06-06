/**
 * 注册页面
 * @author zhang
 * @date 2024年5月20日
 */

@Entry
@Component
struct Register {
  @State
  registrationStatus: number = 0; // 0选择星星号、1填写密码

  build(){
    Stack(){
      Image($r("app.media.return_icon")).width(24).height(24).position({ x: 15, y: 15 })

      Column(){
        Text("两步完成注册：").fontSize(24).fontWeight(FontWeight.Bold)

        Column(){
          Text("确定您的星星号").fontSize(14).fontColor("#333333").fontWeight(FontWeight.Bold)

          Stack({ alignContent: Alignment.TopStart}){
            Text("1563478978").fontSize(18).fontColor("#333333").fontWeight(FontWeight.Bold).position({ x: 0, y: 28 }).markAnchor({ y: "50%" })
            Row(){
              Image($r("app.media.reset")).width(24).height(24)
              // @ts-ignore
            }.width("100%").height("100%").justifyContent(FlexAlign.End).alignItems(HorizontalAlign.Center)
          }.width("100%").margin({  top: 10 }).height(56).backgroundColor("#efefef").border({ radius: 6 }).padding({ right: 15, left: 15 })

          Row(){
            Text("下一步").fontColor("#ffffff").fontSize(16).fontWeight(FontWeight.Bold)
          }.width("100%").backgroundColor("rgba(255, 42, 67, 1)").height(46).border({ radius: 23 }).justifyContent(FlexAlign.Center).margin({ top: 56 }).onClick(() => {

            this.registrationStatus = 1;
          })
        }.margin({ top: 50 }).alignItems(HorizontalAlign.Start)
      }.position({ x: 0, y: 120 }).width("100%").alignItems(HorizontalAlign.Start).padding(15)
      .translate({ x: this.registrationStatus === 0? 0: "-100%" })
      .animation({
        duration: 300,
        curve: Curve.Ease,
        playMode: PlayMode.Normal,
      })

      Column(){
        Text("马上完成注册：").fontSize(24).fontWeight(FontWeight.Bold)
        Column(){
          Text("确认您的密码").fontSize(14).fontColor("#333333").fontWeight(FontWeight.Bold)
          TextInput({ placeholder: "请输入密码" }).width("100%").margin({  top: 10 }).height(56).backgroundColor("#efefef").border({ radius: 6 }).padding({ right: 15, left: 15 })
          .type(InputType.Password)
          Row(){
            Text("注册").fontColor("#ffffff").fontSize(16).fontWeight(FontWeight.Bold)
          }.width("100%").backgroundColor("rgba(255, 42, 67, 1)").height(46).border({ radius: 23 }).justifyContent(FlexAlign.Center).margin({ top: 56 }).onClick(() => {

            this.registrationStatus = 1;
          })
        }.margin({ top: 50 }).alignItems(HorizontalAlign.Start)
      }.position({ x: 0, y: 120 }).width("100%").alignItems(HorizontalAlign.Start).padding(15)
      .translate({ x: this.registrationStatus == 1? 0: "100%" })
      .animation({
        duration: 300,
        curve: Curve.Ease,
        playMode: PlayMode.Normal,
      })

    }
  }
}
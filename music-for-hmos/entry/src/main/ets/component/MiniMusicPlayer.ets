/**
 * 小型音乐播放器组件
 * @author zhang
 * @date 2024年6月5日
 */

@Component
export default struct MiniMusicPlayer {
  @State initialDisplacement: string = "102%";
  @State initialTransparency: number = 0;
  @Link openOrNot: boolean;

  aboutToAppear(){
    setTimeout(() => {
      this.executeEntryAnimation()
    }, 100)
  }

  build(){
    Stack(){
      Row().width("100%").height(42).backgroundColor(Color.White).borderRadius(30)
      Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.SpaceBetween }){
        Row(){
          Stack(){
            Image($r("app.media.icon5")).width(30).height(30).position({ x: 32, y: 29 }).markAnchor({ x: 0, y: 18 })
            Image("https://edata-jiaoxianghui-music.oss-cn-shenzhen.aliyuncs.com/images/1709796768001640-4.jpg").width(50).height(50).borderRadius(6).markAnchor({ x: 0, y: 4 })
          }.width(54).height(54).margin({ right: 20 })

          Text("西贝柳斯《卡累利亚》组曲，作品11 Ⅱ").maxLines(1).textOverflow({ overflow: TextOverflow.Ellipsis }).fontSize(14).fontWeight(FontWeight.Bold)
        }.width("66%")

        Image($r("app.media.pause_status_icon")).width(24).height(24)
      }.position({ x: 0, y: 25 }).markAnchor({ x: 0, y: 24 }).padding({ right: 15 })
    }.width("100%").height(54).padding({ right: 15, left: 15 }).translate({ x: 0, y: this.initialDisplacement }).opacity(this.initialTransparency).animation({
      duration: 300,
      curve: Curve.Ease
    })
  }

  /**
   * 执行入场动画
   */
  executeEntryAnimation(){
    this.initialDisplacement = "0%";
    this.initialTransparency = 1;
  }

  /**
   * 执行出场动画
   */
  executeExitAnimation() {
    this.initialDisplacement = "100%";
    this.initialTransparency = 0;

    setTimeout(() => {

      this.openOrNot = true;
    }, 300)
  }
}
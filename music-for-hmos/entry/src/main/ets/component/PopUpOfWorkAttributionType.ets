/**
 * 作品归属类型弹窗
 * @author zhang
 * @date 2024年6月4日 11点32分
 */

@Component
export default struct PopUpOfWorkAttributionType{
  @State domOpacity: number = 0;
  @State domTranslate: string = "-100%";

  @Link state: boolean;

  aboutToAppear(){
    setTimeout(() => {
      this.executeEntryAnimation()
    }, 100)
  }

  build(){
    Column(){
      Stack(){
        Column().width("100%").height("100%").backgroundColor("rgba(0,0,0,0.5)").opacity(this.domOpacity).animation({
          duration: 500,
          curve: Curve.Ease,
        })
        Column(){
          Column(){
            List(){
              ListItem(){
                Navigator({ target: "pages/views/AggregateSearch" }){
                  Stack(){
                    Image("https://5b0988e595225.cdn.sohucs.com/images/20190414/6cdb3c45348449878eb02c1d1908b397.jpeg").width("100%").height("100%").borderRadius(4).blur(20)
                    Row().backgroundColor("rgba(0,0,0,0.5)").width("100%").height("100%").borderRadius(4)
                    Text("乐 团").fontSize(18).fontWeight(FontWeight.Bold).fontColor("#ffffff")
                  }.width("100%").height(100).backgroundColor("#efefef").borderRadius(4)
                }.params({ idx: 4 })
              }.margin({ bottom: 15 })

              ListItem(){
                Navigator({ target: "pages/views/AggregateSearch" }){
                  Stack(){
                    Image("https://img0.baidu.com/it/u=1447979297,591034845&fm=253&fmt=auto&app=138&f=JPEG?w=767&h=500").width("100%").height("100%").borderRadius(4).blur(20)
                    Row().backgroundColor("rgba(0,0,0,0.5)").width("100%").height("100%").borderRadius(4)
                    Text("指挥家").fontSize(18).fontWeight(FontWeight.Bold).fontColor("#ffffff")
                  }.width("100%").height(100).backgroundColor("#efefef").borderRadius(4)
                }.params({ idx: 5 })
              }.margin({ bottom: 15 })

              ListItem(){
                Navigator({ target: "pages/views/AggregateSearch" }) {
                  Stack(){
                    Image("https://i2.hdslb.com/bfs/archive/c72c0a689ed034b00f0ca26ee8fb75886a9c47c2.jpg").width("100%").height("100%").borderRadius(4).blur(20)
                    Row().backgroundColor("rgba(0,0,0,0.5)").width("100%").height("100%").borderRadius(4)
                    Text("作曲家").fontSize(18).fontWeight(FontWeight.Bold).fontColor("#ffffff")
                  }.width("100%").height(100).backgroundColor("#efefef").borderRadius(4)
                }.params({ idx: 6 })
              }.margin({ bottom: 15 })

              ListItem(){
                Navigator({ target: "pages/views/AggregateSearch" }){
                  Stack(){
                    Image("https://img1.baidu.com/it/u=2728843505,1244092021&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=448").width("100%").height("100%").borderRadius(4).blur(20)
                    Row().backgroundColor("rgba(0,0,0,0.5)").width("100%").height("100%").borderRadius(4)
                    Text("演奏/演唱家").fontSize(18).fontWeight(FontWeight.Bold).fontColor("#ffffff")
                  }.width("100%").height(100).backgroundColor("#efefef").borderRadius(4)
                }.params({ idx: 7 })
              }
            }.padding({ right: 15, left: 15 })
          }.width("50%").height("100%").backgroundColor(Color.White).padding({ top: 30 })
        }.width("100%").height("100%").translate({ x: this.domTranslate }).alignItems(HorizontalAlign.Start).onClick(() => {
          this.executeExitAnimation()
        }).animation({
          duration: 500,
          curve: Curve.Ease,
        })
      }.width("100%").height("100%")
    }.width("100%").height("100%")
  }


  /**
   * 执行进场动画
   */
  executeEntryAnimation(){
    this.domOpacity = 1;
    this.domTranslate = "0%"
  }

  executeExitAnimation(){
    this.domOpacity = 0;
    this.domTranslate = "-100%"
    setTimeout(() => {
      this.state = false
    }, 500)
  }
}
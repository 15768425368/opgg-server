import promptAction from '@ohos.promptAction';
import PopUpOfWorkAttributionType from '../../component/PopUpOfWorkAttributionType';
import router from '@ohos.router';
import MiniMusicPlayer from '../../component/MiniMusicPlayer';

@Component
export default struct HomePage {
  private tabsController: TabsController = new TabsController();
  private swiperController: SwiperController = new SwiperController();

  /**
   * 当前激活的tabs栏下标
   */
  @State currentActivationNavigationBarIndex: number = 1;

  /**
   * 发现模块参数
   */
  @State swiperIdx: number = 0;
  @State bannerArray: any = [
    {
      pictureUrl: "https://edata-jiaoxianghui-music.oss-cn-shenzhen.aliyuncs.com/images/f3e6a1ee6f474076a0108d619b18de90.png",
    },
    {
      pictureUrl: "https://edata-jiaoxianghui-music.oss-cn-shenzhen.aliyuncs.com/images/1712472630064156",
    }
  ]

  @State openPopup: boolean = false;
  @State displayPlayer: boolean = false;

  build(){
    Column(){
      Stack(){
        Tabs({ index: this.currentActivationNavigationBarIndex, controller: this.tabsController }){
          TabContent(){
            this.videoModule()
          }.tabBar(this.TabsItem("视频", 0))
          .backgroundColor("#efefef")
          .padding({ left: 15, right: 15 })

          TabContent(){
            this.DiscoveryModule()
          }.tabBar(this.TabsItem("发现", 1))
          .backgroundColor("#efefef")
          .padding({ left: 15, right: 15 })

          TabContent(){
            this.activityModule()
          }.tabBar(this.TabsItem("活动", 2))
          .backgroundColor("#efefef")
          .padding({ left: 15, right: 15 })
        }.barMode(BarMode.Scrollable).barHeight(56).backgroundColor(Color.White).onChange((event:any) => {
         this.currentActivationNavigationBarIndex = event;
        })

        Column(){
          this.searchUIBuild($r("app.media.menu"))
        }.position({ x: 15, y: 28 }).markAnchor({ x: 0, y: 11 }).onClick(() => {
          this.openPopup = true
        })

        Column(){
          Navigator({ target: "pages/views/WorkSearchTransferPage", type: NavigationType.Push }){
            this.searchUIBuild($r("app.media.search"))
          }
        }.position({ x: "100%", y: 28 }).markAnchor({ x: 40, y: 11 })

        if (this.openPopup){
          Column(){
            PopUpOfWorkAttributionType({ state: $openPopup })
          }.position({ x: 0, y: 0 }).zIndex(10)
        }

        if (this.displayPlayer) {
          Flex(){
            MiniMusicPlayer({ openOrNot: $displayPlayer })
          }.position({ x: 0, y: "100%" }).width("100%").markAnchor({ x: 0, y: "100%" })
        }
      }
    }
  }

  @Builder TabsItem(label: string, idx: number) {
    Column(){
      Text(label).fontColor(Color.Black)
        .fontSize(this.currentActivationNavigationBarIndex === idx ? 18 : 16)
        .fontWeight(this.currentActivationNavigationBarIndex === idx ? FontWeight.Bold : FontWeight.Normal)
    }.padding({ left: 12, right: 12 })
      .onClick((event: any) => {
        this.currentActivationNavigationBarIndex = idx;
        this.tabsController.changeIndex(idx)
      })
      .zIndex(10)
  }

  @Builder searchUIBuild(icon: any) {
    Column(){
      Image(icon).width(22).height(22)
    }
  }

  /**
   * 发现模块
   */
  @Builder DiscoveryModule() {
    Column(){
      Scroll(){
        Column(){
          Swiper(this.swiperController){
            ForEach(this.bannerArray, (item) => {
              Image(item.pictureUrl).width("100%").height(138).border({ radius: 8 })
            })
          }.index(this.swiperIdx).autoPlay(true).interval(5000).margin({ bottom: 30 }).indicatorStyle({
            color: "rgba(0,0,0,0.5)",
            selectedColor: "#ffffff"
          })

          Column(){
            Flex({ justifyContent: FlexAlign.SpaceBetween, alignItems: ItemAlign.Center }){
              Text("一数精选").fontSize(18).fontWeight(FontWeight.Bold).fontColor(Color.Black)
              Navigator({ target: "pages/views/AggregateSearch" }){
                Row(){
                  Text("更多").fontSize(14).fontColor("#7E7E7E").lineHeight(14).margin({ right: 4 })
                  Image($r("app.media.more_arrow_icons")).width(9).height(9)
                  // @ts-ignore
                }.alignItems(HorizontalAlign.Center)
              }.params({ idx: 2 })
            }.width("100%").margin({ bottom: 10 })

            List({ space: 7 }){
              ForEach([1,2,3,4,5,6,7,8], () => {
                ListItem(){
                  Navigator({ target: "pages/views/SongListDetails", type: NavigationType.Push }){
                    Column(){
                      Image("https://edata-jiaoxianghui-music.oss-cn-shenzhen.aliyuncs.com/images/1712826697240123").width("100%").height(94).backgroundColor("#efefef")
                      Text("燃烧的岁月").fontColor("#000000").fontSize(14).margin({ top: 6 }).textOverflow({ overflow: TextOverflow.Ellipsis }).maxLines(1)
                    }.width(94).backgroundColor("#efefef")
                  }
                }
              })
            }.listDirection(Axis.Horizontal).cachedCount(5).width("100%")
          }.width("100%").height(154).margin({ bottom: 16 })

          Column(){
            Flex({ justifyContent: FlexAlign.SpaceBetween, alignItems: ItemAlign.Center }){
              Text("最新发布").fontSize(18).fontWeight(FontWeight.Bold).fontColor(Color.Black)
              Navigator({ target: "pages/views/AggregateSearch" }){
                Row(){
                  Text("更多").fontSize(14).fontColor("#7E7E7E").lineHeight(14).margin({ right: 4 })
                  Image($r("app.media.more_arrow_icons")).width(9).height(9)
                  // @ts-ignore
                }.alignItems(HorizontalAlign.Center)
              }.params({ idx: 1 })
            }.width("100%").margin({ bottom: 10 })

            ForEach([1,3,34,43,35], () => {
              Column(){
                Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.SpaceBetween }){
                  Column(){
                    Image("").width("100%").height("100%").border({ radius: 6 })
                  }.width(59).height(59).backgroundColor("#bfbfbf").border({ radius: 6 }).margin({ right: 9 })

                  Column(){
                    Text("降E大调第五钢琴协奏曲“皇帝琴协奏曲“皇帝").fontColor("#000000").fontSize(14).maxLines(1).textOverflow({ overflow: TextOverflow.Ellipsis }).margin({ bottom: 6 }).width("100%")
                    Text("广州交响乐团/余隆/陈萨-古典换新广州交响乐团/余隆/陈萨-古典换新广州交响乐团/余隆/陈萨-古典换新广州交响乐团/余隆/陈萨-古典换新").fontSize(12).fontColor("#8d8d8d").maxLines(2).textOverflow({ overflow: TextOverflow.Ellipsis }).width("100%")
                  }.width("70%").flexGrow(1)

                  Image($r("app.media.more_arrow_icons")).width(10).height(10)
                }.width("100%")
                Text("同系列乐曲 >").textAlign(TextAlign.Start).width("100%").margin({ top: 6 }).fontColor("#8d8d8d").fontSize(12)
              }.margin({ bottom: 14 }).onClick(() => {
                this.displayPlayer = true;
              })
            })
          }.width("100%")
        }.justifyContent(FlexAlign.Start).padding({ top: 15, bottom: 15 })
      }.scrollBar(BarState.Off).edgeEffect(EdgeEffect.Spring)
    }.width("100%").height("100%")
  }

  /**
   * 视频模块
   */
  @Builder videoModule () {
    Scroll(){
      Column(){
        Flex({ justifyContent: FlexAlign.SpaceBetween, alignItems: ItemAlign.Center }){
          Text("交响乐").fontSize(18).fontWeight(FontWeight.Bold).fontColor(Color.Black)
          Row(){
            Text("更多").fontSize(14).fontColor("#7E7E7E").lineHeight(14).margin({ right: 4 })
            Image($r("app.media.more_arrow_icons")).width(9).height(9)
            // @ts-ignore
          }.alignItems(HorizontalAlign.Center)
        }.width("100%").margin({ bottom: 10 })


        List(){
          ForEach([1,2,3,4,5,6], () => {
            ListItem(){
              Navigator({ target: "pages/views/DetailsOfVideoWorks", type: NavigationType.Push }){
                Column(){
                  Row(){}.width("100%").height(162).backgroundColor(Color.Grey).border({ radius: 6 }).margin({ bottom: 14 })
                  Text("柴可夫斯基《E小调第五交响曲》第一...").fontSize("16").fontColor(Color.Black).width("100%").maxLines(1).textOverflow({ overflow: TextOverflow.Ellipsis })
                }
              }
            }.margin({ bottom: 20 })
          })

          ListItem().width("100%").height(100)
        }
      }.width("100%").height("100%").padding({ top: 15, bottom: 15 })
    }.scrollBar(BarState.Off).edgeEffect(EdgeEffect.Spring)
  }

  /**
   * 活动模块
   */
  @Builder activityModule() {
    Scroll(){
      Column(){
        List(){
          ForEach([1,2,3,4,5,6], () => {
            ListItem(){
              Column(){
                Row(){}.width("100%").height(194).backgroundColor(Color.Grey).border({ radius: 6 }).margin({ bottom: 14 })
                Text("柴可夫斯基《E小调第五交响曲》第一...").fontSize("16").fontColor(Color.Black).width("100%").maxLines(2).textOverflow({ overflow: TextOverflow.Ellipsis })
              }
            }.margin({ bottom: 20 })
          })

          ListItem().width("100%").height(100)
        }
      }.width("100%").height("100%").padding({ top: 15, bottom: 15 })
    }.scrollBar(BarState.Off).edgeEffect(EdgeEffect.Spring)
  }

  closePopup(){
    this.openPopup = false;
  }
}
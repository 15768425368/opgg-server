
import HomePage from './HomePage/homePage'
import MessagePage from './MessagePage';
import PersonalHomepage from './PersonalHomepage';
import router from '@ohos.router';

let storage = LocalStorage.GetShared()
@Entry(storage)
@Component
struct Index {
  @State currentIndex: number = 0
  private controller: TabsController = new TabsController()

  @LocalStorageLink("statusBarHeight")
  statusBarHeight: number = 0;

  @LocalStorageLink("bottomNavigationBarHeight")
  bottomNavigationBarHeight: number = 0;

  pageTransition(){
    PageTransitionExit({ duration: 0, curve: Curve.Ease,  type: RouteType.Push }).slide(SlideEffect.Bottom)
  }

  // 自定义导航页签的样式
  @Builder TabBuilder(title: string, targetIndex: number, selectedImg: Resource, normalImg: Resource) {

    Column() {
      Text(title)
        .fontColor(this.currentIndex === targetIndex ? '#FF6F61' : '#B0B0B0').fontSize(16)
        .fontWeight(this.currentIndex === targetIndex ? FontWeight.Bold: FontWeight.Normal)
    }
    .width('100%')
    .height(50)
    .justifyContent(FlexAlign.Center)
    .onClick(() => {
      this.currentIndex = targetIndex
      this.controller.changeIndex(this.currentIndex)
    })
  }

  build() {
    Tabs({
      barPosition: BarPosition.End,
      controller: this.controller
    }) {
      TabContent() {
        HomePage()
      }.tabBar(this.TabBuilder('首页', 0, $r("app.media.icon_indexed"), $r('app.media.icon_index'))).padding({ top: this.statusBarHeight + 'px' })

      TabContent() {
        Text("关注")
      }.tabBar(this.TabBuilder('关注', 1, $r("app.media.icon_followed"), $r('app.media.icon_follow'))).padding({ top: this.statusBarHeight + 'px' })

      TabContent(){}.tabBar(this.uploadWorksBuilder())

      TabContent() {
        MessagePage()
      }.tabBar(this.TabBuilder('消息', 3, $r("app.media.icon_messageed"), $r('app.media.icon_message'))).padding({ top: this.statusBarHeight + 'px' })

      TabContent() {
        PersonalHomepage()
      }.tabBar(this.TabBuilder('我的', 4, $r("app.media.icon_myed"), $r('app.media.icon_my')))
    }.onChange((index: number) => {
      if(index === 2) {
        this.controller.changeIndex(this.currentIndex);
        router.pushUrl({ url: "pages/UploadWorks/UploadWorks" })
      } else {
        this.currentIndex = index;
      }
    }).scrollable(false)
      .animationDuration(0)
      .width("100%")
      .height("100%")
      .padding({ bottom: this.bottomNavigationBarHeight + 'px' })
      .barHeight(86)
  }


  @Builder uploadWorksBuilder (){
    Column(){
      Image($r("app.media.add")).width(18).height(18)
    }.width(60).height(34).backgroundColor("#FF6F61").border({ radius: 14 })
    .justifyContent(FlexAlign.Center)
  }
}
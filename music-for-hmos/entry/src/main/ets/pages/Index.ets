import MiniMusicPlayer from '../component/MiniMusicPlayer';
import Artist from './Artist/artist';
import HomePage from './HomePage/HomePage';
import OnlineSchool from './OnlineSchool/OnlineSchool';
import PersonalCenter from './PersonalCenter/PersonalCenter';
import ShoppingMall from './ShoppingMall/ShoppingMall';

let storage = LocalStorage.GetShared()

@Entry(storage)
@Component
struct Index {
  private tabsController: TabsController = new TabsController();

  @LocalStorageLink("statusBarHeight")
  statusBarHeight: number = 0; // 状态栏高度

  @LocalStorageLink("bottomNavigationBarHeight")
  bottomNavigationBarHeight: number = 0; // 底部栏安全高度


  /**
   * 当前激活的tabs栏下标
   */
  @State currentActivationNavigationBarIndex: number = 0;

  pageTransition(){
    PageTransitionEnter({ duration: 0 });
    PageTransitionExit({ duration: 300, curve: Curve.Ease }).opacity(0)
  }

  build() {
    Tabs({ barPosition: BarPosition.End, controller: this.tabsController }){
      TabContent(){
       Stack(){
         HomePage()
       }.padding({ top: this.statusBarHeight + 'px' }).backgroundColor(Color.White).width("100%").height("100%")
      }.tabBar(this.TabsItem($r("app.media.homeIcon"), $r("app.media.homePageSelectionIcon"), 0))

      TabContent(){
        Column(){
          Artist()
        }.padding({ top: this.statusBarHeight + 'px' })
      }.tabBar(this.TabsItem($r("app.media.artistIcon"), $r("app.media.artistSelectionIcon"), 1)).backgroundColor("#efefef")

      TabContent(){
        Column(){
          ShoppingMall()
        }.padding({ top: this.statusBarHeight + 'px' })
      }.tabBar(this.TabsItem($r("app.media.mallIcon"), $r("app.media.mallSelectionIcon"), 2)).backgroundColor("#efefef")

      TabContent(){
        OnlineSchool()
      }.tabBar(this.TabsItem($r("app.media.educationIcon"), $r("app.media.educationSelectionIcon"), 3)).backgroundColor("#efefef")

      TabContent(){
        Column(){
          PersonalCenter()
        }.padding({ top: this.statusBarHeight + 'px' })
      }.tabBar(this.TabsItem($r("app.media.myIcon"), $r("app.media.mySelectedIcon"), 4)).backgroundColor("#efefef")
    }.scrollable(false)
      .width("100%")
      .height("100%")
      .backgroundColor("#ffffff")
      .animationDuration(0)
      .barHeight(66)
      .padding({ bottom: this.bottomNavigationBarHeight + 'px' })
      .onChange((idx: number) => {
        this.currentActivationNavigationBarIndex = idx;
      })
  }

  @Builder TabsItem (icon: any, selectIcon: any, idx: number) {
    Row(){
      Image(this.currentActivationNavigationBarIndex === idx ? selectIcon : icon).width(24).height(24).onClick(() => {
        this.currentActivationNavigationBarIndex = idx;
        this.tabsController.changeIndex(idx);
      })
      // @ts-ignore
    }.width("100%").height("100%").alignItems(HorizontalAlign.Center).justifyContent(FlexAlign.Center)
  }
}
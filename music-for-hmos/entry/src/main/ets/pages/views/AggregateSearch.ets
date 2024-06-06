
import router from '@ohos.router';
import LedanListComponent from '../../component/LedanListComponent';
import NoArtworkComponent from '../../component/NoArtworkComponent';
import VideoListComponent from '../../component/VideoListComponent';
/**
 * 聚合搜索页
 * @author zhang
 * @date 2024年6月5日
 */

let storage = LocalStorage.GetShared();
@Entry(storage)
@Component
struct AggregateSearch {
  private tabsController: TabsController = new TabsController()

  @State keyword: string = "";
  @State searchType: string[] = ["综合", "乐曲", "乐单", "视频", "乐团", "指挥家", "作曲家", "演奏家/演唱家"]
  @State currentSelectionType: number = 0

  @LocalStorageLink("statusBarHeight")
  statusBarHeight: number = 0; // 状态栏高度

  pageTransition(){
    PageTransitionExit({ type: RouteType.None, duration: 300, curve: Curve.Ease }).slide(SlideEffect.Right)
  }

  onPageShow(){
    let options: urlParams = router.getParams();
    if(options){
      this.currentSelectionType = options.idx || 0;
      this.tabsController.changeIndex(options.idx)
    }

  }

  build(){
    Column(){
      Flex({ alignItems: ItemAlign.Center, justifyContent: FlexAlign.SpaceBetween }){
        Search({ value: this.keyword, placeholder: "广州交响乐团" }).height(32).placeholderColor("#666666").textFont({ size: 14, weight: FontWeight.Bold }).placeholderFont({ size: 14 })
        Text("取消").width(80).textAlign(TextAlign.Center).onClick(() => {
          router.back();
        })
      }

      Tabs({ controller: this.tabsController, index: this.currentSelectionType }){
        TabContent() {

        }.tabBar(this.searchTypeBuilder("综合", 0)).padding({ top: 20 })

        TabContent() {
          this.universalListBox([1,2,3,4,5,6,7,8,9,23,4,5], "乐曲")
        }.tabBar(this.searchTypeBuilder("乐曲", 1)).padding({ top: 20 })

        TabContent() {
          this.universalListBox([1,2,3,4,5,6,7,8,9,23,4,5], "乐单")
        }.tabBar(this.searchTypeBuilder("乐单", 2)).padding({ top: 20 })

        TabContent() {
          this.universalListBox([1,2,3,4,5,6,7,8,9,23,4,5], "视频")
        }.tabBar(this.searchTypeBuilder("视频", 3)).padding({ top: 20 })

        TabContent() {
          this.universalListBox([1,2,3,4,5,6,7,8,9,23,4,5], "通用")
        }.tabBar(this.searchTypeBuilder("乐团", 4)).padding({ top: 20 })

        TabContent() {
          this.universalListBox([1,2,3,4,5,6,7,8,9,23,4,5], "通用")
        }.tabBar(this.searchTypeBuilder("指挥家", 5)).padding({ top: 20 })

        TabContent() {
          this.universalListBox([1,2,3,4,5,6,7,8,9,23,4,5], "通用")
        }.tabBar(this.searchTypeBuilder("作曲家", 6)).padding({ top: 20 })

        TabContent() {
          this.universalListBox([1,2,3,4,5,6,7,8,9,23,4,5], "通用")
        }.tabBar(this.searchTypeBuilder("演奏家/演唱家", 7)).padding({ top: 20 })
      }.barHeight(26).barMode(BarMode.Scrollable).width("100%").onChange((idx) => {
        this.tabsController.changeIndex(idx);
        this.currentSelectionType = idx;
      }).animationDuration(300)
    }.width("100%").height("100%").padding({  top: this.statusBarHeight + 20 + 'px', right: 15, bottom: 15, left: 15  }).backgroundColor(Color.White)
  }

  @Builder searchTypeBuilder(item: any, idx: number) {
          Row(){
            Text(item).fontSize(14).fontColor(this.currentSelectionType === idx ? "#CF3230" : "#ff5d5d5d").fontWeight(this.currentSelectionType === idx ? FontWeight.Bold : FontWeight.Normal)
          }.padding({ top: 4, right: 4, bottom: 4, left: 4 }).border({ width: { bottom: this.currentSelectionType === idx ? 2 : 0 }, color: "#CF3230" }).margin({ right: 14 })
  }

  @Builder commonListItems() {
    Flex({ alignItems: ItemAlign.Center }){
      Image("").width(60).height(60).borderRadius(30).backgroundColor("#efefef").margin({ right: 16 })
      Text("裕隆").fontSize(14).fontWeight(FontWeight.Bold).maxLines(1).textOverflow({ overflow: TextOverflow.Ellipsis })
    }
  }

  @Builder universalListBox (list: any[], target: string) {
    List(){
      ForEach(list, () => {
        ListItem(){
         Column(){
           if(target === "乐曲") {
             NoArtworkComponent()
           }
           if(target === "乐单") {
             LedanListComponent()
           }
           if (target === "视频") {
             VideoListComponent()
           }
           if (target === "通用") {
             this.commonListItems()
           }
         }.width("100%")
        }.margin({ bottom: 15 }).padding({ bottom: 15 }).border({ width: { bottom: target === "通用" ? 1 : 0 }, color: "#efefef" })
      })

      ListItem(){
        Row().width("100%").height(120)
      }
    }.height("100%").backgroundColor(Color.White)
  }
}

interface urlParams {
  idx?: number
}
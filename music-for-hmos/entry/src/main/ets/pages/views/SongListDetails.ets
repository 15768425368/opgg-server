/**
 * 歌单详情页面
 */
import router from '@ohos.router'
import NoArtworkComponent from '../../component/NoArtworkComponent';

let storage = LocalStorage.GetShared()

@Entry(storage)
@Component
struct SongListDetails {

  @LocalStorageLink("statusBarHeight")
  statusBarHeight: number = 0; // 状态栏高度


  build(){
    Scroll(){
      Column(){
        Stack(){
          Image("https://edata-jiaoxianghui-music.oss-cn-shenzhen.aliyuncs.com/images/1710851557991787").width("100%").height("100%")
          Row().width("100%").height("100%").linearGradient({
            direction: GradientDirection.Top,
            colors: [["rgba(255,255,255, 1)", 0.0], ["rgba(255,255,255,0)", 1.0]]
          })
          Column(){
           Text("GSO广州新年音乐会").fontSize(18).fontWeight(FontWeight.Bold).fontColor(Color.Black).maxLines(1).textOverflow({ overflow: TextOverflow.Ellipsis }).margin({ bottom: 10 })
           Row(){
             Image("").width(26).height(26).backgroundColor("#ccc").borderRadius(13).margin({ right: 4 }).margin({ right: 6 })
             Text("一数音乐官方").fontSize(14).fontColor("#333").margin({ right: 10 })
             Text("收藏歌单").fontSize(12).padding({ top: 2, right: 8, bottom: 2, left: 8 }).border({ width: 1, color: "#333", radius: 18 }).lineHeight(12)
             // @ts-ignore
           }.alignItems(HorizontalAlign.Center).margin({  bottom: 10, })
           Text("广州新年音乐会用美好的音乐辞旧迎新，为广州城市生活凝心聚力，这个初心三十多年来，始终如一。这个初心三十多年来").fontColor("#ff626262").maxLines(2).textOverflow({ overflow: TextOverflow.Ellipsis }).lineHeight(20).fontSize(14)
          }.justifyContent(FlexAlign.End).width("100%").height("100%").alignItems(HorizontalAlign.Start).padding({ left: 15, right: 15 })
          Image($r("app.media.return_icon")).width(22).height(22).position({ x: 15, y: this.statusBarHeight + 20 + 'px' }).onClick(() => {
            router.back()
          })
        }.width("100%").height(320).margin({ bottom: 20 })

        Column(){
          Row(){
            Column(){
              Image($r("app.media.play_icon")).width(14).height(14).markAnchor({ x: -2 })
            }.width(40).height(26).backgroundColor("#CF3230").borderRadius(20).justifyContent(FlexAlign.Center).alignItems(HorizontalAlign.Center).margin({ right: 6 })

            Text("全部播放 29").fontSize(14).fontColor("#333")
            // @ts-ignore
          }.justifyContent(FlexAlign.Start).width("100%").padding({ right: 15, left: 15 }).alignItems(ItemAlign.Center).margin({ bottom: 20 })

          Column(){
            ForEach([1,2,3,4,5,2,3,4,5], () => {
              Column(){
                NoArtworkComponent()
              }.padding({ left: 15, right: 15,bottom: 15 }).margin({ bottom: 20 })
            })
          }
        }
      }
    }.backgroundColor(Color.White)
  }
}
/**
 * 列表型弹窗组件
 * @author zhang
 * @date 2024年5月27日 14点04分
 *
 * @param option 选项列表 { label: 选项标题, key: 选项key }
 * @callback change(key: string)
 */
@Component
export default struct ListOptionsDialog {
  @Provide
  option: optionDataType[] = [];

  build(){
    Column(){
      Text(){

      }
    }
  }
}

interface optionDataType {
  label: string,
  key: string
}
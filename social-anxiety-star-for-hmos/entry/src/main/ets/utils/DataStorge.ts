import dataPreferences from '@ohos.data.preferences';
import { MessageBox } from './Message';
/**
 * 获取本地缓存
 */
export const getStorge = (key: string, defValue, context: any) => {
  return new Promise<any>(async (result, reject) => {
    try {
      let storage = await getExample(context);
      // @ts-ignore
      result(await storage.get(key, defValue))
    } catch (error) {
      reject(false)
      MessageBox("系统错误")
    }
  })
}

/**
 * 添加本地缓存
 */
export const setStorge = (key: string, value, context:any) => {
  return new Promise<any>(async (result, reject) => {
    try {
      let storage = await getExample(context);
      // @ts-ignore
      result(await storage.put(key, value))
    } catch (error) {
      reject(false)
      MessageBox("系统错误")
    }
  })
}

/**
 * 移除指定缓存
 */
export const removeStorge = (key: string, context:any) => {
  return new Promise<any>(async (result, reject) => {
    try {
      let storage = await getExample(context);
      // @ts-ignore
      result(await storage.delete(key))
    } catch (error) {
      reject(false)
      MessageBox("系统错误")
    }
  })
}

/**
 * 清理本地缓存
 */
export const clearStorge = (context:any) => {
  return new Promise<any>(async (result, reject) => {
    try {
      let storage = await getExample(context);
      // @ts-ignore
      result(await storage.clear())
    } catch (error) {
      reject(false)
      MessageBox("系统错误")
    }
  })
}

/**
 * 获取本地缓存实例
 * @returns
 */
const getExample = (context) => {
  return new Promise((result, reject) => {
    try {
      let promise = dataPreferences.getPreferences(context, 'localStorage');
      promise.then((object) => {
        result(object)
      }).catch(() => {
        reject(false)
      })
    } catch (error) {
      reject(false)
    }
  })
}
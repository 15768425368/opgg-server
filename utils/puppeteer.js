const { newError } = require("./message");
const puppeteer = require("puppeteer");
class getWebFun {
  constructor() {}

  op(url) {}

  getList(url) {
    return new Promise(async (R) => {
      try {
        // 创建一个浏览器对象
        const browser = await puppeteer.launch({
          headless: true,
        });

        // 打开一个新的页面
        const page = await browser.newPage();

        await page.goto(url, {
          timeout: 0, // 不限制超时时间
        });

        let html = await page.$$eval("table > tbody > tr", (options) => {
          return options.map((item) => {
            let children = item.childNodes;

            return {
              head_image: children[1].childNodes[0].childNodes[0].src,
              name: children[1].childNodes[0].childNodes[1].innerText,
              level: children[2].innerText,
              win: children[3].innerText,
              debut: children[4].innerText,
              disable: children[5].innerText,
              details_url: children[1].childNodes[0].href,
            };
          });
        });
        browser.close();
        R(html);
      } catch (error) {
        R(new newError({ msg: "系统错误" }));
      }
    });
  }

  singleDetail(url) {
    return new Promise(async (R) => {
      // 创建一个浏览器对象
      const browser = await puppeteer.launch({
        headless: true,
      });

      // 打开一个新的页面
      const page = await browser.newPage();

      await page.goto(url, {
        timeout: 0, // 不限制超时时间
        waitUntil: "domcontentloaded",
      });

      // 获取小技能图标
      let skill = await page.$$eval(".skill", (options) => {
        return options.map((item, idx) => {
          let data = {};
          if (idx == 0) {
            data.passive = item.childNodes[0].childNodes[0].src;
          } else {
            (data.skill =
              item.childNodes[0].childNodes[0].childNodes[0].innerText),
              (data.icon = item.childNodes[0].childNodes[1].src);
          }

          return data;
        });
      });

      // 获取召唤师技能图标
      let summoner_spells_rel = await page.$$eval("aside", (options) => {
        let item = options[0];
        let children = Array.from(
          item.childNodes[0].childNodes[1].childNodes[0].childNodes
        );
        return children.map((item) => {
          return {
            icon: [
              {
                url: item.childNodes[0].childNodes[0].childNodes[0]
                  .childNodes[0].childNodes[0].src,
                name: item.childNodes[0].childNodes[0].childNodes[0]
                  .childNodes[0].childNodes[0].alt,
              },
              {
                url: item.childNodes[0].childNodes[0].childNodes[1]
                  .childNodes[0].childNodes[0].src,
                name: item.childNodes[0].childNodes[0].childNodes[1]
                  .childNodes[0].childNodes[0].alt,
              },
            ],
            use: item.childNodes[1].childNodes[0].childNodes[0].innerText,
            useNum: item.childNodes[1].childNodes[0].childNodes[2].innerText,
            win: item.childNodes[1].childNodes[1].innerText,
          };
        });
      });

      // 获取技能加点推荐
      let skill_recommendation_rel = {};
      skill_recommendation_rel.main = await page.$$eval(
        ".css-13hvktn > div",
        (options) => {
          return options.map((item) => {
            return {
              skill: item.childNodes[0].childNodes[1].innerText,
              icon: item.childNodes[0].childNodes[0].src,
              name: item.childNodes[0].childNodes[0].alt,
            };
          });
        }
      );
      skill_recommendation_rel.second = await page.$$eval(
        ".css-vegv5g strong",
        (options) => {
          return options.map((item) => {
            return {
              skill: item.innerText,
            };
          });
        }
      );

      // 出门装备
      let initial_rel = await page.$$eval(
        ".css-1yie7qw > tbody > tr",
        (options) => {
          let newArr = [options[0], options[1]];
          return newArr.map((item) => {
            let iconDom =
              item.childNodes[0].childNodes[0].childNodes[0].childNodes;
            let icon = [
              {
                url:
                  iconDom[0].childNodes[0].childNodes.length == 1
                    ? iconDom[0].childNodes[0].childNodes[0].src
                    : iconDom[0].childNodes[0].childNodes[1].src,
                name:
                  iconDom[0].childNodes[0].childNodes.length == 1
                    ? iconDom[0].childNodes[0].childNodes[0].alt
                    : iconDom[0].childNodes[0].childNodes[1].alt,
                num:
                  iconDom[0].childNodes[0].childNodes.length > 1
                    ? iconDom[0].childNodes[0].childNodes[0].innerText
                    : "",
              },
            ];

            if (iconDom.length > 1) {
              icon.push({
                url:
                  iconDom[1].childNodes[0].childNodes.length == 1
                    ? iconDom[1].childNodes[0].childNodes[0].src
                    : iconDom[1].childNodes[0].childNodes[1].src,
                name:
                  iconDom[1].childNodes[0].childNodes.length == 1
                    ? iconDom[1].childNodes[0].childNodes[0].alt
                    : iconDom[1].childNodes[0].childNodes[1].alt,
                num:
                  iconDom[1].childNodes[0].childNodes.length > 1
                    ? iconDom[1].childNodes[0].childNodes[0].innerText
                    : "",
              });
            }

            return {
              icon: icon,

              use: item.childNodes[1].childNodes[0].innerText,
              useNum: item.childNodes[1].childNodes[1].innerText,
              win: item.childNodes[2].childNodes[0].innerText,
            };
          });
        }
      );

      // 推荐装备
      let recommended_equipment_rel = await page.$$eval(
        ".css-1yie7qw > tbody > tr",
        (options) => {
          let newArr = [options[4], options[5]];
          return newArr.map((item) => {
            return {
              icon: [
                {
                  name: item.childNodes[0]
                    .querySelectorAll(".item_icon")[0]
                    .querySelector("img").alt,
                  url: item.childNodes[0]
                    .querySelectorAll(".item_icon")[0]
                    .querySelector("img").src,
                },
                {
                  name: item.childNodes[0]
                    .querySelectorAll(".item_icon")[1]
                    .querySelector("img").alt,
                  url: item.childNodes[0]
                    .querySelectorAll(".item_icon")[1]
                    .querySelector("img").src,
                },
                {
                  name: item.childNodes[0]
                    .querySelectorAll(".item_icon")[2]
                    .querySelector("img").alt,
                  url: item.childNodes[0]
                    .querySelectorAll(".item_icon")[2]
                    .querySelector("img").src,
                },
              ],
              use: item.childNodes[1].childNodes[0].innerText,
              useNum: item.childNodes[1].childNodes[1].innerText,
              win: item.childNodes[2].childNodes[0].innerText,
            };
          });
        }
      );

      // 获取克制英雄
      let restraint_rel = await page.$$eval("aside", (options) => {
        let item = options[0].childNodes[1];
        let children = Array.from(item.querySelectorAll("ul")[1].childNodes);
        // return children[0].innerHTML
        return children.map((item) => {
          return {
            icon: item.querySelector("img").src,
            win: item.querySelector(".win-rate").innerText,
          };
        });
      });

      // 被克制
      let counter_rel = await page.$$eval("aside", (options) => {
        let item = options[0].childNodes[1];
        let children = Array.from(item.querySelectorAll("ul")[0].childNodes);
        // return children[0].innerHTML
        return children.map((item) => {
          return {
            icon: item.querySelector("img").src,
            win: item.querySelector(".win-rate").innerText,
          };
        });
      });

      // 符文
      let rune_rel = await page.$$eval(".css-1g8di9l", (options) => {
        let item1 = options[0].childNodes[0]; // 主系
        let item2 = options[0].childNodes[2]; // 副系
        let item3 = options[0].childNodes[4]; // 天赋

        let item1Dom = [],
          item2Dom = [],
          item3Dom = [];

        item1.querySelectorAll("img").forEach((item) => {
          item1Dom.push({
            url: item.src,
            name: item.alt,
          });
        });

        item2.querySelectorAll("img").forEach((item) => {
          item2Dom.push({
            url: item.src,
            name: item.alt,
          });
        });

        item3.querySelectorAll("img").forEach((item) => {
          item3Dom.push({
            url: item.src,
            name: item.alt,
          });
        });

        return {
          title: `${item1.querySelector("h5").innerText} - ${
            item2.querySelector("h5").innerText
          } - ${item3.querySelector("h5").innerText}`,
          continer1: {
            title: item1.querySelector("h5").innerText,
            children: item1Dom,
          },
          continer2: {
            title: item2.querySelector("h5").innerText,
            children: item2Dom,
          },
          continer3: {
            title: item3.querySelector("h5").innerText,
            children: item3Dom,
          },
        };
      });

      browser.close();
      R({
        skill,
        summoner_spells_rel,
        skill_recommendation_rel,
        initial_rel,
        recommended_equipment_rel,
        restraint_rel,
        counter_rel,
        rune_rel,
      });
    });
  }

  getDouble(url, type) {
    return new Promise(async (R) => {
      try {
        // 创建一个浏览器对象
        const browser = await puppeteer.launch({
          headless: true,
          waitUntil: "domcontentloaded",
        });

        // 打开一个新的页面
        const page = await browser.newPage();

        await page.goto(url, {
          timeout: 0, // 不限制超时时间
        });
        setTimeout(async () => {
          let html = await page.$$eval(".body-item", (options) => {
            return options.map((item, idx) => {
              let children = item.childNodes;
              return {
                ranking: idx + 1,
                hero1: children[4].childNodes[0].querySelector("img").src,
                hero2: children[4].childNodes[2].querySelector("img").src,
                win: children[6].innerText,
                debut: children[8].innerText,
                hero_info: [
                  {
                    name: children[4].childNodes[0].innerText,
                    head: children[4].childNodes[0].querySelector("img").src,
                    disabled: children[10].childNodes[0].innerText,
                    win: children[12].childNodes[0].innerText,
                  },
                  {
                    name: children[4].childNodes[2].innerText,
                    head: children[4].childNodes[2].querySelector("img").src,
                    disabled: children[10].childNodes[2].innerText,
                    win: children[12].childNodes[2].innerText,
                  },
                ],
              };
            });
          });
          browser.close();
          html.forEach((item) => {
            item.type = type;
            item._createTime = new Date().getTime();
            item._updateTime = new Date().getTime();
          });
          R(html);
        }, 5000);
      } catch (error) {
        R(new newError({ msg: "系统错误" }));
      }
    });
  }

  getzhuzhuList(url) {
    return new Promise(async (R) => {
      try {
        // 创建一个浏览器对象
        const browser = await puppeteer.launch({
          headless: true,
        });
        // 打开一个新的页面
        const page = await browser.newPage();
        await page.goto(url, {
          timeout: 0, // 不限制超时时间
        });
        // await page.$$eval('.result', (options) => {
        //   return options.map(async (item) => {
        //     item.children[0].children[1].click();

        //   })
        // })

        // setTimeout(async () => {
        //   let element = await page.waitForSelector(".btn_b")
        //   element.click()
        // }, 1000)

        // setTimeout(async () => {
        //   let html = await page.$$eval('.result', (options) => {
        //     return options.map((item) => {
        //       let createTime,nickName;
        //       if(item.children.length == 6){
        //         createTime = item.children[3].innerText
        //         nickName = item.children[4].innerText
        //       }else{
        //         createTime = item.children[2].innerText
        //         nickName = item.children[3].innerText
        //       }
        //       return {
        //         title: item.children[0].children[1].innerText,
        //         href: item.children[0].children[1].href,
        //         createTime,
        //         nickName,
        //       }
        //     })
        //   })
        //   browser.close();
        //   R(html)
        // }, 2000)
        let html = await page.$$eval(".nr", (options) => {
          return options.map((item) => {
            return {
              nickName: item.children[1].innerText,
              title: item.children[0].innerText,
              href: item.children[0].children[0].href,
              createTime: "",
            };
          });
        });
        browser.close();
        R(html);
      } catch (error) {
        R([]);
      }
    });
  }

  getBaiduUrl(url) {
    return new Promise(async (R) => {
      try {
        // 创建一个浏览器对象
        const browser = await puppeteer.launch({
          headless: false,
        });
        // 打开一个新的页面
        const page = await browser.newPage();
        await page.goto(url, {
          timeout: 0, // 不限制超时时间
        });
        let html = await page.$$eval(".down", (options) => {
          return options.map((item) => {
            return {
              url: item.children[0].href,
            };
          });
        });

        const newPage = await browser.newPage();
        await newPage.goto(html[0].url, {
          timeout: 0, // 不限制超时时间
        });

        let newHtml = await newPage.$$eval("a", (options) => {
          return options.map((item) => {
            return {
              url: item.baseURI,
            };
          });
        });

        browser.close();
        if (html.length > 0) {
          R(newHtml[0].url);
        } else {
          R("");
        }
      } catch (error) {
        R("");
      }
    });
  }
}

module.exports = {
  getWebFun,
};

# 西二在线第二次合作轮考核

## 二手游戏账号交易平台 ヾ(≧▽≦*)o

## 闲猫MewStore 安卓端

#### 闲猫MewStore 安卓端ヾ(≧▽≦*)o

#### 队伍名：闲猫吃咸鱼(Android分队)

#### 平台名：闲猫MewStore

> 本项目是一个**二手游戏账号交易**平台，主要提供游戏账号交易服务，为玩家提供便利和安全性。本项目Android端采用原生安卓kotlin编写，后端分别为Java（采用Springboot）和Python（Flask），**实现跨框架跨语言合作**。

#### 合作成员信息

##### 网页端

成员： 吴荣榜

语言与框架：Kotlin 原生安卓

学号：222200314

##### 美术

成员：马雁语

相关软件：XD PS procreate

学号：832204101

##### 后端

成员1：叶宇滟（同时参与Web端和Android端后端的编写）

语言与框架：Java (采用Springboot编写)

学号：222200307

成员2：林荣达（参与Android端后端的编写）

语言与框架：Python（Flask）

学号：062200237

#####  项目仓库地址

前端项目地址：https://github.com/ROBINRUGAN/Android_Round5

#### 技术栈

##### 前端

- `Kotlin原生` + `glide` + `okhttp` + `retrofit` + `viewpager2` + `socket.io-client` + `eventbus`

##### 项目目录树

```
//这个是android_round5文件夹下的内容
C:android_round5
│  AdminGoodsActivity.kt
│  AppService.kt
│  ChatroomActivity.kt
│  DetailActivity.kt
│  ImageActivity.kt
│  LoginActivity.kt
│  MainActivity.kt
│  Myapplication.kt
│  MyBuyActivity.kt
│  MyFavoriteActivity.kt
│  MySellActivity.kt
│  RegisterActivity.kt
│  SettingsActivity.kt
│  TokenInterceptor.kt
│
├─adapter
│      AdminItemAdapter.kt
│      ChatAdapter.kt
│      HomeItemAdapter.kt
│      ImageAdapter.kt
│      MessageItemAdapter.kt
│      MyBuyAdapter.kt
│      MySellAdapter.kt
│      OtherItemAdapter.kt
│      SettingsAdapter.kt
│
├─entity
│      Order.kt
│      Params.kt
│
├─ui
│  ├─admingood
│  │      AdminGoodAllFragment.kt
│  │      AdminGoodApprovedFragment.kt
│  │      AdminGoodRejectedFragment.kt
│  │      AdminGoodUnconfirmedFragment.kt
│  │
│  ├─deal
│  │      DealFragment.kt
│  │      DealViewModel.kt
│  │
│  ├─home
│  │      HomeFragment.kt
│  │      HomeViewModel.kt
│  │
│  ├─me
│  │      MeFragment.kt
│  │      MeViewModel.kt
│  │
│  ├─message
│  │      MessageFragment.kt
│  │      MessageViewModel.kt
│  │
│  ├─mybuy
│  │      AllFragment.kt
│  │      ApprovedFragment.kt
│  │      RejectedFragment.kt
│  │      UnconfirmedFragment.kt
│  │      UnpaidFragment.kt
│  │
│  ├─mysell
│  │      SellAllFragment.kt
│  │      SellApprovedFragment.kt
│  │      SellRejectedFragment.kt
│  │      SellUnconfirmedFragment.kt
│  │      SellUnpaidFragment.kt
│  │
│  └─upload
│          UploadFragment.kt
│          UploadViewModel.kt
│
└─util
        ContextUtil.kt //获取上下文的工具类
        ImageLoadUtil.java //图片加载的工具类
        jsonOf.kt //转成json格式的工具类
        UriPathHelper.kt //解析Uri的工具类
```

#### 项目亮点&加分项

- 搭建MewStore平台，同时开发网页端和Android端，并实现数据同步，双端互通。
- 实现了跨语言合作，攻破核心对接障碍，前端且能成功对接双后端。
- 新增短信验证码的登录、注册方式，申请正式短信签名和模板，已经正式上线并可以面向大众使用。
- 缓存token，添加拦截器，这样可以实现重新启动应用时还能保持登录状态。
- 采用轮播图动态滚动展示商品图片，风格新颖且美观，并且支持查看大图。
- 在订单页、审核页的展示，我仿着bilibili App进行设计，利用ViewPager2和TabLayout的配套使用进行信息的分栏处理。
- 自己设计了一个推荐算法给后端们照着编写，用来计算“猜你喜欢”的游戏商品。
- 实现聊天室功能，可以实现实时对话聊天，并拥有处理已读未读和历史记录的关系。
- 实现了双端交互，可以实现Web端-Android端、Web端-Web端、Android端-Android端的交互使用，并且数据互通且实时更新。
- 按照美术出图编写xml，基本做到完美还原。
- 项目具有个性与特色，有专门的Logo，风格不同于当下千篇一律的页面布局与风格，实现了创新和突破。
- （彩蛋）实现Web端和Android端梦幻联动，手机访问网址会重定向到下载页，提示用户下载App，点击“MewStore For Android”即可下载APK。
- 项目成功部署上线，利用Docker和Nginx以及Screen解决Python部署问题。
- 将项目绑定到自己的域名上，可以实现公网访问，可供大众使用。
- 利用aes加密敏感数据，提升安全性。

#### 未解决的问题

ButtonNavigationView的中间的按钮没有办法放大突出，各个图标只能呈现相同大小

#### "猜你喜欢"算法

![img](https://github.com/ROBINRUGAN/ROBINRUGAN/blob/main/assets/output.png)

##### 项目进度

安卓端和Web端进度同步，现完成了绝大部分功能（唯独除了举报功能），可以投入正常使用。此外举报功能后端已经写好（配套的相关逻辑还有拉黑、冻结、发送系统消息）但由于时间并不充裕，且得分占比权重并不大（放在了加分项里，甚至分值只有5%），涉及到的增删查改只是较为麻烦而已，没有什么新的内容，所以我们举报功能暂时不上线，并把注意力放在了相关逻辑的进一步优化和细节处理。
此外，我认为，一个项目最重要的部分就是投入使用，如果不能给其他人使用，只能自己自娱自乐，在我看来这个项目就是没意义的。因此，我们将两个后端部署在服务器上，解决了python部署不了的问题，也把前端部署并绑定自己的域名，实现项目正式上线。
并且，我们在相关细节方面进行了润色，尽可能提高用户体验感，也实现了两端跨语言合作、双端梦幻联动、双端互通，这也是其他组做不到的（只有我们开多方向 bushi）。
所以，我觉得安卓端和网页端的进度是97%

#### 合作情况（中期答辩时）

- 前端目前根据美术所绘制的页面进行编写，已经编写了大部分的页面和跳转功能，并优化了组件的展示效果
- 两个后端之间解决了token跨语言解码问题，打破合作障碍，并初步编写大部分的接口
- 虽然在一开始的时候举步维艰，并且遭遇大量考试冲击挤兑，不过慢慢地通过讨论与摸索，合作各方都逐渐熟练起来，不论是自己的项目开发问题抑或是合作问题都得到了很好的解决，开发速度开始稳步提高。

#### 合作情况（现在）

- Web后端这边一个队友摆烂，导致Web后端API无法对接，不过好在还有一套Android后端，进行了及时补救
- 剩下的队友们尽心尽力，有什么问题都会第一时间在大群提出来，然后一起讨论，然后队友们也很勤奋用心，遇到问题能够做到迎难而上。
- 并且后端们和我一样都是肝帝（bushi），比如林荣达和我一起通宵对接websocket，真的很感动！
- 还有就是叶宇滟凌晨三点被我叫起来改接口（），真的辛苦了！
- 以及我们的美术马雁语，她其实本来工作量只需要画一个端的套图就可以了的，但是她不仅画了网页端，还画了安卓端的图，相当于将她原本的工作量乘2，真的很感激很感激！
- 因此总的来说，我们团队都是一心想把项目写好的人，我自认为我们的合作情况是非常优秀的。

#### App下载网址：

如果你在使用电脑，直接游览器点开下载不了，请复制到浏览器粘贴~

http://106.14.35.23:8888/down/P4ZUU6DLNCy1.apk

如果是手机，那么可以直接访问我们已经部署了的网页，他会重定向你到下载页：

http://mewtopia.cn

![img](https://github.com/ROBINRUGAN/ROBINRUGAN/blob/main/assets/MewSore%20for%20android.png)

#### 视频演示：

暂时无法在飞书文档外展示此内容

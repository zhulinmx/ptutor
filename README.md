# 基于android的家教app
## 背景
毕业设计的选题<br>
完全被坑/(ㄒoㄒ)/<br>
导师给我改的题，从没写过Android的我，在办公室的瞬间我的内心是崩溃的<br>
不过还好，我也是顺利完成的大组答辩，别的倒是没什么记忆，只是记得当天我是那个答的最迅速的<br>
## 功能
导师说我是高仿<br>
我不只知道是赞美还是讽刺，只好苦涩地说，被你看出来了<br>
### 主界面
logo设计理念来自于尔康手<br>
![](https://github.com/zhulinmx/ptutor/blob/project_img/Screenshot_2017-05-29-17-13-34.png)
![](https://github.com/zhulinmx/ptutor/blob/project_img/Screenshot_2017-05-29-13-16-22.png) <br>
因为我写过Android和手机微信端小程序，我发现这种特别注重的就是和用户的交互，最好让人进来就知道要干嘛，从哪个门出去，所以寒假时候在家看了几本app界面设计的相关书籍，以前实习第一个月老板让我先从调试JS开始，总觉得真是烦烦烦，对着浏览器各种兼容问题，发誓这辈子都不要写前端，但是后面我渐渐发现即使写后端也还是要懂一些前端，对，我说的是nodejs￣▽￣ <br>
![](https://github.com/zhulinmx/ptutor/blob/project_img/Screenshot_2017-05-29-13-15-47.png) <br>
<br>家教app无外乎是两个角色：老师/学生。这里我是将两种角色放在了同一个客户端上，因为观望其他app也是这么做的，比如boss直聘，底部是波纹的动画效果，用了一个四段二阶贝塞尔曲线，我室友说像屏幕里进污水了，我理解为褒奖2333 <br>
其实整个app就是围绕着：学生<-->课程<-->老师 <br>
最后app写一半的时候，发现这就是个电商嘛╰(￣▽￣)╭ <br>
顺便吐槽一下Android调试工具╰(￣▽￣)╭先是使用Eclipse自带的ADT来运行app，但是速度太慢，于是改用virtualBox与genymotion搭建调试环境，前面好好的，某天突然崩掉之后再也跑步起来，最后为求系统运行的真实性，直接改成了真机调试（手机型号是Hisense M20-T，这是寝室办宽带送的2333），最后这种方式也是最让我满意的一种方式 <br>
下面我分别解释两个角色的大体功能：
### 学生端
以学生身份进入客户端包括的功能有注册与登录、注销登录、查询课程、查看课程、收藏课程、订购课程、评价课程、个人信息管理、课程安排 <br>
主页面
![](https://github.com/zhulinmx/ptutor/blob/project_img/Screenshot_2017-05-28-20-11-24.png) <br>
![](https://github.com/zhulinmx/ptutor/blob/project_img/Screenshot_2017-05-29-13-30-11.png) <br>
![](https://github.com/zhulinmx/ptutor/blob/project_img/Screenshot_2017-05-29-13-50-19.png) <br>
模糊搜索 <br>
![](https://github.com/zhulinmx/ptutor/blob/project_img/Screenshot_2017-05-30-13-43-43.png) <br>
课程详情 <br>
![](https://github.com/zhulinmx/ptutor/blob/project_img/Screenshot_2017-05-28-20-13-55.png) <br>
![](https://github.com/zhulinmx/ptutor/blob/project_img/Screenshot_2017-05-29-13-30-40.png) <br>
课程相关老师 <br>
![](https://github.com/zhulinmx/ptutor/blob/project_img/Screenshot_2017-05-28-20-12-51.png) <br>
个人信息</br>
![](https://github.com/zhulinmx/ptutor/blob/project_img/Screenshot_2017-05-30-00-02-58.png) <br>
![](https://github.com/zhulinmx/ptutor/blob/project_img/Screenshot_2017-05-28-20-12-51.png) <br>
![](https://github.com/zhulinmx/ptutor/blob/project_img/Screenshot_2017-05-30-00-02-38.png) <br>








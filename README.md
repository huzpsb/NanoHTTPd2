NanoHTTPd2
======================
新一代中小型后端开发框架

“杀鸡别用牛刀；写小型web后端别用企业级框架！”

Version 1.5

简介
======================
本项目是 NanoHTTPd (https://github.com/gseguin/NanoHTTPd) 的延续版。

同时，加入了很多~~方便外包人摸鱼~~简化小型项目开发难度的功能。

功能亮点
======================

* 伪数据库。能够直接把ConcurrentHashMap（前提是Key和Value都可以序列化）当作数据库使用；甚至可以在不同版本的序列化对象中自动互相转换。
* 伪JSON。可以把简单对象直接作为JSON格式返回。
* CSV导出。可以导出所有数据为CSV表格。自动添加表头。
* 多线程。全自动多线程香不香？
* 文件支持。能够处理含文件的POST/GET请求；有FileServer工具类，可以简单轻松的提供本地文件。
* Logger与Console。摆脱庞大而臃肿还爆出过RCE的Log4j！

（不要跟我讲数据量特别、特别大的时候的性能。你代码有多少人用你自己不清楚？Spring的确适合企业开发，但是一个中学的评教系统啊之类的真的就没必要了好吧。）

Edit:进行了一次压力测试。测试接口为DBServer的/create接口。
100线程，每个线程1k次调用。耗时67s，相当于每次调用0.67ms，失败次数0。100k个记录总没话说了吧？67秒里面是有一次save的，线程安全也没有问题。TwT

框架特色
======================

* 小：框架本身只有50kb，支持边缘计算（树莓派）等应用场景。
* 简：框架不依赖任何第三方库，你可以轻易地理解、修改并重新编译框架，实现自己需要的功能。
* 香：以下例子只有2kb（算上所有类4kb，你可以在example文件夹里找到它的完整代码），却实现了一个带伪数据库和4个接口的后端。

````java
package example.Backend;

import nano.http.d2.console.Logger;
import nano.http.d2.core.Response;
import nano.http.d2.database.NanoDb;
import nano.http.d2.json.NanoJSON;
import nano.http.d2.serve.ServeProvider;
import nano.http.d2.utils.Mime;
import nano.http.d2.utils.Status;

import java.util.Properties;

public class DBServer implements ServeProvider {
    public static final NanoDb<String, User> udb;

    static {
        try {
            udb = new NanoDb<>("students.udb");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response serve(String uri, String method, Properties header, Properties parms, Properties files) {
        try {
            switch (uri) {
                case "/create":
                    User u = new User();
                    u.age = Integer.parseInt(parms.getProperty("age"));
                    u.home = parms.getProperty("home");
                    udb.set(parms.getProperty("name"), u);
                    return new Response(Status.HTTP_OK, Mime.MIME_PLAINTEXT, "Success");
                case "/delete":
                    udb.remove(parms.getProperty("name"));
                    return new Response(Status.HTTP_OK, Mime.MIME_PLAINTEXT, "Success");
                case "/download":
                    Response r = new Response(Status.HTTP_OK, Mime.MIME_DEFAULT_BINARY, udb.toCSV(User.class, "姓名", Locates.getLocalizer()));
                    r.addHeader("Content-Disposition", "attachment; filename=\"download.csv\"");
                    return r;
                case "/query":
                    Response r2 = new Response(Status.HTTP_OK, Mime.MIME_JSON, NanoJSON.asJSON(udb.query(parms.getProperty("name")), User.class));
                    r2.addHeader("Access-Control-Allow-Origin", "*");
                    return r2;
                default:
                    Logger.warning(uri);
                    // Please read the notes in ExampleServer.java (package: nano.http.d2.serve)
                    // You may want to use FileServer.serveFile() here!
                    return new Response(Status.HTTP_NOTFOUND, Mime.MIME_PLAINTEXT, "Not found");
            }
        } catch (Exception e) {
            return new Response(Status.HTTP_INTERNALERROR, Mime.MIME_PLAINTEXT, "Mal-formatted request!");
        }
    }
}

````

````
/create
创建一个学生
接口类型：POST
参数：
name - 学生名称
age - 学生年龄
home - 学生家庭住址
返回：
200 - 成功
其他 - 失败原因

/delete
删除一个学生
接口类型：POST
参数：
name - 学生名称
返回：
200 - 成功
其他 - 失败原因

/download
接口类型：GET
以电子表格的形式下载所有学生的信息
参数：
无
返回：
电子表格。（设定的是浏览器访问）

/query
查询一个学生的个人信息
接口类型：GET
参数：
name - 学生名称
返回：
json - 成功
例子: {age:15,home:"BeiJing"}
其他 - 失败原因

伪数据库会被保存至“students.udb”文件。
````

使用教程
===========

* 懒得写，反正写了也没有人看
* 可以去看看example文件夹，里面有完整的例子。
* ~~还有问题就请您RTFSC吧，就50kb也不多~~

联系信息
======================
NanoHTTPd原作者：

* Copyright © 2001,2005-2011 Jarno Elonen <elonen@iki.fi>
* Copyright © 2010 Konstantinos Togias <info@ktogias.gr>

NanoHTTPd2修改者：

* Copyright © 2022 huzpsb <huzpsb@qq.com>

~~毒瘤出题人~~测试人员：

* Copyright © 2022 lbh <1601078366@qq.com>

#### 完结撒花

( Ad: 如果您是个人开发者而不是接单佬，也一定请康康咱的https://github.com/BukkitHTTP/BukkitHTTP )

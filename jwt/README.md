jwt的组成

jwt由三部分组成，前面两个部分是base编码的明文，第一部分标识是一个jwt和对应的签名算法

第二部分为一些额外的信息的明文，比如用户名和超时时间

第三部分是对前面做的一个签名，用用户的密码作为秘钥，签名后如果别人篡改了数据不生成签名则校验不通过，如果篡改了数据并且伪造了一个签名则由于黑客无法知道用户的密码，所以造出来的签名时使用的秘钥和校验时不匹配（因为服务器的秘钥来自数据库），也无法校验通过，以此达到数据的安全性

jwt的使用 

访问接口需要带上token，在请求头里面增加一个header：Authorization，值为token，token是从login里面登录获取的 

可以通过设置com.study.jwt.service.filter.JwtFilter.initAnonymousPaths()方法，添加自定义免token的接口，比如swaggerui可以无需token访问

![未授权访问](https://github.com/qq1623299667/study/blob/master/jwt/src/main/resources/image/%E6%9C%AA%E6%8E%88%E6%9D%83%E8%AE%BF%E9%97%AE.png)

![登录获取token](https://github.com/qq1623299667/study/blob/master/jwt/src/main/resources/image/%E7%99%BB%E5%BD%95%E8%8E%B7%E5%8F%96token.png)

![授权访问](https://github.com/qq1623299667/study/blob/master/jwt/src/main/resources/image/%E6%8E%88%E6%9D%83%E8%AE%BF%E9%97%AE.png)


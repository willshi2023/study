jwt的使用 

访问接口需要带上token，在请求头里面增加一个header：Authorization，值为token，token是从login里面登录获取的 

可以通过设置com.study.jwt.service.filter.JwtFilter.initAnonymousPaths()方法，添加自定义免token的接口，比如swaggerui可以无需token访问

![未授权访问](https://github.com/qq1623299667/study/blob/master/jwt/src/main/resources/image/%E6%9C%AA%E6%8E%88%E6%9D%83%E8%AE%BF%E9%97%AE.png)

![登录获取token](https://github.com/qq1623299667/study/blob/master/jwt/src/main/resources/image/%E7%99%BB%E5%BD%95%E8%8E%B7%E5%8F%96token.png)

![授权访问](https://github.com/qq1623299667/study/blob/master/jwt/src/main/resources/image/%E6%8E%88%E6%9D%83%E8%AE%BF%E9%97%AE.png)


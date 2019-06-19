sql目录：springboot\springboot-mybatis-multidatasource\src\main\resources\sql

场景：有两个数据库，一个叫test1，一个叫test2。需要有一个工程同时访问两个不同的数据库。当然也可以是多个数据库

首先是项目的结构，需要注意的是mapper.java的路径需要放在不同位置，这样配置mapper扫描的时候可以指定不同的数据源

![Image text](https://github.com/qq1623299667/study/blob/master/springboot/springboot-mybatis-multidatasource/src/main/resources/image/%E9%A1%B9%E7%9B%AE%E7%BB%93%E6%9E%84.png)  

![Image text](https://github.com/qq1623299667/study/blob/master/springboot/springboot-mybatis-multidatasource/src/main/resources/image/mapper%E7%9A%84%E7%BB%91%E5%AE%9A.png)  

![Image text](https://github.com/qq1623299667/study/blob/master/springboot/springboot-mybatis-multidatasource/src/main/resources/image/%E4%B8%BA%E7%A8%8B%E5%BA%8F%E6%8C%87%E5%AE%9A%E4%B8%8D%E5%90%8C%E7%9A%84%E6%95%B0%E6%8D%AE%E6%BA%90.png)  

下面是结果图，证明确实访问到了不同的数据源

![Image text](https://github.com/qq1623299667/study/blob/master/springboot/springboot-mybatis-multidatasource/src/main/resources/image/%E8%AE%BF%E9%97%AE%E6%8E%A5%E5%8F%A3%E8%8E%B7%E5%8F%96%E7%AC%AC%E4%B8%80%E4%B8%AA%E6%95%B0%E6%8D%AE%E6%BA%90%E9%87%8C%E7%9A%84%E6%95%B0%E6%8D%AE.png)  

![Image text](https://github.com/qq1623299667/study/blob/master/springboot/springboot-mybatis-multidatasource/src/main/resources/image/%E8%AE%BF%E9%97%AE%E6%8E%A5%E5%8F%A3%E8%8E%B7%E5%8F%96%E7%AC%AC%E4%BA%8C%E4%B8%AA%E6%95%B0%E6%8D%AE%E6%BA%90%E9%87%8C%E7%9A%84%E6%95%B0%E6%8D%AE.png)  

最后，由于yml额外的数据会引起警告，增加一个配置文件，解除告警。0 error(s), 0 warning(s)

![Image text](https://github.com/qq1623299667/study/blob/master/springboot/springboot-mybatis-multidatasource/src/main/resources/image/%E5%A2%9E%E5%8A%A0yml%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6%E8%A7%A3%E9%99%A4%E8%AD%A6%E5%91%8A.png)  
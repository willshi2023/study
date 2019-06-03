emq的详细解读，参考：https://www.jianshu.com/p/3d5b487c6860  
emq的启动：docker run --name emq -p 18083:18083 -p 1883:1883 -p 8084:8084 -p 8883:8883 -p 8083:8083 -p 8080:8080 -d registry.cn-hangzhou.aliyuncs.com/synbop/emqttd:2.3.6  
docker教程：参考本仓库docker的PPT  
emq控制台：http://192.168.99.100:18083/#/  
实测图片：  
![Image text](https://github.com/qq1623299667/study/blob/master/emq/src/main/resources/image/emq%E6%8E%A7%E5%88%B6%E5%8F%B0.png)  
![Image text](https://github.com/qq1623299667/study/blob/master/emq/src/main/resources/image/%E5%8F%91%E9%80%81%E8%AF%B7%E6%B1%82%E6%B5%8B%E8%AF%95%E6%B6%88%E6%81%AF%E7%9A%84%E5%8F%91%E9%80%81.png)  
![Image text](https://github.com/qq1623299667/study/blob/master/emq/src/main/resources/image/%E6%8E%A7%E5%88%B6%E5%8F%B0%E6%98%BE%E7%A4%BA%E6%94%B6%E5%88%B0%E8%AE%A2%E9%98%85%E6%B6%88%E6%81%AF.png)  
转发消息：  
![Image text](https://github.com/qq1623299667/study/blob/master/emq/src/main/resources/image/%E6%B6%88%E6%81%AF%E7%9A%84%E8%BD%AC%E5%8F%91.png)  


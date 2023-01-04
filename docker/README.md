## 说明
本项目主要用于hub.docker.com镜像制作（efak官方不提供docker镜像，因此自己做一个）

## 镜像制作
```shell script
docker build -t seawenc/efak:3.0.4 .
docker push seawenc/efak:3.0.4
```

## 用法：
```
docker run -d -p 8048:8048 \
--restart=always  --name efak \
-v /tmp/logs:/opt/app/efak/logs \
-v /data/workspace/my/efak-src/efak-web/src/main/resources/conf/system-config.properties:/opt/app/efak/conf/system-config.properties \
-v /tmp/db:/opt/app/efak/db \
seawenc/efak:3.0.4
```
访问：http://localhost:8048

使用文档，请参考：https://gitee.com/seawenc/kafka-ha-installer
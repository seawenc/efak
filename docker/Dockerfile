FROM seawenc/centos7fat:1.0
MAINTAINER chengsheng(seawenc)

#rm -rf /etc/yum.repos.d/* && \
#curl -o /etc/yum.repos.d/CentOS-Base.repo https://mirrors.aliyun.com/repo/Centos-7.repo && \


#RUN wget -c http://192.168.56.1:8000/efak-web-${VERSION}-bin.tar.gz（原始地址：https://github.com/smartloli/kafka-eagle-bin/archive/v3.0.1.tar.gz ）
# 此包通过 git@github.com:sewenc/efak-src.git master源码打包
ENV VERSION=3.0.5
RUN cd /opt/app && curl -O http://192.168.56.1:8000/efak-web-${VERSION}-bin.tar.gz && \
    tar -xzf efak-web-${VERSION}-bin.tar.gz && \
    rm -rf efak-web-${VERSION}-bin.tar.gz && \
    mv efak-web-${VERSION} efak

#替换jar包，解决两个bug：1.不支持双ip，2.jmx异常时提示不对
#已提merge request, 合并后，用新版本，将不再需要以下两行内容: https://github.com/smartloli/EFAK/pull/649
#COPY /efak-common-${VERSION}.jar /
#RUN sed -i '/ke.war/a\ \\cp -f /efak-common-3.0.1.jar /opt/app/efak/kms/webapps/ke/WEB-INF/lib/' /opt/app/efak/bin/ke.sh

ENV KE_HOME=/opt/app/efak
ENV PATH $KE_HOME/bin:$PATH
WORKDIR /opt/app/efak
COPY /ke-start.sh /
COPY /healthcheck.sh /
#添加建康检查
HEALTHCHECK --start-period=300s --retries=1 --interval=10s --timeout=5s CMD sh /healthcheck.sh

EXPOSE 8048

CMD ["sh","/ke-start.sh"]
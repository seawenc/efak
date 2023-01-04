## 通过判断接口是否正常来判断应用是否正常
curl -i --connect-timeout 3 http://127.0.0.1:8048
_RET=$?
if [ $_RET != 0 ]; then
  # 杀死容器主进程，让其重启
  echo '' > /opt/app/efak/logs/log.log
  ps -ef | grep tail | head -1 |awk '{print $2}' | xargs -I {} kill -9 {}
  exit 1
else
  echo '正常'
  exit 0
fi
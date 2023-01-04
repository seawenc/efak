sh /opt/app/efak/bin/ke.sh restart
# 官方版本启动无守护进程，因此直接查看日志，再加建康检查
tail -100f /opt/app/efak/logs/log.log
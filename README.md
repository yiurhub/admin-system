# admin-system
## 数据监控系统

> 后端 docker run script
```shell
docker run -d -p 9786:9786 -v /home/web/admin-system/sources/:/www/sources -v /home/web/admin-system/log:/www/log --name admin-system admin-system
```

> 前端 docker run script
```shell
docker run -d -p -v /usr/local/nginx/conf/nginx.conf:/etc/nginx/nginx.conf --name admin-system-vue admin-system-vue
docker run -d -p 80:80 -v /home/web/admin-system-vue:/usr/share/nginx/html:ro -v /usr/local/nginx/conf/nginx.conf:/etc/nginx/nginx.conf:ro --name admin-system-vue nginx
```

> 界面展示

![image-20220620135133923](.\images\image-20220620135133923.png)

![image-20220620135059263](.\images\image-20220620135059263.png)

![image-20220620135109378](.\images\image-20220620135109378.png)

![image-20220620135024453](.\images\image-20220620135024453.png)


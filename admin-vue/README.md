> docker run script
```shell
docker run -d -p -v /usr/local/nginx/conf/nginx.conf:/etc/nginx/nginx.conf --name admin-system-vue admin-system-vue
docker run -d -p 80:80 -v /home/web/admin-system-vue:/usr/share/nginx/html:ro -v /usr/local/nginx/conf/nginx.conf:/etc/nginx/nginx.conf:ro --name admin-system-vue nginx
```
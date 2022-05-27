> docker run script
```shell
docker run -d -p 9786:9786 -v /home/web/admin-system/sources/:/www/sources -v /home/web/admin-system/log:/www/log --name admin-system admin-system
```
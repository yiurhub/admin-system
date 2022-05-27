FROM nginx

MAINTAINER Yiur <3227341995@qq.com>

ENV WORK "/usr/share/nginx/html"

MAINTAINER $WORK

COPY dist/* /$WORK

CMD ["--server.port=80"]

EXPOSE 80
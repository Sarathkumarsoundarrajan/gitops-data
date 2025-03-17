FROM nginx:latest
RUN rm -rf /usr/share/nginx/html/*
ADD dist/cecom /usr/share/nginx/html
RUN ls /usr/share/nginx/html
RUN touch /etc/nginx/conf.d/default.conf
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
ENTRYPOINT [ "nginx" , "-g" , "daemon off;"]
FROM nginx:latest
RUN rm -rf /usr/share/nginx/html/*
RUN mkdir /usr/share/nginx/html/dist
ADD dist /usr/share/nginx/html/dist
RUN touch /etc/nginx/conf.d/default.conf
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
ENTRYPOINT [ "nginx" , "-g" , "daemon off;"]

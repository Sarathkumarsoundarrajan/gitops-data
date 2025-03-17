# FROM nginx:alpine
# COPY . /usr/share/nginx/html
FROM php:8.1.0
RUN apt-get update -y && apt-get install -y openssl zip unzip git libonig-dev
RUN curl -sS https://getcomposer.org/installer | php -- --install-dir=/usr/local/bin --filename=composer
RUN docker-php-ext-install pdo mbstring mysqli pdo_mysql
RUN mkdir -p /usr/src/
WORKDIR /usr/src/
COPY . /usr/src/
RUN rm composer.lock
RUN composer install
RUN php artisan key:generate
CMD php artisan serve --port=8000 --host=0.0.0.0
EXPOSE 8000

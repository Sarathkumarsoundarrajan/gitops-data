FROM php:7.4
RUN apt-get update -y && apt-get install -y \
    openssl \
    zip \
    unzip \
    git \
    libonig-dev \
    libpng-dev \
    libzip-dev
RUN docker-php-ext-install pdo mbstring mysqli pdo_mysql gd zip
RUN curl -sS https://getcomposer.org/installer | php -- --install-dir=/usr/local/bin --filename=composer
RUN mkdir -p /usr/src/
WORKDIR /usr/src/
COPY . /usr/src/
RUN rm -f composer.lock
RUN composer install
RUN php artisan key:generate
CMD php artisan serve --port=8000 --host=0.0.0.0
EXPOSE 8000
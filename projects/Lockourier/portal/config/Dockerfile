FROM wordpress:latest

# Copy custom plugins only if the directory exists
RUN if [ -d "custom-plugins" ]; then \
      cp -R custom-plugins /var/www/html/wp-content/plugins/; \
    fi

# Copy custom themes only if the directory exists
RUN if [ -d "custom-themes" ]; then \
      cp -R custom-themes /var/www/html/wp-content/themes/; \
    fi

EXPOSE 80
CMD ["apache2-foreground"]

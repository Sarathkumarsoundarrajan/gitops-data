def call() {
  container('php') {
    sh "echo 'Current workspace: ${WORKSPACE}'"
    sh 'cd ${WORKSPACE}'

    // Install WordPress CLI if not already installed
    sh '''
      if ! command -v wp &> /dev/null; then
        echo "Installing WordPress CLI..."
        curl -O https://raw.githubusercontent.com/wp-cli/builds/gh-pages/phar/wp-cli.phar
        chmod +x wp-cli.phar
        mv wp-cli.phar /usr/local/bin/wp
      fi
    '''

    // Check if composer.json exists and install dependencies
    if (fileExists('composer.json')) {
      sh 'composer install --no-dev'
    }

    // Set up WordPress core configuration
    sh '''
      if [ ! -f wp-config.php ]; then
        echo "Creating wp-config.php..."
        wp config create --dbname=${DB_NAME} --dbuser=${DB_USER} --dbpass=${DB_PASS} --dbhost=${DB_HOST} --path=${WORKSPACE} --allow-root
      fi
    '''

    // Install WordPress core if not already installed
    sh '''
      if ! wp core is-installed --path=${WORKSPACE} --allow-root; then
        echo "Installing WordPress core..."
        wp core install \
          --url=${SITE_URL} \
          --title="${SITE_TITLE}" \
          --admin_user=${ADMIN_USER} \
          --admin_password=${ADMIN_PASS} \
          --admin_email=${ADMIN_EMAIL} \
          --path=${WORKSPACE} \
          --allow-root
      fi
    '''

    // Activate plugins
    sh '''
      echo "Activating custom plugins..."
      wp plugin activate --all --path=${WORKSPACE} --allow-root
    '''

    // Activate theme with fallback if THEME_NAME is not set
    sh '''
      if [ -z "${THEME_NAME}" ]; then
        echo "THEME_NAME is not set. Using default theme 'astra'."
        THEME_NAME="astra"
      fi

      echo "Activating theme: ${THEME_NAME}..."
      if wp theme is-installed ${THEME_NAME} --path=${WORKSPACE} --allow-root; then
        wp theme activate ${THEME_NAME} --path=${WORKSPACE} --allow-root
      else
        echo "Error: Theme '${THEME_NAME}' is not installed. Available themes are:"
        wp theme list --path=${WORKSPACE} --allow-root
        exit 1
      fi
    '''

    // Set proper permissions for wp-content
    sh '''
      echo "Setting file permissions..."
      chmod -R 775 ${WORKSPACE}/wp-content
      chown -R www-data:www-data ${WORKSPACE}/wp-content
    '''
  }
}

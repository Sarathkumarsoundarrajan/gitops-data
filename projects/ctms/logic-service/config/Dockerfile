FROM node:18.16.0-alpine
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 3001
USER node
CMD [ "npm", "run", "dev" ]
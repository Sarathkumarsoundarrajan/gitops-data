FROM node:20.3.1-alpine
RUN mkdir -p /usr/src/
WORKDIR /usr/src/
COPY . /usr/src/
RUN npm install --force
EXPOSE 3000
CMD npm run start
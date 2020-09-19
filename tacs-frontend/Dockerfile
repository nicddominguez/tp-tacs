FROM node:12-slim

COPY ./app /tacs/app
COPY ./server /tacs/server

WORKDIR /tacs/app
RUN npm install
RUN npm run build
RUN cp -r /tacs/app/build /tacs/server

WORKDIR /tacs/server
RUN npm install

ENTRYPOINT ["npm", "start"]

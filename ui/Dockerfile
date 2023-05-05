# Build steps
FROM node:16-alpine AS build
WORKDIR /app

ARG baseUrl=http://localhost:3000
ENV VITE_BASE_URL=$baseUrl

COPY package.json .
COPY yarn.lock .
RUN yarn install
COPY . .

RUN npm run build

# Run steps
FROM bitnami/nginx:latest

COPY --from=build /app/dist /app
EXPOSE 8080

CMD ["/opt/bitnami/scripts/nginx/run.sh"]

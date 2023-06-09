# Getting Started

1. For this application I have used Spring boot 3.1.0 with java 17 and mysql db as a database.
2. For db migration I used flyway,  All the required sql scripts are in resource/db/migration folder
3. For mysql I used mysql docker image, same yaml file is attached in project, it can be started using (docker compose -f mysql.yaml up) command
4. After starting docker container , connect to db using credentials username: root, password: example and create schema with name sat_result
5. For all the apis postman collection is created and attached in project main folder
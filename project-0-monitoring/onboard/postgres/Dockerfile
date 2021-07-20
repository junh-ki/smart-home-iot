# Postgres Base (the same version as the one on Raspberry Pi 4)
FROM postgres:11.12-alpine

# Instruct the container to create the 'sensors' schema on the first run.
ENV POSTGRES_DB sensors

# Any '.sql' file found in the /docker-entrypoint-initdb.d/ of the container will be executed.
COPY sensors.sql /docker-entrypoint-initdb.d/

# docker build -t smarthomeiot/postgres .
# docker push smarthomeiot/postgres
# docker run --name postgres-0 -e POSTGRES_PASSWORD=password -d -p 5432:5432 smarthomeiot/postgres

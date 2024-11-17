# Use a imagem base oficial do PostgreSQL
FROM postgres:alpine

COPY ./docker-volumes/postgres /var/lib/postgresql/data

RUN chown -R postgres:postgres /var/lib/postgresql/data

EXPOSE 5432

ENV POSTGRES_DB=raybank
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=12345

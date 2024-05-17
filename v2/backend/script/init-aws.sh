#!/bin/bash
echo 'criando fila sqs'
awslocal sqs create-queue --queue-name keycloak-event-listener-queue  | tr -d '\r'
echo 'Fila criada com sucesso'
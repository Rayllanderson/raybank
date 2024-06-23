#!/bin/bash
# echo 'criando fila sqs'
# awslocal sqs create-queue --queue-name keycloak-event-listener-queue  | tr -d '\r'
# echo 'Fila criada com sucesso'

# echo 'criando bucket s3'
# awslocal s3api create-bucket --bucket rayllanderson-raybank-bucket
# awslocal s3api list-buckets
# echo 'Bucket criado com sucesso'

# echo 'criando funcao lambda'

# awslocal lambda create-function \
#  --function-name CreateThumbnail \
#  --zip-file fileb://${PWD}/lambda/function.zip  \
#  --handler lambda_function.lambda_handler \
#  --runtime python3.8 \
#  --role arn:aws:iam::000000000000:role/lambda-role


# echo 'lambda criada com sucesso'


echo 'iniciando terraform local'
cd terraform
terraform init
tflocal apply -auto-approve 

awslocal sqs list-queues
awslocal s3api list-buckets
awslocal lambda list-functions
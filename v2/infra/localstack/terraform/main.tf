provider "aws" {
  region                     = "us-east-1"
  access_key                 = "test"
  secret_key                 = "test"
  skip_credentials_validation = true
  skip_requesting_account_id = true
  skip_metadata_api_check    = true
}

resource "aws_s3_bucket" "raybank_bucket" {
  bucket = "rayllanderson-raybank-bucket"
}

resource "aws_sqs_queue" "keycloak_event_listener_queue" {
  name                      = "keycloak-event-listener-queue"
  delay_seconds             = 0
  max_message_size          = 262144
  message_retention_seconds = 345600
  visibility_timeout_seconds = 30
}

resource "aws_lambda_function" "create_thumbnail" {
  function_name = "CreateThumbnail"
  filename      = "${path.module}/../lambda/function.zip" 
  handler       = "lambda_function.lambda_handler"
  runtime       = "python3.8"
  role          = "arn:aws:iam::000000000000:role/lambda-role"
}
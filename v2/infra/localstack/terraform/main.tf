provider "aws" {
  region                      = "us-east-1"
  access_key                  = "test"
  secret_key                  = "test"
  skip_credentials_validation = true
  skip_requesting_account_id  = true
  skip_metadata_api_check     = true
}

####### BUCKET ##########
resource "aws_s3_bucket" "raybank_bucket" {
  bucket = "rayllanderson-raybank-bucket"
}


####### SQS QUEUES ##########
resource "aws_sqs_queue" "keycloak_event_listener_queue" {
  name                       = "keycloak-event-listener-queue"
  delay_seconds              = 0
  max_message_size           = 262144
  message_retention_seconds  = 345600
  visibility_timeout_seconds = 30
}

resource "aws_sqs_queue" "thumbnail_event_listener_queue" {
  name                       = "thumbnail-event-listener-queue"
  delay_seconds              = 0
  max_message_size           = 262144
  message_retention_seconds  = 345600
  visibility_timeout_seconds = 30
}

resource "aws_sqs_queue" "cognito_queue" {
  name                       = "cognito-event-listener-queue"
  delay_seconds              = 0
  max_message_size           = 262144
  message_retention_seconds  = 345600
  visibility_timeout_seconds = 30
}

resource "aws_sqs_queue" "s3_event_queue" {
  name                       = "create-thumb-sqs"
  delay_seconds              = 0
  max_message_size           = 262144
  message_retention_seconds  = 345600
  visibility_timeout_seconds = 30
  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.s3_event_dlq.arn
    maxReceiveCount     = 10
  })
}

resource "aws_sqs_queue" "s3_event_dlq" {
  name = "create-thumb-sqs-dlq"
}


####### LAMBDA FUNCTION ##########
resource "aws_lambda_function" "create_thumbnail_lambda" {
  function_name = "lambda_function"
  filename      = "${path.module}/../../lambda/my_deployment_package.zip"
  handler       = "lambda_function.lambda_handler"
  runtime       = "python3.11"
  role          = "arn:aws:iam::000000000000:role/lambda-role"
  environment {
    variables = {
      SQS_QUEUE_URL = aws_sqs_queue.s3_event_queue.id
    }
  }
}


####### BUCKET NOTIFICATION ##########
resource "aws_s3_bucket_notification" "thumbnail_to_sqs_notification" {
  bucket = aws_s3_bucket.raybank_bucket.id
  queue {
    queue_arn     = aws_sqs_queue.s3_event_queue.arn
    events        = ["s3:ObjectCreated:*"]
    filter_prefix = "uploads/"
  }
  queue {
    queue_arn     = aws_sqs_queue.thumbnail_event_listener_queue.arn
    events        = ["s3:ObjectCreated:*"]
    filter_prefix = "thumbnails/"
  }
  depends_on = [ aws_sqs_queue.s3_event_queue, aws_sqs_queue.thumbnail_event_listener_queue ]
}

####### LAMBDA LISTEN SQS ##########
resource "aws_lambda_event_source_mapping" "s3_event_processor_mapping" {
  event_source_arn = aws_sqs_queue.s3_event_queue.arn
  function_name    = aws_lambda_function.create_thumbnail_lambda.arn
}

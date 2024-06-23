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

resource "aws_sqs_queue" "thumbnail_event_listener_queue" {
  name                      = "thumbnail-event-listener-queue"
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

resource "aws_lambda_permission" "allow_s3_to_invoke_lambda" {
  statement_id  = "AllowExecutionFromS3"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.create_thumbnail.function_name
  principal     = "s3.amazonaws.com"
  source_arn    = aws_s3_bucket.raybank_bucket.arn
}

resource "aws_s3_bucket_notification" "upload_bucket_notification" {
  bucket = aws_s3_bucket.raybank_bucket.id

  lambda_function {
    lambda_function_arn = aws_lambda_function.create_thumbnail.arn
    events              = ["s3:ObjectCreated:*", "s3:ObjectRemoved:*"]
    filter_suffix       = "uploads/"
  }

  depends_on = [aws_lambda_permission.allow_s3_to_invoke_lambda]
}

resource "aws_s3_bucket_notification" "thumbnail_to_sqs_notification" {
  bucket = aws_s3_bucket.raybank_bucket.id

  queue {
    queue_arn = aws_sqs_queue.thumbnail_event_listener_queue.arn
    events    = ["s3:ObjectCreated:*", "s3:ObjectRemoved:*"]
    filter_prefix = "thumbnail/"
  }
}
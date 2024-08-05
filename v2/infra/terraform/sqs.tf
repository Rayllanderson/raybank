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

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

resource "aws_sqs_queue_policy" "sqs_policy" {
  queue_url = aws_sqs_queue.s3_event_queue.id
  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Principal = {
          Service = "s3.amazonaws.com"
        },
        Action = "sqs:SendMessage",
        Resource = aws_sqs_queue.s3_event_queue.arn,
        Condition = {
          ArnLike = {
            "aws:SourceArn": aws_s3_bucket.raybank_bucket.arn
          }
        }
      }
    ]
  })
}

resource "aws_sqs_queue_policy" "sqs_policy_thumbnail_event_listener_queue" {
  queue_url = aws_sqs_queue.thumbnail_event_listener_queue.id
  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Principal = {
          Service = "s3.amazonaws.com"
        },
        Action = "sqs:SendMessage",
        Resource = aws_sqs_queue.thumbnail_event_listener_queue.arn,
        Condition = {
          ArnLike = {
            "aws:SourceArn": aws_s3_bucket.raybank_bucket.arn
          }
        }
      }
    ]
  })
}
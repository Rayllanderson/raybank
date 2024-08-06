resource "aws_s3_bucket" "raybank_bucket" {
  bucket = "rayllanderson-raybank-bucket"
}

resource "aws_s3_bucket_ownership_controls" "bucket_ownership_controls" {
  bucket = aws_s3_bucket.raybank_bucket.id
  rule {
    object_ownership = "BucketOwnerPreferred"
  }
}

resource "aws_s3_bucket_acl" "acl" {
  bucket = aws_s3_bucket.raybank_bucket.id
  acl    = "private"

  depends_on = [aws_s3_bucket_ownership_controls.bucket_ownership_controls]
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
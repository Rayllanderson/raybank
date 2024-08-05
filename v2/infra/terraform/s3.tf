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

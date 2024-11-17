data "aws_caller_identity" "current" {}
data "aws_region" "current_region" {}


resource "aws_ecs_cluster" "raybank_cluster" {
  name = "raybank-cluster"
}
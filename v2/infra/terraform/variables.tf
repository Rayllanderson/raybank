variable "region" {
  description = "AWS region"
  default     = "us-east-1"
}

variable "subnet_ids" {
  description = "List of Subnet IDs"
  type        = list(string)
  default = [ "subnet-0e3da8689e2b1882d", "subnet-0aa1b978260c4f6ce", "subnet-097bfa952ed00c98f" ]
}

variable "raybank_image" {
  description = "Docker image for Raybank"
  default = "rayllanderson/raybank:latest"
}

variable "keycloak_image" {
  description = "Docker image for Keycloak"
  default = "rayllanderson/raybank-keycloak:v0"
}
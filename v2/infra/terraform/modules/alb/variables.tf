variable "region" {
  description = "AWS region"
  default     = "us-east-1"
}

variable "subnet_ids" {
  description = "List of Subnet IDs"
  type        = list(string)
  default     = []
}

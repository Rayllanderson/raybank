variable "subnet_ids" {
  description = "List of Subnet IDs"
  type        = list(string)
  default     = []
}

variable "database_security_groups_ids" {
  description = "List of Subnet IDs"
  type        = list(string)
  default     = []
}

variable "raybank_image" {
  description = "Docker image for Raybank"
  default     = "rayllanderson/raybank:latest"
}

variable "keycloak_image" {
  description = "Docker image for Keycloak"
  default     = "rayllanderson/raybank-keycloak:v0"
}

variable "raybank_tg_arn" {
  description = "ARN do Target Group para Raybank"
  type        = string
}

# variable "keycloak_tg_arn" {
#   description = "ARN do Target Group para Keycloak"
#   type        = string
# }

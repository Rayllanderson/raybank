# resource "aws_ecs_service" "keycloak_service" {
#   name            = "keycloak-service"
#   cluster         = aws_ecs_cluster.raybank_cluster.id
#   task_definition = aws_ecs_task_definition.keycloak_task.arn
#   desired_count   = 1

#   capacity_provider_strategy {
#     capacity_provider = "FARGATE_SPOT"
#     weight            = 1
#   }

#   network_configuration {
#     subnets          = var.subnet_ids
#     security_groups  = var.database_security_groups_ids
#     assign_public_ip = true
#   }

#   load_balancer {
#     target_group_arn = var.keycloak_tg_arn
#     container_name   = "keycloak-container"
#     container_port   = 8080
#   }
# }


# resource "aws_ecs_task_definition" "keycloak_task" {
#   family                   = "keycloak-task"
#   network_mode             = "awsvpc"
#   requires_compatibilities = ["FARGATE"]
#   cpu                      = "256" # 0.25 vCPU
#   memory                   = "512" # 512 MB
#   execution_role_arn       = aws_iam_role.ecs_task_execution_role.arn
#   task_role_arn            = aws_iam_role.ecs_task_role.arn

#   container_definitions = jsonencode([
#     {
#       name      = "keycloak-container"
#       image     = var.keycloak_image
#       cpu       = 256
#       memory    = 512
#       essential = true
#       portMappings = [
#         {
#           containerPort = 8080
#           protocol      = "tcp"
#         }
#       ]
#       environment = [
#         # {
#         #   name  = "KEYCLOAK_IMPORT"
#         #   value = "/opt/jboss/keycloak/imports/realm-export.json"
#         # },
#         # {
#         #   name  = "JAVA_OPTS"
#         #   value = "-Dkeycloak.profile.feature.upload_scripts=enabled"
#         # },
#         {
#           name  = "KC_SQS_QUEUE"
#           value = "keycloak-event-listener-queue"
#         },
#         {
#           name  = "JAVA_OPTS"
#           value = "-Xms256m -Xmx512m"
#         },
#         {
#           name  = "SQS_LOCAL"
#           value = "false"
#         },
#       ]
#       secrets = [
#         {
#           name      = "KEYCLOAK_ADMIN"
#           valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/KEYCLOAK_ADMIN"
#         },
#         {
#           name      = "KEYCLOAK_ADMIN_PASSWORD"
#           valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/KEYCLOAK_ADMIN_PASSWORD"
#         },
#         {
#           name      = "KC_DB"
#           valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/DB_USERNAME"
#         },
#         {
#           name      = "KC_DB_URL"
#           valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/KC_DB_URL"
#         },
#         {
#           name      = "KC_DB_USERNAME"
#           valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/DB_USERNAME"
#         },
#         {
#           name      = "KC_DB_PASSWORD"
#           valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/DB_PASSWORD"
#         }

#       ]
#       command : [
#         "start-dev",
#         # "-Dkeycloak.import=/opt/jboss/keycloak/imports/realm-export.json",
#         # "--import-realm"
#       ],
#       healthCheck = {
#         command     = ["CMD-SHELL", "curl -f http://localhost:8080 || exit 1"],
#         interval    = 60,
#         timeout     = 5,
#         retries     = 3,
#         startPeriod = 60
#       },
#       logConfiguration = {
#         logDriver = "awslogs"
#         options = {
#           awslogs-group         = "/ecs/keycloak-container"
#           awslogs-region        = "us-east-1"
#           awslogs-stream-prefix = "ecs"
#         }
#       }
#   }])
# }

# resource "aws_cloudwatch_log_group" "ecs_keycloak_log_group" {
#   name              = "/ecs/keycloak-container"
#   retention_in_days = 3
# }
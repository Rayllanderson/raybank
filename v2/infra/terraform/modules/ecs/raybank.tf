resource "aws_ecs_service" "raybank_service" {
  name                              = "raybank-service"
  cluster                           = aws_ecs_cluster.raybank_cluster.id
  task_definition                   = aws_ecs_task_definition.raybank_task.arn
  health_check_grace_period_seconds = 120
  desired_count                     = 1

  capacity_provider_strategy {
    capacity_provider = "FARGATE_SPOT"
    weight            = 1
  }

  network_configuration {
    subnets          = var.subnet_ids
    security_groups  = var.database_security_groups_ids
    assign_public_ip = true
  }

  load_balancer {
    target_group_arn = var.raybank_tg_arn
    container_name   = "raybank-container"
    container_port   = 8080
  }
}


resource "aws_ecs_task_definition" "raybank_task" {
  family                   = "raybank-task"
  network_mode             = "awsvpc"
  requires_compatibilities = ["FARGATE"]
  cpu                      = "512" # 0.5 vCPU
  memory                   = "1024"
  execution_role_arn       = aws_iam_role.ecs_task_execution_role.arn
  task_role_arn            = aws_iam_role.ecs_task_role.arn

  container_definitions = jsonencode([
    {
      name      = "raybank-container"
      image     = var.raybank_image
      cpu       = 512
      memory    = 1024
      essential = true
      portMappings = [
        {
          containerPort = 8080
          protocol      = "tcp"
        }
      ]
      secrets = [
        {
          name      = "POSTGRES_PASSWORD"
          valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/DB_PASSWORD"
        },
        {
          name      = "POSTGRES_HOST"
          valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/DB_HOST"
        },
        {
          name      = "POSTGRES_PORT"
          valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/DB_PORT"
        },
        {
          name      = "POSTGRES_USERNAME"
          valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/DB_USERNAME"
        },
        {
          name      = "RESOURCE_SERVER_ISSUER"
          valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/RESOURCE_SERVER_ISSUER"
        },
        {
          name      = "RESOURCE_SERVER_JWK_SET_URI"
          valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/RESOURCE_SERVER_JWK_SET_URI"
        },
        {
          name      = "COGNITO_ISSUER_URI"
          valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/RESOURCE_SERVER_ISSUER"
        },
        {
          name      = "COGNITO_CLIENT_ID"
          valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/COGNITO_CLIENT_ID"
        },
        {
          name      = "COGNITO_CLIENT_SECRET"
          valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/COGNITO_CLIENT_SECRET"
        }

      ]
      environment = [
        {
          name  = "TZ"
          value = "America/Sao_Paulo"
        },
        {
          name  = "SPRING_PROFILES_ACTIVE"
          value = "prod"
        },
        {
          name  = "POSTGRES_SCHEMA"
          value = "raybank"
        },
        {
          name  = "JAVA_OPTS"
          value = "-Xms512m -Xmx512m"
        },
        {
          name  = "OAUTH_PROVIDER_NAME"
          value = "cognito"
        },
        {
          name  = "S3_REGION"
          value = "${data.aws_region.current_region.name}"
        }
      ],
      health_check = {
        path                = "/actuator/health"
        interval            = 120
        timeout             = 30
        healthy_threshold   = 3
        unhealthy_threshold = 3
      }
      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = "/ecs/raybank-container"
          awslogs-region        = "us-east-1"
          awslogs-stream-prefix = "ecs"
        }
      }
  }])

}

resource "aws_cloudwatch_log_group" "ecs_raybank_log_group" {
  name              = "/ecs/raybank-container"
  retention_in_days = 3
}

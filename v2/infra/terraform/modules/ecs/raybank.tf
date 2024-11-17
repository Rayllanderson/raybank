resource "aws_ecs_service" "raybank_service" {
  name            = "raybank-service"
  cluster         = aws_ecs_cluster.raybank_cluster.id
  task_definition = aws_ecs_task_definition.raybank_task.arn
  desired_count   = 1

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
  cpu                      = "256" # 0.25 vCPU
  memory                   = "512" # 512 MB
  execution_role_arn       = aws_iam_role.ecs_task_execution_role.arn
  task_role_arn            = aws_iam_role.ecs_task_role.arn

  container_definitions = jsonencode([
    {
      name      = "raybank-container"
      image     = var.raybank_image
      cpu       = 256
      memory    = 512
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
          name      = "KEYCLOAK_SERVER_URL"
          valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/KEYCLOAK_SERVER-URL"
        },
        {
          name      = "KEYCLOAK_CLIENT_SECRET"
          valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/KEYCLOAK_CLIENT-SECRET"
        },
      ]
      environment = [
        {
          name  = "SPRING_PROFILES_ACTIVE"
          value = "prod"
        },

        {
          name  = "POSTGRES_SCHEMA"
          value = "raybank"
        },
        {
          name  = "KEYCLOAK_REALM"
          value = "raybank"
        },
        {
          name  = "KEYCLOAK_CLIENT_ID"
          value = "raybank-backend-create-user"
        },
        {
          name  = "JAVA_OPTS"
          value = "-Xms512m -Xmx512m"
        },
        {
          name  = "SQS_ENDPOINT"
          value = "https://sqs.us-east-1.amazonaws.com"
        },
        {
          name  = "SQS_REGION"
          value = "us-east-1"
        },
      ],
      healthCheck = {
        command     = ["CMD-SHELL", "curl -f http://localhost:8080/actuator/health || exit 1"],
        interval    = 30,
        timeout     = 5,
        retries     = 3,
        startPeriod = 300
      },
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

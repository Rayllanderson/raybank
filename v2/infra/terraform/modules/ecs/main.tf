data "aws_caller_identity" "current" {}
data "aws_region" "current_region" {}


resource "aws_ecs_cluster" "raybank_cluster" {
  name = "raybank-cluster"
}

resource "aws_ecs_task_definition" "raybank_task" {
  family                   = "raybank-task"
  network_mode             = "awsvpc"
  requires_compatibilities = ["EC2"]
  cpu                      = "256"
  memory                   = "512"
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
          hostPort      = 8080
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
      ]
  }])
}

resource "aws_ecs_service" "raybank_service" {
  name            = "raybank-service"
  cluster         = aws_ecs_cluster.raybank_cluster.id
  task_definition = aws_ecs_task_definition.raybank_task.arn
  desired_count   = 1
  launch_type     = "EC2"
  network_configuration {
    subnets          = var.subnet_ids
    assign_public_ip = false
  }

  load_balancer {
    target_group_arn = var.raybank_tg_arn
    container_name   = "raybank-container"
    container_port   = 8080
  }
}


resource "aws_ecs_task_definition" "keycloak_task" {
  family                   = "keycloak-task"
  network_mode             = "awsvpc"
  requires_compatibilities = ["EC2"]
  cpu                      = "256"
  memory                   = "512"
  execution_role_arn       = aws_iam_role.ecs_task_execution_role.arn
  task_role_arn            = aws_iam_role.ecs_task_role.arn

  container_definitions = jsonencode([
    {
      name      = "keycloak-container"
      image     = var.keycloak_image
      cpu       = 256
      memory    = 512
      essential = true
      portMappings = [
        {
          containerPort = 8080
          hostPort      = 8080
        }
      ]
      environment = [
        {
          name  = "KEYCLOAK_IMPORT"
          value = "/opt/jboss/keycloak/imports/realm-export.json"
        },
        {
          name  = "JAVA_OPTS"
          value = "-Dkeycloak.profile.feature.upload_scripts=enabled"
        },
        {
          name  = "KC_SQS_QUEUE"
          value = "keycloak-event-listener-queue"
        }
      ]
      secrets = [
        {
          name      = "KEYCLOAK_ADMIN"
          valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/KEYCLOAK_ADMIN"
        },
        {
          name      = "KEYCLOAK_ADMIN_PASSWORD"
          valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/KEYCLOAK_ADMIN_PASSWORD"
        },
        {
          name      = "KC_DB"
          valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/DB_USERNAME"
        },
        {
          name      = "KC_DB_URL"
          valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/KC_DB_URL"
        },
        {
          name      = "KC_DB_USERNAME"
          valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/DB_USERNAME"
        },
        {
          name      = "KC_DB_PASSWORD"
          valueFrom = "arn:aws:ssm:${data.aws_region.current_region.name}:${data.aws_caller_identity.current.account_id}:parameter/DB_PASSWORD"
        }
      ]
      command = [
        "-Dkeycloak.import=/opt/jboss/keycloak/imports/realm-export.json",
        "--import-realm"
      ]
  }])
}

resource "aws_ecs_service" "keycloak_service" {
  name            = "keycloak-service"
  cluster         = aws_ecs_cluster.raybank_cluster.id
  task_definition = aws_ecs_task_definition.keycloak_task.arn
  desired_count   = 1
  launch_type     = "EC2"

  network_configuration {
    subnets          = var.subnet_ids
    assign_public_ip = false
  }

  load_balancer {
    target_group_arn = var.keycloak_tg_arn
    container_name   = "keycloak-container"
    container_port   = 8080
  }
}


resource "aws_ecs_capacity_provider" "spring_boot_cp" {
  name = "spring-boot-ecs"

  auto_scaling_group_provider {
    auto_scaling_group_arn         = aws_autoscaling_group.spring_boot_asg.arn
    managed_termination_protection = "DISABLED"

    managed_scaling {
      maximum_scaling_step_size = 1
      minimum_scaling_step_size = 1
      status                    = "ENABLED"
      target_capacity           = 100
    }
  }
}

resource "aws_ecs_capacity_provider" "keycoak_cp" {
  name = "keycloak-ecs"

  auto_scaling_group_provider {
    auto_scaling_group_arn         = aws_autoscaling_group.keycloak_asg.arn
    managed_termination_protection = "DISABLED"

    managed_scaling {
      maximum_scaling_step_size = 1
      minimum_scaling_step_size = 1
      status                    = "ENABLED"
      target_capacity           = 100
    }
  }
}

resource "aws_ecs_cluster_capacity_providers" "cluster_cp" {
  cluster_name       = aws_ecs_cluster.raybank_cluster.name
  capacity_providers = [aws_ecs_capacity_provider.spring_boot_cp.name, aws_ecs_capacity_provider.keycoak_cp.name]

  default_capacity_provider_strategy {
    capacity_provider = aws_ecs_capacity_provider.spring_boot_cp.name
    base              = 1
    weight            = 100
  }
}



resource "aws_iam_role" "ecs_task_execution_role" {
  name = "ecsTaskExecutionRole_2"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action    = "sts:AssumeRole"
        Effect    = "Allow"
        Principal = {
          Service = "ecs-tasks.amazonaws.com"
        }
      }
    ]
  })
}

# Anexa a política de execução da tarefa ECS ao papel
resource "aws_iam_role_policy" "ecs_task_execution_role_policy" {
  name   = "ecs-task-execution-role-policy"
  role   = aws_iam_role.ecs_task_execution_role.id
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = [
          "ssm:GetParameter",
          "ssm:GetParameters",
          "ssm:GetParametersByPath",
          "kms:Decrypt"
        ]
        Effect   = "Allow"
        Resource = "*"
      }
    ]
  })
}

# Anexa a política gerenciada do ECS ao papel de execução
resource "aws_iam_role_policy_attachment" "ecs_task_execution_role_policy_attachment" {
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
  role      = aws_iam_role.ecs_task_execution_role.name
}

# Anexa a política gerenciada do ECS ao papel de execução
resource "aws_iam_role_policy_attachment" "ecs_task_execution_role_policy_attachment" {
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy"
  role      = aws_iam_role.ecs_task_execution_role.name
}



#task role
resource "aws_iam_role" "ecs_task_role" {
  name = "ecsTaskRole"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action    = "sts:AssumeRole"
        Effect    = "Allow"
        Principal = {
          Service = "ecs-tasks.amazonaws.com"
        }
      }
    ]
  })
}

# Adiciona uma política personalizada ao papel da tarefa ECS
resource "aws_iam_role_policy" "ecs_task_role_policy" {
  name   = "ecs-task-role-policy"
  role   = aws_iam_role.ecs_task_role.id
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = [
          "ssm:GetParameter",
          "ssm:GetParameters",
          "ssm:GetParametersByPath"
        ]
        Effect   = "Allow"
        Resource = "*"
      }
    ]
  })
}

# --- ECS Launch Template ---
data "aws_vpc" "main" {
  default = true
}


resource "aws_launch_template" "spring_boot_instance" {
  name_prefix            = "spring-boot-instance"
  image_id               = "ami-04ca2090011ea0a25"
  instance_type          = "t2.micro"
  vpc_security_group_ids = [aws_security_group.ecs_security_group.id]

  iam_instance_profile { arn = aws_iam_instance_profile.ecs_instance_profile.arn }
  monitoring { enabled = true }

  user_data = base64encode(<<-EOF
      #!/bin/bash
      echo ECS_CLUSTER=raybank-cluster >> /etc/ecs/ecs.config;
    EOF
  )
}

resource "aws_launch_template" "keycloak_instance" {
  name_prefix            = "keycloak-instance"
  image_id               = "ami-04ca2090011ea0a25"
  instance_type          = "t2.micro"
  vpc_security_group_ids = [aws_security_group.ecs_security_group.id]

  iam_instance_profile { arn = aws_iam_instance_profile.ecs_instance_profile.arn }
  monitoring { enabled = true }

  user_data = base64encode(<<-EOF
      #!/bin/bash
      echo ECS_CLUSTER=raybank-cluster >> /etc/ecs/ecs.config;
    EOF
  )
}

resource "aws_security_group" "ecs_security_group" {
  name        = "ecs-security-group"
  description = "Security group for ECS instances and tasks"
  vpc_id      = data.aws_vpc.main.id

  egress {
    from_port   = 0
    to_port     = 65535
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# Auto Scaling Group para instâncias EC2 do ECS (Spring Boot)
resource "aws_autoscaling_group" "spring_boot_asg" {
  desired_capacity = 1
  max_size         = 1
  min_size         = 1
  launch_template {
    id      = aws_launch_template.spring_boot_instance.id
    version = "$Latest"
  }
  vpc_zone_identifier = var.subnet_ids
  health_check_type   = "EC2"
  tag {
    key                 = "Name"
    value               = "spring-boot-instance"
    propagate_at_launch = true
  }
}

# Auto Scaling Group para instâncias EC2 do ECS (Keycloak)
resource "aws_autoscaling_group" "keycloak_asg" {
  desired_capacity = 1
  max_size         = 1
  min_size         = 1
  launch_template {
    id      = aws_launch_template.keycloak_instance.id
    version = "$Latest"
  }
  vpc_zone_identifier = var.subnet_ids
  health_check_type   = "EC2"

  tag {
    key                 = "Name"
    value               = "keycloak-instance"
    propagate_at_launch = true
  }
}

resource "aws_iam_role" "ecs_instance_role" {
  name = "ecsInstanceRole"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action = "sts:AssumeRole",
        Effect = "Allow",
        Principal = {
          Service = "ec2.amazonaws.com",
        },
      },
    ],
  })
}

resource "aws_iam_role_policy_attachment" "ecs_instance_role_policy" {
  role       = aws_iam_role.ecs_instance_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonEC2ContainerServiceforEC2Role"
}

resource "aws_iam_instance_profile" "ecs_instance_profile" {
  name = "ecsInstanceProfile"
  role = aws_iam_role.ecs_instance_role.name
}
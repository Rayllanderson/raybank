# Use a imagem base do LocalStack
FROM localstack/localstack:3.0.2

# Instale dependências necessárias usando apt-get (para imagens baseadas em Debian/Ubuntu)
RUN apt-get update && \
    apt-get install -y \
    curl \
    unzip && \
    rm -rf /var/lib/apt/lists/*

# Instale o Terraform
RUN curl -LO https://releases.hashicorp.com/terraform/1.1.1/terraform_1.1.1_linux_amd64.zip \
    && unzip terraform_1.1.1_linux_amd64.zip -d /usr/local/bin \
    && rm terraform_1.1.1_linux_amd64.zip

# Instale o terraform-local
RUN pip install terraform-local

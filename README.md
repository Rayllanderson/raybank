# 🏦 Raybank
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/Rayllanderson/raybank/blob/main/LICENSE) 

## Sobre o projeto
**[https://raybank.vercel.app](https://raybank.vercel.app)**  (O sistema está disponível para uso das **08:00 AM** às **20:00 PM**.)

**Raybank** é um sistema bancário digital desenvolvido para oferecer uma experiência completa de transações de sistemas financeiros reais. Raybank oferece uma ampla gama de funcionalidades, como transferências via Pix, cartões de débito e crédito, emissão e pagamento de boletos, depósitos e pagamentos diversos, além de uma interface de usuário intuitiva e responsiva.

**O que você pode fazer com o Raybank**
## <img src="https://user-images.githubusercontent.com/741969/99538099-3b7a5d00-298b-11eb-9f4f-c3d0cd4a5280.png" width="22" height="22"> **Sistema Completo de Pix**  
- Crie, gerencie e exclua suas chaves Pix com facilidade.  
- Faça transferências por QR Code, chave ou conta, sem complicações.  
- Configure limites para manter o controle total das transações.  
- Reembolse com apenas um clique, graças ao suporte completo para devoluções.

<div class="markdown-heading"></div>

## 💳 **Cartão de Débito e Crédito**  
- Parcelamentos com faturas reais, prazos, atrasos e tudo o que o mercado exige.  
- Pague suas faturas com facilidade gerando boletos ou com saldo da conta.
- Experiência de compras virtuais integrada via API.  
- Controle total de limites, com ajustes automáticos conforme uso e pagamento. 

## 💰 **Boletos Bancários**  
- Crie e pague boletos em qualquer momento, para qualquer usuário.
- Realize depósitos utilizando boletos com simplicidade.  
- Gerencie tudo por status e acompanhe os vencimentos como em bancos reais.  

## 💸 **Soluções Financeiras Completas**  
- Deposite como preferir: QR Code, boleto ou diretamente na conta.  
- Pague faturas, QR Code, boletos e mais com total controle
- Realize transferências usando QR Code, chave Pix ou número da conta. 
- Cada pagamento, transferência e movimentação são registrados em extratos detalhados.

## 🔐 Segurança em Primeiro Lugar  
- Proteção de dados garantida com **OAuth2 e Keycloak** para uma experiência confiável.  

## Telas
![img](https://github.com/user-attachments/assets/e47eff92-d017-479a-82ca-ee1312685368)
![img2](https://github.com/user-attachments/assets/7c2fff8f-97e0-4486-b90c-db633720c66a)

### 📱 Versão Mobile 
O design deste projeto foi pensado para oferecer uma ótima experiência em qualquer dispositivo, seja no computador ou no celular:

![Fotoram io (2)](https://github.com/user-attachments/assets/269a129b-b99e-4efe-98e3-c5c215cb686a)
![Fotoram io (3)](https://github.com/user-attachments/assets/b1659333-8797-453b-a885-7df91a5e6227)




## 📹 Demonstração
https://github.com/user-attachments/assets/ff71ab39-d1be-4bed-a81b-04d4bb5a5b11


## Por Trás do Raybank

Raybank combina um **backend sólido** em Java com Spring Boot e PostgreSQL, com um **frontend moderno** utilizando React e Next.js. A infraestrutura é totalmente automatizada com **Terraform** e **AWS**, proporcionando escalabilidade e robustez. 
A mensageria é feita com **SQS**, e o processamento de imagens e thumbnails é otimizado com **Python**.


## ⚙️ **Tecnologias Utilizadas**

### ☕ **Backend**
- **Java** com **Spring**.
- Banco de dados **PostgreSQL**.
- Mensageria com **AWS SQS** (opção para **Kafka**).

### 🌐 **Frontend**
-  **TypeScript**
- **React** com **Next.js 14**.
- **TailwindCSS** para estilização rápida e flexível.

### 🏗️ **Infraestrutura**
- **AWS** (S3, Lambda, SQS, e mais).
- **Terraform** para automação de infraestrutura.
- **Docker** e **Docker Compose** para containerização.
- **LocalStack** para emulação local de serviços AWS.

### 🔒 **Autenticação e Autorização**
- **OAuth2** com **Keycloak** para controle de acesso seguro.
- Webhook para eventos de criação de usuário utilizando **SQS** para gerenciar eventos.

### 🖼️ **Processamento de Imagens**
- **Python** para geração de thumbnails.
- Armazenamento em **AWS S3**.

## 📡 Arquitetura e Infraestrutura  
A arquitetura do RayBank é projetada para alta escalabilidade, com utilização de **AWS** e mensageria.  
Veja abaixo a arquitetura completa:
![infra drawio2](https://github.com/user-attachments/assets/2c4b2648-6bb2-49f3-ad4d-332b45688f2a)

---

## 🚀 Como executar o projeto
### Pré-requisitos

- **Docker**: Certifique-se de ter o Docker instalado em sua máquina.
- **Docker Compose**: Também é necessário ter o Docker Compose instalado para gerenciar múltiplos containers.

### Passos para executar o projeto

1. **Clone o repositório**
   
   No terminal, clone o repositório com o seguinte comando:

   ```bash
   git clone https://github.com/Rayllanderson/raybank.git
   ```
2. **Acesse a pasta da infraestrutura:**
Navegue até a pasta onde o arquivo [docker-compose.yml](https://github.com/Rayllanderson/raybank/blob/main/v2/infra/docker-compose.yml) está localizado:
   ```bash
   cd raybank/v2/infra
   ```
4. **Suba os containers Docker**
Execute o comando abaixo para subir os containers. Esse processo pode levar algum tempo, dependendo da configuração da sua máquina:
   ```bash
   docker-compose up
   ```
6. Acesse o Frontend
Após o Docker concluir a inicialização dos containers, o frontend estará disponível na seguinte URL:
[http://localhost:3000](http://localhost:3000)

**Observação:** O tempo de inicialização pode variar. Caso seja a primeira vez que os containers estão sendo executados, pode demorar alguns minutos, dependendo dos recursos da sua máquina.

---
## Telas da Versão 1.0 (Jun. 2021)
<img src="https://user-images.githubusercontent.com/63964369/121674468-7b6c7300-ca88-11eb-9a04-c9eac58a6da8.png" width="49%"> <img src="https://user-images.githubusercontent.com/63964369/121675067-4876af00-ca89-11eb-9e0c-02520814aaef.png" width="49%">
<img src="https://user-images.githubusercontent.com/63964369/121675068-4876af00-ca89-11eb-887e-061b7757656e.png" width="49%"> <img src="https://user-images.githubusercontent.com/63964369/121675069-490f4580-ca89-11eb-8a24-a7a162ced021.png" width="49%">
<img src="https://user-images.githubusercontent.com/63964369/121675065-47de1880-ca89-11eb-86ca-f2da137e5dd1.png" width="49%"> <img src="https://user-images.githubusercontent.com/63964369/121674465-7ad3dc80-ca88-11eb-9f0b-0abfa449b219.png" width="49%">

## ❓ Como contribuir para o projeto

1. Faça um **fork** do projeto.
2. Crie uma nova branch com as suas alterações: `git checkout -b my-feature`
3. Salve as alterações e crie uma mensagem de commit contando o que você fez: `git commit -m "feature: My new feature"`
4. Envie as suas alterações: `git push origin my-feature`
> Caso tenha alguma dúvida confira este [guia de como contribuir no GitHub](https://github.com/firstcontributions/first-contributions)


## 📝 Licença

Este projeto esta sobe a licença MIT.

Rayllanderson Gonçalves Rodrigues

https://www.linkedin.com/in/rayllanderson/

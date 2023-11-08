# 🏦 Raybank
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/Rayllanderson/raybank/blob/main/LICENSE) 

## Sobre o projeto

Raybank funciona como um banco! *de brincadeira, claro.* É possível  transferir para um amigo, depositar, pagar um boleto, 
e, ah, também lhe oferecemos um cartão de crédito com crédito inicial no valor de R$ 5.000,00 🤑🤑🤑, você pode realizar 
compras nesse cartão de crédito. Só não esqueça de pagar a fatura, tá ok? 

Raybank É seguro! Confia! Ninguém irá roubar seu saldo. Só vem :) Brincadeiras a parte, a aplicação realiza todas as 
transações de maneiras mockadas através da api. Então, não se preocupe com dinheiro de verdade aqui

Raybank é um aplicativo de simulação de banco desenvolvida para experimentar a construção de um aplicativo Mobile utilizando o Flutter. 


### Telas

<img src="https://user-images.githubusercontent.com/63964369/121674468-7b6c7300-ca88-11eb-9a04-c9eac58a6da8.png" width="49%"> <img src="https://user-images.githubusercontent.com/63964369/121675067-4876af00-ca89-11eb-9e0c-02520814aaef.png" width="49%">

<img src="https://user-images.githubusercontent.com/63964369/121675068-4876af00-ca89-11eb-887e-061b7757656e.png" width="49%"> <img src="https://user-images.githubusercontent.com/63964369/121675069-490f4580-ca89-11eb-8a24-a7a162ced021.png" width="49%">


Ah, toda transferência, depósito e pagamento, gera um extrato. Você pode conferi-los no saldo da conta ou no cartão de crédito

<img src="https://user-images.githubusercontent.com/63964369/121675065-47de1880-ca89-11eb-86ca-f2da137e5dd1.png" width="49%"> <img src="https://user-images.githubusercontent.com/63964369/121674465-7ad3dc80-ca88-11eb-9f0b-0abfa449b219.png" width="49%">





## 🛠 Tecnologias utilizadas
### :coffee: Back end
- [Java](https://www.oracle.com/br/java/)
- JPA / Hibernate
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Framework](https://spring.io/projects/spring-framework)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa) 
- [Spring Security](https://spring.io/projects/spring-security)
- [Maven](https://maven.apache.org/)

### 📱 Mobile
- [Flutter](https://flutter.dev/?gclsrc=ds&gclsrc=ds)
- [Dart](https://dart.dev/)


## :hammer: Implantação em produção
- Back end: Heroku
- Banco de dados: Postgresql

## 🚀 Como executar o projeto

### 🎲 Back end

Pré-requisitos: Java 11

```bash
# clonar repositório
git clone https://github.com/Rayllanderson/raybank

# entrar na pasta do projeto
cd api

# executar o projeto
./mvnw spring-boot:run
```

### 🧭 Mobile

Pré-requisitos: Dispositivo móvel android e ou flutter instalado em sua máquina

Instale o [apk](https://drive.google.com/file/d/1AYUgURkTkrlTIkiXiZOhcgc6zIXZegnh/view?usp=sharing) e pronto!

Caso queira buildar seu próprio apk, siga os passos: (flutter sdk é necessário)

```bash

#entrar na pasta do projeto
cd mobile

#buildar o apk
flutter build apk

#após o build, ele ficará na pasta mobile\build\app\outputs\apk\release
```

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

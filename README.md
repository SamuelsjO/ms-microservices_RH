# ms-microservices_RH

# Project architecture 

![arquitetura](https://user-images.githubusercontent.com/28466117/139351069-ef5eb8c4-6fe8-4316-9811-4008596fc14c.png)

	microservices Human resources, API REST com Spring Boot e Java 11
	
	Feign para requisições de API entre microsserviços
	
	Ribbon para balanceamento de carga
	
	Servidor Eureka para registro dos microsserviços
	
	API Gateway Zuul para roteamento e autorização
	
	Hystrix para tolerância a falhas
	
	OAuth e JWT para autenticação e autorização
	
	Servidor de configuração centralizada com dados em repositório Git
	
	Geração de containers Docker para os microsserviços e bases de dado, fazer melhorias com docker-compose
	
# Rotas da API

    porta:localhost:8765 referente api-gateway
    
    ----------------------------------------------------------------------------------------------------------
    usuario e senha fictício
    hr-oauth:
       POST:
    	/hr-oauth/oauth/token
	
![oauth2](https://user-images.githubusercontent.com/28466117/140620535-3bb1367d-a758-4f3f-a4c6-49ca70dee0be.png)
![oauth](https://user-images.githubusercontent.com/28466117/140620536-e6c03b12-3638-48b1-8392-c97e5d74b42b.png)

	 response:
![oauth3](https://user-images.githubusercontent.com/28466117/140620612-5a8ef8b0-c474-43fe-ab3f-30efbaa9b7f4.png)




----------------------------------------------------------------------------------------------------------------
# Configurando token para acesso a rotas

![token](https://user-images.githubusercontent.com/28466117/140621327-c4a4781e-958e-49ee-b042-531a42a5c4ac.png)

----------------------------------------------------------------------------------------------------------------


       GET:
       busca usuario por email
       /hr-oauth/users/search?email=leia@gmail.com
    
     
     hr-user
       GET:
       busca usuario por ID
       /hr-user/users/1
       
       GET:
       busca usuario por email
       /hr-user/users/search?email=leia@gmail.com
     
     hr-payroll
       GET:
       calculo de pagamento com base nos dias trabalhado
       /hr-payroll/payments/{workid}/days/{days}
     
     hr-worker
        GET:
        Busca trabalhador por {id}
        /hr-worker/workers/{id}
        
        GET:  
        Busca todos trabalhadores cadastrado na base
        /hr-worker/workers
        
    Actuator:
      POST:
      Atualiza as configuraçoes em runtime
      /hr-worker/actuator/refresh
      
    
    --------------------------------------------------------------------------------------------------------------  
    hr-config-server
    
    porta: localhost:8888, porta de configuraçao de servidor
       GET:
       hr-worker configs default profile
       /hr-worker/default
       
       hr-worker configs test profile
       /hr-worker/test
       
       hr-worker configs on console
       /hr-worker/workers/configs
   


    -----------------------------------------------------------------------------------------------------------
## CRIANDO CONTAINER DOCKER A PARTIR DE COMANDOS


# Criar rede docker para sistema hr

    docker network create hr-net

# perfil dev com Postgresql no Docker

    docker pull postgres:12-alpine

    docker run -p 5432:5432 --name hr-worker-pg12 --network hr-net -e POSTGRES_PASSWORD=1234567 -e POSTGRES_DB=db_hr_worker postgres:12-alpine

	docker run -p 5432:5432 --name hr-user-pg12 --network hr-net -e POSTGRES_PASSWORD=1234567 -e POSTGRES_DB=db_hr_user -d -p postgres:12-alpine

# hr-config-server

	FROM openjdk:11
	VOLUME /tmp
	EXPOSE 8888
	ADD ./target/hr-config-server-0.0.1-SNAPSHOT.jar hr-config-server.jar
	ENTRYPOINT ["java","-jar","/hr-config-server.jar"]


	mvnw clean package

	docker build -t hr-config-server:v1 .

	docker run -p 8888:8888 --name hr-config-server --network hr-net -e GITHUB_USER={user} -e GITHUB_PASS={password} hr-config-server:v1
	
	GITHUB_USER={user} e GITHUB_PASS={password}, se o github for privado deverá infomar o user e password
	
# hr-eureka-server

    FROM openjdk:11
    VOLUME /tmp
    EXPOSE 8761
    ADD ./target/hr-eureka-server-0.0.1-SNAPSHOT.jar hr-eureka-server.jar
    ENTRYPOINT ["java","-jar","/hr-eureka-server.jar"]
	

	mvnw clean package

    docker build -t hr-eureka-server:v1 .

    docker run -p 8761:8761 --name hr-eureka-server --network hr-net hr-eureka-server:v1
	
# hr-worker
 
	FROM openjdk:11
	VOLUME /tmp
	ADD ./target/hr-worker-0.0.1-SNAPSHOT.jar hr-worker.jar
	ENTRYPOINT ["java","-jar","/hr-worker.jar"]
	

	mvnw clean package -DskipTests
	
	docker build -t hr-worker:v1 .
	
	docker run -P --network hr-net hr-worker:v1
	
# hr-user

	FROM openjdk:11
	VOLUME /tmp
	ADD ./target/hr-user-0.0.1-SNAPSHOT.jar hr-user.jar
	ENTRYPOINT ["java","-jar","/hr-user.jar"]

	mvnw clean package -DskipTests
	
	docker build -t hr-user:v1 .
	
	docker run -P --network hr-net hr-user:v1

# hr-payroll

	FROM openjdk:11
	VOLUME /tmp
	ADD ./target/hr-payroll-0.0.1-SNAPSHOT.jar hr-payroll.jar
	ENTRYPOINT ["java","-jar","/hr-payroll.jar"]
	
	mvnw clean package -DskipTests
	
	docker build -t hr-payroll:v1 .
	
	docker run -P --network hr-net hr-payroll:v1

# hr-oauth

	FROM openjdk:11
	VOLUME /tmp
	ADD ./target/hr-oauth-0.0.1-SNAPSHOT.jar hr-oauth.jar
	ENTRYPOINT ["java","-jar","/hr-oauth.jar"]
	
	mvnw clean package -DskipTests
	
	docker build -t hr-oauth:v1 .
	
	docker run -P --network hr-net hr-oauth:v1,
	
# hr-api-gateway-zuul

	FROM openjdk:11
	VOLUME /tmp
	EXPOSE 8765
	ADD ./target/hr-api-gateway-zuul-0.0.1-SNAPSHOT.jar hr-api-gateway-zuul.jar
	ENTRYPOINT ["java","-jar","/hr-api-gateway-zuul.jar"]
	
	
	mvnw clean package -DskipTests
	
	docker build -t hr-api-gateway-zuul:v1 .
	
	docker run -p 8765:8765 --name hr-api-gateway-zuul --network hr-net hr-api-gateway-zuul:v1


# Melhorias com docker-compose
  
	proximo passo ...

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

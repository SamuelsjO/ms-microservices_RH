# ms-microservices_RH
	microservices Human resources

	Project architecture

![arquitetura](https://user-images.githubusercontent.com/28466117/139351069-ef5eb8c4-6fe8-4316-9811-4008596fc14c.png)


# Criar rede docker para sistema hr

    docker network create hr-net

# perfil dev com Postgresql no Docker

    docker pull postgres:12-alpine

    docker run -p 5432:5432 --name hr-worker-pg12 --network hr-net -e POSTGRES_PASSWORD=1234567 -e POSTGRES_DB=db_hr_worker postgres:12-alpine

	docker run -p 5432:5432 --name hr-user-pg12 --network hr-net -e POSTGRES_PASSWORD=1234567 -e POSTGRES_DB=db_hr_user -d -p postgres:12-alpine

#hr-config-server

	FROM openjdk:11
	VOLUME /tmp
	EXPOSE 8888
	ADD ./target/hr-config-server-0.0.1-SNAPSHOT.jar hr-config-server.jar
	ENTRYPOINT ["java","-jar","/hr-config-server.jar"]


	mvnw clean package

	docker build -t hr-config-server:v1 .

	docker run -p 8888:8888 --name hr-config-server --network hr-net -e GITHUB_USER=acenelio -e GITHUB_PASS= hr-config-server:v1
	
#hr-eureka-server
 
	FROM openjdk:11
	VOLUME /tmp
	ADD ./target/hr-worker-0.0.1-SNAPSHOT.jar hr-worker.jar
	ENTRYPOINT ["java","-jar","/hr-worker.jar"]
	

	mvnw clean package -DskipTests
	
	docker build -t hr-worker:v1 .
	
	docker run -P --network hr-net hr-worker:v1
	
#hr-user

	FROM openjdk:11
	VOLUME /tmp
	ADD ./target/hr-user-0.0.1-SNAPSHOT.jar hr-user.jar
	ENTRYPOINT ["java","-jar","/hr-user.jar"]

	mvnw clean package -DskipTests
	
	docker build -t hr-user:v1 .
	
	docker run -P --network hr-net hr-user:v1

#hr-payroll

	FROM openjdk:11
	VOLUME /tmp
	ADD ./target/hr-payroll-0.0.1-SNAPSHOT.jar hr-payroll.jar
	ENTRYPOINT ["java","-jar","/hr-payroll.jar"]
	
	mvnw clean package -DskipTests
	
	docker build -t hr-payroll:v1 .
	
	docker run -P --network hr-net hr-payroll:v1

#hr-oauth

	FROM openjdk:11
	VOLUME /tmp
	ADD ./target/hr-oauth-0.0.1-SNAPSHOT.jar hr-oauth.jar
	ENTRYPOINT ["java","-jar","/hr-oauth.jar"]
	
	mvnw clean package -DskipTests
	
	docker build -t hr-oauth:v1 .
	
	docker run -P --network hr-net hr-oauth:v1,
	
#hr-api-gateway-zuul

	FROM openjdk:11
	VOLUME /tmp
	EXPOSE 8765
	ADD ./target/hr-api-gateway-zuul-0.0.1-SNAPSHOT.jar hr-api-gateway-zuul.jar
	ENTRYPOINT ["java","-jar","/hr-api-gateway-zuul.jar"]
	
	
	mvnw clean package -DskipTests
	
	docker build -t hr-api-gateway-zuul:v1 .
	
	docker run -p 8765:8765 --name hr-api-gateway-zuul --network hr-net hr-api-gateway-zuul:v1

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

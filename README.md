# Spring Boot 3 Microservices with Kubernetes and Angular
ref: https://www.youtube.com/watch?v=yn_stY3HCr8

## Will build a simple online shopping application
- Product Service: create and view products, like a product catalog
- Order Service: can order products
- Inventory Service: can check if product is in stock or not
- Notification Service: can send notifictaions after order is placed
- Order Service, Inventory Service and Notification Service will interact with each other
- Synchronous and Asynchronous communication

## WILL COVER:
- Service delivery
- Centralized configuration
- Distributed tracing
- Event driven architecture
- Centralized logging
- Circuit breaker
- Secure microservice using Keycloak

### Spring Initializr:
https://start.spring.io/

### Docker > Get Docker:
https://docs.docker.com/get-started/get-docker/

!Important

To run Windows containers, you need Windows 10 or Windows 11 Professional or Enterprise edition. Windows Home or Education editions only allow you to run Linux containers.

Install Docker Desktop on Linux for a Windows Home ediition
https://docs.docker.com/desktop/install/linux/

Follow this instruction
https://docs.docker.com/engine/install/ubuntu/#install-using-the-repository

### Open in IntelliJ
and run Maven Goal "mvn clean verify"
"mvn clean" ran successfully
"mvn verify" failed due to error: https://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException

add Docker plugin in IntelliJ 

connect Docker with WSL: Ubuntu (in Ubuntu Terminal run "docker info" and find Docker Root Dir)
    > Virtual Machine path: /var/lib/docker/
    > Local path: \\wsl.localhost\Ubuntu\Docker

a bit useful ref: https://www.freecodecamp.org/news/where-are-docker-images-stored-docker-container-paths-explained/#:~:text=The%20storage%20location%20of%20Docker%20images%20and%20containers&text=Ubuntu%3A%20%2Fvar%2Flib%2F,Windows%3A%20C%3A%5CProgramData%5CDockerDesktop

not very useful ref: https://www.jetbrains.com/help/idea/settings-docker.html#virtual-machine-path

### docker-compose.yml
ref: https://docs.divio.com/reference/docker-docker-compose/
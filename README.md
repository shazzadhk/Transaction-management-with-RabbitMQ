# Transaction Management with Spring Boot and RabbitMQ

## Description

This is a Spring Boot Microservice project where I have implemented Transactional Management using
Spring Boot and RabbitMQ.<br> For Application Monitoring Prometheus and Grafana is used in this
project.

## Tech Stack

---
1. Java 17
2. Spring Boot 3
3. Postgres
4. RabbitMQ
5. Prometheus
6. Grafana
7. Docker

### RabbitMQ
RabbitMQ is a message-queueing software also known as a message broker or queue
manager. Simply said; it is software where queues are defined, to which applications connect in
order to transfer a message or messages.RabbitMQ enables asynchronous processing, meaning that it allows you to put a message in a
queue without processing it immediately. RabbitMQ is therefore ideal for long-running tasks or
blocking tasks, allowing web servers to respond quickly to requests instead of being forced to
perform computationally intensive tasks on the spot. RabbitMQ simply stores messages and
passes them to consumers when ready. See documentation [here](https://www.rabbitmq.com/)
### Prometheus
Prometheus is an open source time-series database originally developed by SoundCloud.
It comes with its proper query language PromQL and used for event monitoring and alerting. Prometheus is not only a time series database;
t’s an entire ecosystem of tools that can be attached to expand functionality. Prometheus monitors a wide variety of systems like
servers, databases, individual virtual machines, IoT, machine learning models, and many more.
Prometheus is now becoming an indispensable component in high-load microservice systems. See documentation [here](https://prometheus.io/)
### Grafana
Grafana is open source visualization and analytics software.
It allows you to query, visualize, alert on, and explore your metrics no matter where they are stored.
In plain English, it provides you with tools to turn your time-series database (prometheus for example) data into beautiful graphs and visualizations.
See documentation [here](https://grafana.com/)

## Download Project
```gitexclude
git clone https://github.com/shazzadhk/Transaction-management-with-RabbitMQ.git
```
## Pre-Requisites

1. Docker Engine
2. JDK version 17

## Dependencies
For RabbitMQ
```maven
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-amqp</artifactId>
    </dependency>
```

For Metrics
```maven
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
```

For Prometheus
```maven
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-registry-prometheus</artifactId>
        <scope>runtime</scope>
    </dependency>
```

## How To Run

1. Go to every microservice location and run the following command.
    ```gitexclude
    docker compose up
    ```
2. It will automatically download docker images and run docker container
3. Get Tools in the following port
   * RabbitMQ in 15672
   * Postgres in 5432
   * Prometheus in 9090
   * Grafana in 3000
4. Get all metrics in http://your-ip/actuator
5. Get all Prometheus metrics in http://your-ip/actuator/prometheus
6. Replace all IP Address of all jobs in prometheus.yml file with your own IP address
7. Change email credentials with your own email credentials in config.yml file


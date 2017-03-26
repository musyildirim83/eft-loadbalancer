Load Balancer - Dropwizard service using DDSL
======================================

Make sure zookeeper is running on localhost.

Build and package the example app:

    mvn package

start the service:

    java -jar target/loadbalancer.jar server conf/local/config.yml
    
urls:
    
    GET     /loadbalancer/broadcast
    GET     /loadbalancer/service1 
    GET     /loadbalancer/service2 
    GET     /loadbalancer/service3 
    GET     /loadbalancer/serviceGr1 
    GET     /loadbalancer/serviceName/{serviceName}


FROM java:alpine
ADD target/loadbalancer.jar /data/loadbalancer.jar
ADD conf /data/conf
CMD java -jar /data/loadbalancer.jar server /data/conf/local/config.yml
ENV SERVICE_TAGS=rest
ENV SERVICE_NAME=loadbalancer
EXPOSE 8080

FROM tomcat:10

RUN mkdir -p /usr/local/tomcat/webapps/Rest
COPY target/RestTemplate-1.0-SNAPSHOT /usr/local/tomcat/webapps/Rest

CMD ["catalina.sh", "run"]


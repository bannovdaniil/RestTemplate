FROM tomcat:10
COPY target/RestTemplate-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/Rest.war
CMD ["catalina.sh", "run"]


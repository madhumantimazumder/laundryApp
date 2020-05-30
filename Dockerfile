#FROM tomcat

#RUN rm -rf $CATALINA_HOME/webapps/ROOT
#COPY target/laundryapp.war $CATALINA_HOME/webapps/ROOT.war

#EXPOSE 8080 3306
FROM siddharthmishra01/tomcat-mysql:1.0.0
MAINTAINER Madhumanti,Utsav,Ujan,Shashank
WORKDIR /opt/apache-tomcat-8.5.55
COPY target/laundryapp.war /opt/apache-tomcat-8.5.55/webapps/
EXPOSE 8080 3306

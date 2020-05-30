FROM tomcat

MAINTAINER Madhumanti,Utsav,Ujan,Shashank
RUN rm -rf $CATALINA_HOME/webapps/ROOT
COPY target/laundryapp.war $CATALINA_HOME/webapps/ROOT.war

#EXPOSE 8080 3306
#FROM siddharthmishra01/tomcat-mysql:1.0.0
#WORKDIR /opt/apache-tomcat-8.5.55
#COPY target/laundryapp.war /opt/apache-tomcat-8.5.55/webapps/
#EXPOSE 8080 3306

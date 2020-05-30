FROM tomcat
MAINTAINER Madhumanti,Utsav,Ujan,Shashank

RUN rm -rf $CATALINA_HOME/webapps/ROOT
COPY target/laundryapp.war $CATALINA_HOME/webapps/ROOT.war

EXPOSE 8080 3306

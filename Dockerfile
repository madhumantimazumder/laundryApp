FROM tomcat
MAINTAINER Madhumanti,Utsav,Ujan,Shashank

RUN rm -rf $CATALINA_HOME/webapps/ROOT
COPY target/calculator.war $CATALINA_HOME/webapps/ROOT.war

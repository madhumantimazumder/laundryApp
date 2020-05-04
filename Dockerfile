FROM tomcat
MAINTAINER Madhumanti,Utsav,Ujan,Shashank

RUN rm -rf $CATALINA_HOME/webapps/ROOT
COPY target/laundry\ app.war $CATALINA_HOME/webapps/ROOT.war

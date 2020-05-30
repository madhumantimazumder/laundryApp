FROM tomcat
MAINTAINER Madhumanti,Utsav,Ujan,Shashank

RUN rm -rf $CATALINA_HOME/webapps/ROOT
COPY target/laundryapp.war $CATALINA_HOME/webapps/ROOT.war


RUN apt-get update && \
  wget https://dev.mysql.com/get/mysql-apt-config_0.8.4-1_all.deb && \
  dpkg -i mysql-apt-config_0.8.4-1_all.deb && rm -f mysql-apt-config_0.8.4-1_all.deb
  
# Add volumes for MySQL 
VOLUME  ["/etc/mysql", "/var/lib/mysql"]

EXPOSE 8080 3306

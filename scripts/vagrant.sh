echo "PS1='\[\e[0;31m\]\u\[\e[m\] \[\e[1;34m\]\w\[\e[m\] \$ '" >> /home/vagrant/.bashrc
echo "cd /home/vagrant/darshini" >>  /home/vagrant/.bashrc
source /home/vagrant/.bashrc
cd /home/vagrant/darshini
printf 'Y\n' | add-apt-repository -y ppa:webupd8team/java
apt-get update
echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 select true" | sudo debconf-set-selections
apt-get install -y oracle-java8-installer
printf 'Y\n' | apt-get install -y oracle-java8-set-default
printf 'Y\n' | apt-get install -y maven
printf 'Y\n' | apt-get install git
printf 'Y\n' | apt-get install npm

#adjust tomcat7 settings
cp -rf /home/vagrant/darshini/conf/settings.xml /usr/share/maven/conf/settings.xml
printf 'Y\n' | apt-get install -y tomcat7 tomcat7-admin tomcat7-common
echo "export CATALINA_BASE=/var/lib/tomcat7" >> /usr/share/tomcat7/bin/setenv.sh
echo "export  JAVA_HOME=/usr/lib/jvm/java-8-oracle" >> /usr/share/tomcat7/bin/setenv.sh
chmod +x /usr/share/tomcat7/bin/setenv.sh
cp -rf /home/vagrant/darshini/conf/tomcat-users.xml /var/lib/tomcat7/conf/tomcat-users.xml
sudo sed -i '/JAVA_OPTS="-Djava.awt.*/c\JAVA_OPTS="-Djava.awt.headless=true -Xmx1024m -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:InitiatingHeapOccupancyPercent=0 -Dcom.sun.management.jmxremote.port=8086 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"' /etc/default/tomcat7
echo "JAVA_HOME=/usr/lib/jvm/java-8-oracle" >> /etc/default/tomcat7

#copy the correct property files and create the required directories
cp -f conf/vagrant/*.properties src/main/resources/META-INF/*.properties
mkdir -p /home/vagrant/darshini-es/data
chmod 777 /home/vagrant/darshini-es/data
mkdir -p /home/vagrant/darshini-es/logs
chmod 777 /home/vagrant/darshini-es/logs
mkdir -p /home/vagrant/darshini-logs/darshini
chmod 777 /home/vagrant/darshini-logs/darshini


#get the npm modules for js files of webpages
mkdir -p src/main/webapp/WEB-INF/node_modules
npm install --prefix ./src/main/webapp/WEB-INF


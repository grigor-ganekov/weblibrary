# weblibrary
Web library containing some generated books

You will need Apache Tomcat 8 server, MySQL RDBMS, JDBC connector.

1.First add the JDBC connector to the project libraries. Add your own or the one located in "weblibrary/weblibrary-ver1/WebContent/WEB-INF/lib/mysql-connector-java-5.1.34-bin.jar"

2.Then deploy the database located in weblibrary/weblibrary-ver1/Dump20150316.sql to your MySQL RDBMS

3.Modify the web.xml file located in weblibrary/weblibrary-ver1/WebContent/WEB-INF/web.xml 
 
<param-value>DBURL</param-value> here add your database url
<param-value>USERNAME</param-value> MySQL user username that have all priveleges for the database
<param-value>PASSWORD</param-value> password 

4.Connect your Tomcat 8 server to the web project


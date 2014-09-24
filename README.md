What is **CAS Integration Demo**
================================

TODO

Running locally
===============

This project uses the Maven Cargo plugin to run the CMS and site locally in Tomcat.
From the project root folder, execute:

  `$ mvn clean install`
  
  `$ mvn -P cargo.run`


Basic Test Cases
================
  - Visit http://localhost:8080/cms/
  - You will be redirected to a login page in http://localhost:8080/cas/...
  - Login by **editor/Mellon** or **author/Mellon**
  - You will be redirected to Hippo CMS UI without any more login.

Access the CMS at http://localhost:8080/cms, and the site at http://localhost:8080/site
Logs are located in target/tomcat7x/logs

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
  1. Open a browser and visit *http://localhost:8080/cms/*
  1. You will be redirected to a login page in *http://localhost:8080/cas/...*
  1. Login by **editor/Mellon** or **author/Mellon**
  1. You will be redirected to Hippo CMS UI without any more login.

What is **Hippo CAS Integration Demo**
================================

**Hippo CAS Integration Demo** project is implemented to demonstrate how to integrate Hippo CMS 7 with a Single Sign-on solution such as [Central Authentication Service (CAS)] [1].

This project uses Jasig CAS Service Web Application and Jasig CAS Client Library of [Central Authentication Service project] [2] simply because it is most convenient to use those modules as Maven dependencies and very easy to demonstrate how to integrate Hippo CMS with Single Sign-on solutions. Most of this integration method can be applied to other integration scenarios with other Single Sign-on products or solutions than CAS.



Prerequisites
=============
- Hippo CMS 7.9.2 or higher

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



[1]: http://en.wikipedia.org/wiki/Central_Authentication_Service    "Central Authentication Service (CAS)"
[2]: https://www.apereo.org/cas    "Jasig Central Authentication Service project"
[3]: https://github.com/Jasig/cas/wiki/Installation-Guide    "Jasig/cas Installation Guide"

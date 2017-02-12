FROM ubuntu:14.04

# Install Java.
RUN \
  apt-get update && \
  apt-get install -y openjdk-7-jdk && \ 
  rm -rf /var/lib/apt/lists/*
RUN \
  apt-get update && \
  apt-get install -y build-essential
RUN mkdir server

COPY rmi /server/rmi
COPY PingPongServer.java /server/PingPongServer.java 
COPY PingServerInterface.java /server/PingServerInterface.java 
COPY PingServerFactory.java /server/PingServerFactory.java 
COPY PingServer.java /server/PingServer.java
# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-7-openjdk-amd64

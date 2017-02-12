FROM ubuntu:14.04

# Install Java.
RUN \
  apt-get update && \
  apt-get install -y openjdk-7-jdk && \ 
  rm -rf /var/lib/apt/lists/*
RUN \
  apt-get update && \
  apt-get install -y build-essential
RUN mkdir client

COPY rmi /client/rmi
COPY PingPongClient.java /client/PingPongClient.java 
COPY PingServerInterface.java /client/PingServerInterface.java 
COPY PingServerFactory.java /client/PingServerFactory.java 
# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-7-openjdk-amd64

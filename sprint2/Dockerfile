## ----------------------------------------------------------
## Dockerfile for analisiproblema
## ----------------------------------------------------------

FROM openjdk:12.0.2
EXPOSE 8078
## ADD extracts the tar
ADD ./build/distributions/unibo.analisiproblema-1.0.tar /
WORKDIR /unibo.analisiproblema-1.0/bin
COPY ./*.pl ./
COPY ./resources/*.json ./
COPY ./mapRoomEmpty.* ./
## COPY ./*.py ./

## RUN apt-get update -y
## RUN apt-get install -y wiringpi
## RUN sudo apt-get install -y python

CMD ["bash", "unibo.analisiproblema"]


##  docker build -t basicrobot22:1.0 .
##  docker run -it --rm --name basicrobot22:1.0 -p8020:8020/tcp --privileged basicrobot22:1.0  /bin/bash

From ubuntu:latest
# Install OpenJDK-8
RUN apt-get update && \
    apt-get install -y openjdk-19-jdk && \
    apt-get install -y ant && \
    apt-get clean;

# Fix certificate issues
RUN apt-get update && \
    apt-get install ca-certificates-java && \
    apt-get clean && \
    update-ca-certificates -f;

# Setup JAVA_HOME -- useful for docker commandline
ENV JAVA_HOME /usr/lib/jvm/java-19-openjdk-amd64/
RUN export JAVA_HOME

COPY ./src/ /home/chat

COPY ./exec_up.sh .
RUN chmod a+x exec_up.sh

RUN apt install -y net-tools
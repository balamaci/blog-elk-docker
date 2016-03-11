#FROM        python:2-wheezy
FROM gliderlabs/alpine:3.3


RUN apk --update add python py-pip openssl ca-certificates
RUN apk --update add --virtual build-dependencies python-dev build-base wget unzip

ENV ELASTALERT_URL https://github.com/Yelp/elastalert/archive/master.zip
ENV ELASTALERT_HOME /opt/elastalert

RUN cd /tmp && wget https://github.com/Yelp/elastalert/archive/master.zip && \
    unzip *.zip && \
    rm *.zip && \
#    mkdir /elastalert && \
    mv elastalert-master /elastalert

WORKDIR /elastalert

RUN ls && python setup.py install && pip install -r requirements.txt
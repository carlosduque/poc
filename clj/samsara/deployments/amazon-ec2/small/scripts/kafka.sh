#!/bin/bash -e

echo '------------------------------------------------------------------'
echo '                    Samsara Kafka'
echo '------------------------------------------------------------------'
docker pull samsara/kafka

docker run -d --restart=on-failure:10 \
       -p 9092:9092 \
       -p 15000:15000 \
       -v /logs/kafka:/logs \
       -v /data/kafka:/data \
       -e "KAFKA_BROKER_ID=1" \
       -e "ADV_IP=$(curl 'http://169.254.169.254/latest/meta-data/local-ipv4')" \
       -e "ZOOKEEPER_PORT_2181_TCP_ADDR=$1" \
       samsara/kafka

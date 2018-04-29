#!/bin/bash -e
#
# $1 - OPTIONAL
#      The name of the docker image to use.
#      ex:
#        mytest/myimage:1.0
#        some.private.docker.repo:5000/mytest/myimage:1.0
#


if [ "$(id -u)" != "0" ] ; then
    echo "Running the script with root's privileges"
    sudo "$0" "$*"
    exit $?
fi

echo "waiting for system to fully come online."
sleep 30

IMAGE=${1:-samsara/ingestion-api}
echo '------------------------------------------------------------------'
echo '                    Using image:' $IMAGE
echo '------------------------------------------------------------------'
mkdir -p /etc/samsara/images
echo "$IMAGE" > /etc/samsara/images/ingestion-api


echo '------------------------------------------------------------------'
echo '                    Setup upstart service'
echo '------------------------------------------------------------------'
cat >/etc/init/ingestion.conf <<\EOF
description "Ingestion-API container"
author "Bruno"
start on runlevel [2345]
stop on runlevel [016]
respawn
pre-start exec /usr/bin/docker rm ingestion | true
exec /usr/bin/docker run --name ingestion \
       --dns $(curl "http://169.254.169.254/latest/meta-data/local-ipv4") \
       -p 9000:9000 \
       -p 15000:15000 \
       -v /logs/ingestion:/logs \
       -e KAFKA_1_PORT_9092_TCP_ADDR=kafka.service.consul \
       -e RIEMANN_PORT_5555_TCP_ADDR=riemann.service.consul \
       -e "TRACKING_ENABLED=true" \
       `cat /etc/samsara/images/ingestion-api`

pre-stop script
        /usr/bin/docker stop ingestion
        /usr/bin/docker rm ingestion
end script
EOF

echo '------------------------------------------------------------------'
echo '                Pull the latest image'
echo '------------------------------------------------------------------'
docker pull `cat /etc/samsara/images/ingestion-api`



echo '------------------------------------------------------------------'
echo '                add service to consul'
echo '------------------------------------------------------------------'
cat > /etc/consul.d/ingestion.json <<\EOF
{
  "service": {
    "name": "ingestion",
    "tags": [],
    "port": 9000
  },
  "check": {
    "id": "ingestion-http-status",
    "name": "Ingestion api status",
    "script": "curl -si -m 1 http://$(curl -s http://169.254.169.254/latest/meta-data/local-ipv4):9000/v1/api-status",
    "interval": "5s"
  }
}
EOF

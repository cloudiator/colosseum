#!/bin/bash

# get interface of default route
INTERFACE=`ip route | awk '/default/ {print $5}'`
# get mtu of interface
MTU=`ifconfig ${INTERFACE} | awk -F: '/MTU/{print $2+0}'`

# default docker file exists
if [ -f /etc/default/docker ]; then
    echo DOCKER_OPTS="--mtu=${MTU}" | tee --append /etc/default/docker > /dev/null
fi

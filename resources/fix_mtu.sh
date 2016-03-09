#!/bin/bash

# get interface of default route
INTERFACE=`ip route | awk '/default/ {print $5}'`
# get mtu of interface
MTU=`ifconfig ${INTERFACE} | awk -F: '/MTU/{print $2+0}'`

# default docker file exists
if [ -f /etc/default/docker ]; then
    echo DOCKER_OPTS="--mtu=${MTU}" | tee --append /etc/default/docker > /dev/null

    # we need to set environment file, as later ubuntu systemd are skipping the default file...
    if [ -f /lib/systemd/system/docker.service ]; then
        perl -i -pe "BEGIN{undef $/;} s/^\[Service\]$/[Service]\nEnvironmentFile=-\/etc\/default\/docker/sgm" /lib/systemd/system/docker.service
        systemctl daemon-reload
    fi
fi

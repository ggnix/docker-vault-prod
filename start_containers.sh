#!/bin/bash
sudo /usr/local/bin/docker-compose up -d
sudo docker exec -it docker_vault_container_1 /bin/sh /vault/unlock.sh

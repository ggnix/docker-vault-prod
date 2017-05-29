#!/bin/bash
docker-compose up -d
docker exec -it docker_vault_container_1 /bin/sh /vault/unlock.sh

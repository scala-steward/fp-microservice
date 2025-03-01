#!/bin/zsh

### load generic infra vars
source "../../vars.sh"

export DB_DEPLOY_NAME=$APP_NAME-db

## Generated resolved docker-compose file to use
DOCKER_COMPOSE_FILE="$(envresolve "docker-compose.yml")"

### Deploy APP Docker image
(cd "$PROJECT_DIR" || exit; chmod 777 deployImage.sh; ./deployImage.sh) &&

### Stop and run
docker compose -f "$DOCKER_COMPOSE_FILE" stop
open http://localhost:"$APP_PORT"/docs &&
docker compose -f "$DOCKER_COMPOSE_FILE" up &&

# Init db
(docker exec -it "$DB_DEPLOY_NAME" /bin/bash -c "createdb -U postgres $DB_NAME" || true)

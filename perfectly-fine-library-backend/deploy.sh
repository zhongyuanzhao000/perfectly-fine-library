cd $(dirname $0) &&
git pull --recurse-submodules &&
git submodule update --init --recursive &&
mvn package -DskipTests &&
docker-compose -f "docker-compose.yml" down &&
docker container prune -f
docker image prune -f
docker-compose -f "docker-compose.yml" up -d --build
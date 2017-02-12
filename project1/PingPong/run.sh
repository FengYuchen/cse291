rm -rf server/rmi client/rmi
cp -r ../rmi server/rmi
cp -r ../rmi client/rmi
docker rm -f $(docker ps -a -q)



cd server
echo "-------------------------------------"
echo "Initializing server....."
echo "-------------------------------------"
#Build and run catserver container
docker build -t server .
docker run -itd --name server server  /bin/bash -c " cd server; javac *.java; java PingPongServer"
address=$(docker inspect --format '{{ .NetworkSettings.IPAddress}}' server)

cd ../client
docker build -t client .
docker run -itd --name client client  /bin/bash -c " cd client; javac *.java; java PingPongClient $address 1234 1"

echo "-------------------------------------"
docker logs -f client
docker logs -f server
echo "-------------------------------------"

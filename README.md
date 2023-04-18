# SocketChat
# SocketChat
# Crear una network
docker network create --subnet=172.20.0.0/16 customnetwork
# montar servidor
docker run -it --net customnetwork --ip 172.20.0.10 --name test 
# lanzar servidor 
./exec_up.sh
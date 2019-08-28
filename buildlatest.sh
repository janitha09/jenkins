git fetch upstream
git checkout master
git merge upstream/master
docker run -it -v /home/ubuntu/Documents/jenkins:/app -w /app -v /home/ubuntu/.m2:/root/.m2 maven:3-jdk-8 mvn clean package -pl war -am -DskipTests -Dfindbugs.skip

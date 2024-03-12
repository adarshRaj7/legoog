#!/bin/bash

mvn spring-boot:run > start.log &

counter=1
while [ $counter -le 30 ]
do
	response=$(curl --write-out '%{http_code}' --head --silent --output /dev/null http://127.0.0.1:8081/v2/search/that_allows)
	if [[ "$response" -ne 200 ]] ; then
		echo "Got code $response. Retrying in 1 sec (30s total)..."
	else
		echo "Got status code 200! Yay, webserver is up!"
		exit 0
	fi
	sleep 1
	((counter++))
done

echo "Website failed to come up and run within 30 sec. Log:"
cat start.log
exit 1

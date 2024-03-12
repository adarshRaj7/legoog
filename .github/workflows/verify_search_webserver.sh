#!/bin/bash

response=$(curl --silent /dev/null http://127.0.0.1:8081/v2/search/that_allows)
echo "Searched 'that allows', and it returned \"$response\""

search_result=$(echo $response|grep 4|grep true)
if [ -z $search_result ]; then
  echo "Search result failed validation"
  exit 1
else
  echo "Search result validated. Yee-haw!"
fi
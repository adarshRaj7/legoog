#!/bin/bash

source ./.github/workflows/cfg_location.sh

while IFS= read -r line
do
  if [[ "$line" == *"assignment: "* ]]; then
    url=`echo $line | sed 's/assignment: \(.*\)/\1/'`
  fi
done < "$cfg"

filename=$(basename -- "$url")
wget $url 

while IFS= read -r line
do
  if [[ "$line" == *"- Due: "* ]]; then
    duedate=`echo $line | sed 's/- Due: \(.*\)/\1/'`
  fi
done < "$filename"

if [ -z "$duedate" ]
then
  echo "Failed to extra due date. Please contact instructor."
  exit 1
fi

todate=`TZ=PST8PDT date`

echo "today: $todate"
echo "due date: $duedate"

todateepoch=$(date -d "$todate" +%s)
duedateepoch=$(date -d "$duedate" +%s)
if [ "$todateepoch" -gt "$duedateepoch" ] ;
then
  echo "Submission past due!"
  exit 1
else
  echo "Deadline checked out."
fi

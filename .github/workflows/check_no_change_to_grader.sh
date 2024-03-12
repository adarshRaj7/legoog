#!/bin/bash

git fetch origin main:main

changes=`git diff --name-only main`

if [[ "$changes" == *".github"* ]]; then
  echo "Potentially attempting to change grader script. Not permitted!"
  exit 1
fi
echo "No change to grader script."

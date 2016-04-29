#!/bin/bash

set -e -u

if [ "$TRAVIS_REPO_SLUG" == "cloudiator/colosseum" ] && \
   [ "$TRAVIS_PULL_REQUEST" == "false" ]; then

  echo "Writing travis build number..."
  echo "Travis build number is $TRAVIS_BUILD_NUMBER"

  echo $TRAVIS_BUILD_NUMBER > conf/.travis

  echo "Writing travis build number finished..."

fi

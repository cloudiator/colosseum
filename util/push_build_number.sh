#!/bin/bash

set -e -u

if [ "$TRAVIS_REPO_SLUG" == "cloudiator/colosseum" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then

  echo -e "Publishing build number...\n"

  cp .travis $HOME/.travis

  cd $HOME
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "travis-ci"

  git clone --quiet --branch=master https://${GH_TOKEN}@github.com/cloudiator/colosseum clone  > /dev/null

  cd clone

  rm -f .travis
  cp $HOME/.travis .
  git add .
  git commit -m "Latest travis build number on successful build $TRAVIS_BUILD_NUMBER auto-pushed to repository."
  git push -q origin master > /dev/null

  echo -e "Published build number.\n"
fi

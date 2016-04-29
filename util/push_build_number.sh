#!/bin/bash

set -e -u

if [ "$TRAVIS_REPO_SLUG" == "cloudiator/colosseum" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then

  echo -e "Publishing build number...\n"

  # get the author of the latest commit
  AUTHOR=$(git show -s --format=format:%ae)

  if [ "$AUTHOR" == "travis@travis-ci.org" ]; then
    echo -e "Stop publishing as last author was travis itself to avoid infinite cycle\n"
    exit
  fi

  # copy .travis file to temp location
  cp .travis $HOME/.travis

  # setup a new clone
  cd $HOME
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "travis-ci"

  # do a new clone with correct credentials
  git clone --quiet --branch=master https://${GH_TOKEN}@github.com/cloudiator/colosseum clone  > /dev/null

  # in the new clone, copy the temp .travis file from above
  cd clone
  rm -f .travis
  cp $HOME/.travis .

  # commit the .travis file change
  git add .
  git commit -m "[ci skip] Latest travis build number on successful build $TRAVIS_BUILD_NUMBER auto-pushed to repository."
  git push -q origin master > /dev/null

  echo -e "Published build number.\n"
fi

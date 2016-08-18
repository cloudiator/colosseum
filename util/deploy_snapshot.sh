#!/bin/bash

set -e -u

if [ "$TRAVIS_REPO_SLUG" == "cloudiator/colosseum" ] && \
   [ "$TRAVIS_JDK_VERSION" == "oraclejdk8" ] && \
   [ "$TRAVIS_PULL_REQUEST" == "false" ] && \
   [ "$TRAVIS_BRANCH" == "master" ]; then

  echo "Publishing snapshot..."

  sbt 'set credentials += Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", System.getenv("CI_DEPLOY_USERNAME"), System.getenv("CI_DEPLOY_PASSWORD"))' clean publish

  echo "Snapshot published."

fi

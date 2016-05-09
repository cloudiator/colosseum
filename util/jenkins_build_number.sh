#!/bin/bash

set -e -u

  echo "Writing jenkins build number..."
  echo "Jenkins build number is $BUILD_NUMBER"

  echo $BUILD_NUMBER > conf/.jenkins

  echo "Writing jenkins build number finished..."

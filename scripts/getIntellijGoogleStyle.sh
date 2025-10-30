#!/bin/bash

PROJECT_ROOT=$(sh ./getRootDir.sh)
echo "my root $PROJECT_ROOT"

curl -o $PROJECT_ROOT/codestyle/intellij-google-style.xml https://raw.githubusercontent.com/google/styleguide/refs/heads/gh-pages/intellij-java-google-style.xml
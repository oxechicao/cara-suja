#!/bin/bash

PROJECT_ROOT=$(pwd)

MARKER="build.gradle" # Or "package.json", "pom.xml", etc.
CURRENT_DIR=$(pwd)
while [[ "$CURRENT_DIR" != "/" && ! -e "$CURRENT_DIR/$MARKER" ]]; do
	CURRENT_DIR=$(dirname "$CURRENT_DIR")
done

if [[ -e "$CURRENT_DIR/$MARKER" ]]; then
	PROJECT_ROOT="$CURRENT_DIR"
	echo $PROJECT_ROOT
else
	exit 1
fi
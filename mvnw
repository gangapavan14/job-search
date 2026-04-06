#!/bin/sh

set -eu

WRAPPER_DIR="$(CDPATH= cd -- "$(dirname -- "$0")/.mvn/wrapper" && pwd)"

DISTRIBUTION_URL=""
DISTRIBUTION_PATH=""

while IFS='=' read -r key value; do
  case "$key" in
    distributionUrl) DISTRIBUTION_URL="$value" ;;
    distributionPath) DISTRIBUTION_PATH="$value" ;;
  esac
done < "$WRAPPER_DIR/maven-wrapper.properties"

if [ -z "$DISTRIBUTION_URL" ] || [ -z "$DISTRIBUTION_PATH" ]; then
  echo "distributionUrl or distributionPath is missing from .mvn/wrapper/maven-wrapper.properties"
  exit 1
fi

MAVEN_HOME="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)/$DISTRIBUTION_PATH"

if [ ! -x "$MAVEN_HOME/bin/mvn" ]; then
  echo "Downloading Maven distribution..."
  ZIP_PATH="$WRAPPER_DIR/apache-maven.zip"
  EXTRACT_PATH="$WRAPPER_DIR/extract"

  rm -f "$ZIP_PATH"
  rm -rf "$EXTRACT_PATH"

  mkdir -p "$EXTRACT_PATH"
  curl -fsSL "$DISTRIBUTION_URL" -o "$ZIP_PATH"
  unzip -q "$ZIP_PATH" -d "$EXTRACT_PATH"

  ROOT_DIR="$(find "$EXTRACT_PATH" -mindepth 1 -maxdepth 1 -type d | head -n 1)"
  if [ -z "$ROOT_DIR" ]; then
    echo "Unable to extract Maven distribution."
    exit 1
  fi

  rm -rf "$MAVEN_HOME"
  mkdir -p "$(dirname "$MAVEN_HOME")"
  mv "$ROOT_DIR" "$MAVEN_HOME"

  rm -f "$ZIP_PATH"
  rm -rf "$EXTRACT_PATH"
fi

exec "$MAVEN_HOME/bin/mvn" "$@"

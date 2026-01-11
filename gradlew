#!/bin/sh
set -e
DIRNAME=$(cd "$(dirname "$0")" && pwd)
CLASSPATH="$DIRNAME/gradle/wrapper/gradle-wrapper.jar"

# Download wrapper if not present
if [ ! -f "$CLASSPATH" ]; then
    mkdir -p "$DIRNAME/gradle/wrapper"
    curl -sL -o "$CLASSPATH" "https://services.gradle.org/distributions/gradle-8.7-bin.zip" 2>/dev/null || \
    wget -q -O "$CLASSPATH" "https://services.gradle.org/distributions/gradle-8.7-bin.zip" 2>/dev/null || true
fi

exec java -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"

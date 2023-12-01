#!/bin/bash

fail() {
    echo >&2 "---- FAILED ----"
    exit 1
}

if [ $# -ne 1 ]
then
    echo >&2 "Usage: $0 dayNumber"
    exit 1
fi

INPUT_DIR="inputs"
mkdir "$INPUT_DIR" 2>/dev/null

FILE="Day$1"
TEMPLATE="Template.kt"

INPUT_F="inputs/$FILE.input"
TEST_F="inputs/$FILE.test"


sed -e "s,TEST,$TEST_F," -e "s,INPUT,$INPUT_F," "$TEMPLATE" > "$FILE.kt"

STATUS=$?
if [ $STATUS -ne 0 ]
then
    fail
fi

touch "$TEST_F.txt" "$INPUT_F.txt"
STATUS=$?
if [ $STATUS -ne 0 ]
then
    fail
fi

echo "---- READY ----"
exit 0
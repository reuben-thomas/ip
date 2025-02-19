#!/usr/bin/env bash

# create bin directory if it doesn't exist
if [ ! -d "../bin" ]; then
  mkdir ../bin
fi

# delete output from previous run
if [ -e "./ACTUAL.TXT" ]; then
  rm ACTUAL.TXT
fi

# delete saved file from previous run
if [ -e "./KIPP.txt" ]; then
  rm KIPP.txt
fi

# compile the code into the bin folder, terminates if error occurred
if ! javac -cp ../src/main/java -Xlint:none -d ../bin ../src/main/java/kippchatcli/KippChatCli.java; then
  echo "********** BUILD FAILURE **********"
  exit 1
fi

# set test username environment variable
export KIPP_CHAT_TEST_USERNAME="cooper-testuser"
java -classpath ../bin kippchatcli.KippChatCli <input.txt >ACTUAL.TXT
# unset KIPP_CHAT_TEST_USERNAME

# convert to UNIX format
cp EXPECTED.TXT EXPECTED-UNIX.TXT
dos2unix ACTUAL.TXT EXPECTED-UNIX.TXT

# compare the output to the expected output
diff ACTUAL.TXT EXPECTED-UNIX.TXT
if [ $? -eq 0 ]; then
  echo "Test result: PASSED"
  exit 0
else
  echo "Test result: FAILED"
  exit 1
fi

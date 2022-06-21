#!/bin/bash
VERBOSE=$1
TIMELIMIT=0.5s
mkfifo iopipe0 iopipe1

if [[ $VERBOSE ]]
then
    timeout $TIMELIMIT <iopipe0 java -classpath solutions Popular | tee iopipe1 & <iopipe1 python3 tasks/popular/run.py | tee iopipe0;
else
    timeout $TIMELIMIT <iopipe0 java -classpath solutions Popular | python3 tasks/popular/run.py > iopipe0;
fi
PASS=$?

rm iopipe0 iopipe1
exit $PASS

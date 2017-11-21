#!/bin/bash

echo "Changing directories to build"
cd build

echo "Executing WordCount..."
hadoop jar WordCount.jar WordCount /user/`whoami`/input /user/`whoami`/output 

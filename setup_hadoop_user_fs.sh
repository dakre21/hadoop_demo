#!/bin/bash

hadoop fs -ls /user/`whoami`
hadoop fs -mkdir /user/`whoami`/input 
hadoop fs -put input.txt /user/`whoami`/input/ 

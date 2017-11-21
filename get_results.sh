#!/bin/bash
hadoop fs -getmerge /user/`whoami`/output/ output/final.txt
hadoop fs -get /user/`whoami`/output


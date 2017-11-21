#!/bin/bash
echo "Setting up hadoop environment"
export HADOOP_CLASSPATH=$(hadoop classpath)
echo $HADOOP_CLASSPATH  

echo "Compiling java code"
javac -classpath ${HADOOP_CLASSPATH} WordCount.java -d build/ 

echo "Building jar file"
cd build/ 
jar cf WordCount.jar *.class 

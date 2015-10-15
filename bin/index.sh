#!/bin/bash
javac -d "../bin" ../src/*.java
java IndexFiles $1 $2

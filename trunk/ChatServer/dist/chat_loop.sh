#!/bin/bash

err=1
until [ $err == 0 ];
do
	/home/java/bin/java -Xmx512m -cp ./libs/*:ae-chat-1.0.1.jar com.aionengine.chatserver.ChatServer > log/stdout.log 2>&1
	err=$?
	sleep 10
done
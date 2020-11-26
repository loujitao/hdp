#!/bin/bash

if (($#==0))
then
    exit 1;
fi
for i in hdp001 hdp002 hdp003
do
    echo Starting zk in $i
    ssh $i "source /etc/profile && /home/hadoop/hdp2/zookeeper3.4/bin/zkServer.sh $1" > /dev/null
done

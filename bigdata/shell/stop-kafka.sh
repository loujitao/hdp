for i in `cat /home/hadoop/hdp2/clusterbin/slaves`
do
echo "========== $i ==========" 
ssh $i 'source /home/hadoop/hdp2/kafka_2.11-0.11.0.2/bin/kafka-server-stop.sh stop'
echo $?
done

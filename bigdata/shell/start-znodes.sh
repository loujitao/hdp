for i in `cat /home/hadoop/hdp2/clusterbin/slaves`
do
echo "========== $i ==========" 
ssh $i 'source /etc/profile;/home/hadoop/hdp2/zookeeper3.4/bin/zkServer.sh start'
done

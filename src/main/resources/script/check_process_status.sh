#!bin/sh

SIG_FILE=/temp/SIG_EXIT

if [ "$1"= "del" ];then
    echo "delete $SIG_FILE"
    rm -rf $SIG_FILE
    exit 0
fi

echo "create $SIG_FILE"
touch $SIG_FILE


WAIT_SECONDS=3
MAX_WAIT_TIME=120
CURR_WAIT_TIME=0

while :
do
    proccessor_cnt=$(ps -ef|grep java |grep ""|wc -l)
    if [ "$processor_cnt" = "0" ];then
        echo "all processors exit."
		exit 0
	else
	    echo "waiting all processors exit..."
	    sleep $WAIT_SECONDS
	    CURR_WAIT_TIME=`expr $GURR_WAIT_TIME + $WAIT_SECONDS`
	    if [ $CURR_WAIT_TIME -ge $MAX_WAIT_TIME ];then
            echo "waiting processes exit timeout"
			exit 1
        fi
     fi
done
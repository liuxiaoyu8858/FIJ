#! /bin/sh

ROOT=/apps/srv/instance/project/webapp
LIB=$ROOT/WEB-INF/lib
CONF=$ROOT/WEB-INF/classes

SIG_FILE=/tmp/SIG_EXIT
if [ -f "$SIG_FILE" ];then
	echo "$SIG_FILE exist"
	exit 1
fi

libs=""
for lib in $(ls $LIB)
do
    libs=$libs":$LIB/$lib"
done

export CLASSPATH=${CONF}${libs}

pid=$(ps -ef|grep "MainProcessor cronImpl"|grep -v "grep"|awk '{print $2}')
if [ "$pid" != "" ];then
    echo "MainProcessor cronImpl $pid exist"
    exit 1
fi

java -Djava.util.Arrays.useLegacyMergeSort=true -server -Xms128m -Xmx128m -XX:NewRatio=3 packge CronTaskImpl

pid=$(ps -ef|grep "MainProcessor cronImpl"|awk '{print $2}')
echo "MainProcessor cronImpl start - pid:$pid"

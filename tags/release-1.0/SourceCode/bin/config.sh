source encoding
LIB_DIR=../lib/
for i in "$LIB_DIR"/*.jar
do
	CLASSPATH="$i:$CLASSPATH"
done
CONF_DIR=../conf/
HOME=../
TMP=../tmp
CLASSPATH=$CLASSPATH:$CONF_DIR:$TMP
WRITE_MAIN="com.tydic.config.Write"
READ_MAIN="com.tydic.config.Read"
LIST_MAIN="com.tydic.config.Ls"
CAT_MAIN="com.tydic.config.Cat"
DELETE_MAIN="com.tydic.config.Delete"
LOAD_MAIN="com.tydic.config.Load"
#echo CLASSPATH=$CLASSPATH
#echo param1=$1
case $1 in
ls)
java -classpath $CLASSPATH $LIST_MAIN $2 $3 $4 $5 $6 $7 $8
;;

cat)
java -classpath $CLASSPATH $CAT_MAIN $2 $3 $4 $5 $6 $7 $8
;;

write)
for i in $* 
do
  if [ "$eff" == "eff" ]
  then
    tmp=`echo $i | awk '/[a-zA-Z0-9]+@[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+\:(\/[a-zA-Z0-9]+)+/'`
    if [ "$tmp" != "" ]
    then
      var=`echo $i | awk 'BEGIN {FS="/"} {print $NF}'`
      var2=`echo $i | awk '/\.xml$|\.properties/'`
      if [ "$var2" != "" ]
      then
      des="../tmp/$var"
      scp $i $des
      else
      des="../tmp"
      scp -r $i $des
      fi
      param="$param $var"
    else
    param="$param $i"
    fi
  fi
  eff=eff
done
java -classpath $CLASSPATH $WRITE_MAIN $param
rm -r ../tmp/*
;;

read)
if [ "$ENCODING" = "" ]
then
ENCODING="utf8"
fi
java -classpath $CLASSPATH -Dfile.encoding="$ENCODING" $READ_MAIN $2 $3 $4 $5 $6 $7 $8
#java -classpath $CLASSPATH $READ_MAIN $2 $3 $4 $5 $6 $7 $8
;;
delete)
java -classpath $CLASSPATH $DELETE_MAIN $2 $3 $4 $5 $6 $7 $8
;;

load)
java -classpath $CLASSPATH $LOAD_MAIN $2 $3 $4 $5 $6 $7 $8
;;

*)
echo "Usage: $0 {ls [-p path]|cat|write|read|delete|load}"
esac

#script may take 1 argument to represent the log file that will be parsed, otherwise assuming a default file
#prints to output the percentage of resonses that took too long, were 500 level representing server issues, and the number of responses that had neither problem
#
#
format_NN_N () {
local S_NN_X=$((100*$1/$2));
local S_XX_N=$((1000*$1/$2 - S_NN_X*10));
printf "%d.%d" $S_NN_X $S_XX_N;
}

if [ $# -eq 0 ]
then
 LF=logs/rollingFile.log
else
 LF=$1
fi
R=$(grep -Poc "Response: " $LF)
R5=$(grep -Poc "(?<=Response: \[)(5[0-9]+)" $LF)
RTTL=$(grep -Poc "(Response: \[.*\][^0-9]*)\K([0-9]*[2-9][0-9][0-9]\.[0-9]*)" $LF)
R5TTL=$(grep -Poc "(Response: \[5.*\][^0-9]*)\K([0-9]*[2-9][0-9][0-9]\.[0-9]*)" $LF)
RS=$(($R - ($RTTL+$R5-$R5TTL)))
if [ $R -gt 0 ]
then
 printf "TOOK TOO LONG: %s%%\nSERVER FAILURES: %s%%\nSUCCESS RATE: %s%%\n" $(format_NN_N $RTTL $R) $(format_NN_N $R5 $R) $(format_NN_N $RS $R)
fi
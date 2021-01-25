#!/bin/bash
# electron.js からの呼び出し
# mjpg-streamer を終了させる
# SIGINT(2): ctrl+c 
ps aux | grep mjpg | grep -v grep | awk '{print "kill -2", $2}' | bash
echo 'mjpg-streamer process has killed'
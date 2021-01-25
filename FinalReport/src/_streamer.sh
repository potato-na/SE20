#!/bin/bash
# run-mirror.sh からの呼び出し
# mjpg-streamer を起動する
cd ~/Libraries/mjpg-streamer/mjpg-streamer-experimental/
exec ./mjpg_streamer -i "./input_uvc.so -f 30 -r 960x540 -d /dev/video0 -y -n -rot 180" -o "./output_http.so -w ./www -p 8080"
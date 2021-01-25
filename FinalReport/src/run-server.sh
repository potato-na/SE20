#!/bin/bash

# mediapipe ライブラリ読み込み
source ~/.bashrc
pyenv shell 3.8.3

if [ $1 == "dev" ]; then
	python3 py/hand_recognizer.py --device 1
else
	python3 py/hand_recognizer.py --device 1 --use_stream_camera
fi




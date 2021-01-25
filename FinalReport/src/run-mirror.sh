#!/bin/bash
#node_modules/.bin/electron js/electron.js $1;

# Mac での実行時に必要?
source ~/.bashrc

# サブシェルでmjpg_streamer を実行
#source ./_streamer.sh
bash ./_streamer.sh &
electron . $1 &

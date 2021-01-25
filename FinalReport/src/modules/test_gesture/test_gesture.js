var current_index = 1;
var images = ['music0.jpg', 'music1.jpg', 'music2.jpg'];
var audio = new Audio();
var slider = document.getElementById('slider');

// スライダー
// inputイベント時の関数
const rangeOnChange = (e) => {
    audio.volume =  e.target.value;
}

// ハンドジェスチャー 
function receiveGesture(gesture) {
    switch (gesture) {
        case 'right':   
            if (current_index < images.length - 1) {
                current_index += 1;
                // 次のイメージの表示
                showImage(current_index);
            }

            break;
        case 'left':
            if (current_index > 0 ) {
                current_index -= 1;
                // 次のイメージの表示
                showImage(current_index);
            }
            break
        default:
            break
    }
}

function showImage(index) {
    document.getElementById('image').src = `resources/music${index}.jpg`;
    audio.src = `music/music${index}.mp3`;
    audio.volume = slider.value;
    audio.play();
}

window.onload = function() {
    var btn_left = document.querySelector('#btn_left');
    var btn_right = document.querySelector('#btn_right');
    btn_left.addEventListener('click', function() {
        receiveGesture('left')
    });
    btn_right.addEventListener('click', function() {
        receiveGesture('right')
    });
    slider.addEventListener('input', rangeOnChange);
    // 初期化
    showImage(current_index);
}
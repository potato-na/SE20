// index.html 内で読み込み
// ###### slider ######
// index.html のiframe 内に id: sliderがあればそのvalueに差分を渡す 
// ###### gesture ######
// index.html の iframe 内に id: btn_left/right があればそれらのボタンを押したことにする

// iframe 内の要素取得用
const iframe_element = document.getElementById('iframe_module');
// 差分を受け取って反映
function changeSlider(dif) {
    const cwindow = iframe_element.contentWindow
    if (cwindow == null) {
        return;
    }
    const inputSlider = cwindow.document.querySelector('#slider');
    if (inputSlider == null) {
        return;
    }
    var currentValue = cwindow.getSliderValue();
    const newValue = parseFloat(currentValue) + dif;
    if (newValue >= 0 && newValue <= 1) {
        cwindow.setSliderValue(newValue);
    } 
}   

function inputGesture(gesture) {
    const cwindow = iframe_element.contentWindow
    if (cwindow == null) {
        return;
    }
    const inputLeft = cwindow.document.querySelector('#btn_left');
    const inputRight = cwindow.document.querySelector('#btn_right');
    
    if (inputLeft == null) {
        return;
    }
    
    switch (gesture) {
        case 'right':
            console.log('gesture right');
            inputRight.click();
            break
        case 'left':
            console.log('gesture left');
            inputLeft.click();
            break
        default:
            console.log(`unnexpected gesture: ${gesture}`);
    }
}

function debugLoop_gesture() {
    var diffs = [0.1, 0.1, 0.2, 0.1, 0,, 0.1, -0.1, -0.3, -0.4, 0, 0.5, 1];
    var cnt = 0;
    setInterval(function() {
        changeSlider(diffs[cnt]);
        cnt += 1;
        if (cnt >= diffs.length) {
            cnt = 0;
        }
    }, 2000);
}

window.onload = function() {
    // debugLoop_gesture();
}

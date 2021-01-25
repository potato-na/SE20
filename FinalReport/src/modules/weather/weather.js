var result = null;
var data = null;
var cityId = 270000;
var url = 'https://weather.tsukumijima.net/api/forecast/city/'



var xhr = new XMLHttpRequest();
console.log('hoge');
xhr.open('GET', `${url}${cityId}`, true);
xhr.onload = function() {
    data = JSON.parse(this.response);
    showWeather(data);
}
xhr.send();

function showWeather(data) {
    // 天気情報の一部を抽出
    var location = data.title.split(' ')[0]; 
    var date = data.forecasts[0].date;
    var description = data.description.text.split('\n')[0];
    var telop = data.forecasts[0].telop;
    var weather = splitTelop(telop);
    var iconFile = getIconName(weather);
    console.log(location, date, weather, iconFile, description);

    // htmlの書き換え
    // document.getElementsByID('description');
    document.getElementById('icon').innerHTML = `<img id="icon_place" src=icon/${iconFile}>`;
    document.getElementById('location').innerHTML = location;
    document.getElementById('date').innerHTML = date;
    document.getElementById('telop').innerHTML = telop;
    document.getElementById('desc').innerHTML = description;
}

// 先頭の天気概況用語のみを抽出
function splitTelop(telop) {
    // 天気変化用語
    var term = ['一時', '時々', 'のち', '後一時', '後時々'];
    term.forEach(function(t) {
        console.log(telop, t);
        if (telop.match(t)) {
           telop = telop.split(t)[0];
           return telop;
        }
    });
    return telop;
}

// アイコン名の取得
function getIconName(weather) {
    // 現在時刻
    var now = new Date();
    var hour = now.getHours();
    // 天気概況用語 + ファイル名
    const sunny = ['hare.png', '快晴', '晴'];
    const cloudy = ['kumori.png', '曇', '薄曇', '霧'];
    const rainy = ['ame.png', '霧雨', '雨', '大雨', '暴風雨'];
    const snowy = ['yuki.png', 'みぞれ', '地ふぶき', 'ふぶき', 'ひょう', 'あられ'];
    const thunder = ['kaminari.png', '雷'];
    const windy = ['kaze.png', '大風'];

    const icons = [cloudy, rainy, snowy, thunder, windy];

    console.log('hoge', weather);

    // 晴れの時のみ昼夜切り替え
    if (sunny.includes(weather)) {
        // 昼
        if (5 <= hour && hour <= 18) {
            return sunny[0];
        } else {
            return 'moon.png';
        }
    }
    // その他の時
    var result = icons.filter(function(arrayObj) {
        return arrayObj.includes(weather);
    });
    icons.forEach(function(icon) {
        if (icon.includes(weather)) {
            return icon[0];
        }
    });

    if (result.length > 0) {
        return result[0][0];
    } else {
        // 例外
        console.log('該当する天気アイコンがありません');
        return sunny[0];
    }
}

    


/*
// PHPを介してjsonデータを取得
var httpObj = new XMLHttpRequest();
var prefix = 'http://';
var address = 'localhost';
var port = '5000';
httpObj.getAllResponseHeaders('GET', `${prefix}${address}:${port}/modules/weather/getLWWS.php?city=${cityId}`, true);
httpObj.onload = function() {
    data = JSON.parse(this.responseText);
    console.log(data);
}
httpObj.send();
*/

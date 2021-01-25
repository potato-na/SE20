// ref: https://re-fort.hatenablog.com/entry/2014/02/23/122005
var audio; // 再生中のオーディオ

// 前回分リストを消去
function clearSong(){
    $(function(){
        $("#error").text("");
        $(".itunes-embed").remove();
    });
  };

// 渡された情報をもとに検索、データ取得
function getInfo (keyWord, maxNum) {

    // 基本情報
    var params = {
        lang: 'ja_jp',
        entry: 'music',
        media: 'music',
        country: 'JP',
        term: keyWord,
        limit: maxNum,
    };

      // APIに投げる
    $.ajax({
        url: 'https://itunes.apple.com/search',
        method: 'GET',
        data: params,
        dataType: 'jsonp',
        
        //成功
        success: function(json) {
            showData(json);
        },

        //失敗
        error: function() {
            $(function(){
                $("#error").text("＊ エラーが発生しました ＊");
            });
        },
    });
};

// 取得したデータを表示する
function showData(json) {
    // データが取得できた
    if (json.results.length != 0) {
        var sArray = shuffleArrayList(json.results.length);
        var alubumPictures = {};
        for (var i = 0, len = json.results.length; i < len; i++) {
            var result = json.results[i];
            var html;
            var artist = result.artistName;
            var album = result.collectionName;
            var track = result.trackName;
            console.log(artist, album, track);
    
            // 最初のデータのみ画面上に表示する
            if (sArray[i] == 0){
                // html = '<div id ="' + sArray[i] + '" class="music itunes-embed freezed itunes-kind-song"><a id="u' + sArray[i] + '" href="' + result.trackViewUrl + '&amp;at=10ldcR" rel="nofollow" target="_blank"><img src="' + result.artworkUrl100 + '" class="itunes-embed-image" /></a><div class="itunes-embed-info"><p class="itunes-embed-title"><a id="s' + sArray[i] + '" href="' + result.trackViewUrl + '&amp;at=10ldcR" rel="nofollow" target="_blank">' + result.trackName + '</a></p><ul><li id="a' + sArray[i] + '" class="itunes-embed-artist">' + result.artistName + '</li><li class="itunes-embed-price">&yen;' + result.trackPrice + '</li><li class="itunes-embed-badge"><a href="' + result.trackViewUrl + '&amp;at=10ldcR" rel="nofollow" target="_blank"><img src="/images/theme/itunes/itunes-badge-itunes@2x.png" width="44px" height="15px"></a></ul><br/><ul><li class="itunes-embed-preview"><audio id="m' + sArray[i] + '" src="' + result.previewUrl + '" controls="" ></audio></div>';
                html = `<div id=${sArray[i]} class='music'> <p class='itunes-embed-title'>${track}</p> <p class='itunes-embed-artist'>${artist}</p> <p class='itunes-embed-preview'><audio id='m${sArray[i]}' src='${result.previewUrl}' controls=''></audio></p> </div>`
            }
    
            // 他は隠しておく
            else{
                // html = '<div id ="' + sArray[i] + '" class="itunes-embed freezed itunes-kind-song" style="display:none;"><a id="u' + sArray[i] + '" href="' + result.trackViewUrl + '&amp;at=10ldcR" rel="nofollow" target="_blank"><img src="' + result.artworkUrl100 + '" class="itunes-embed-image" /></a><div class="itunes-embed-info"><p class="itunes-embed-title"><a id="s' + sArray[i] + '" href="' + result.trackViewUrl + '&amp;at=10ldcR" rel="nofollow" target="_blank">' + result.trackName + '</a></p><ul><li id="a' + sArray[i] + '" class="itunes-embed-artist">' + result.artistName + '</li><li class="itunes-embed-price">&yen;' + result.trackPrice + '</li><li class="itunes-embed-badge"><a href="' + result.trackViewUrl + '&amp;at=10ldcR" rel="nofollow" target="_blank"><img src="/images/theme/itunes/itunes-badge-itunes@2x.png" width="44px" height="15px"></a></ul><br/><ul><li class="itunes-embed-preview"><audio id="m' + sArray[i] + '" src="' + result.previewUrl + '" controls="" ></audio></div>';
                html = `<div id=${sArray[i]} style='display:none;'> <p class='itunes-embed-title'>${track}</p> <p class='itunes-embed-artist'>${artist}</p> <p class='itunes-embed-preview'><audio id='m${sArray[i]}' src='${result.previewUrl}' controls=''></audio></p> </div>`
            }
            alubumPictures[sArray[i]] = `<div class="cover"><img src=${result.artworkUrl100} width=300>"</div>`;
            // htmlにアペンド
            $('#displayArea').append(html);
            //$("#prevSong").show();
            //$("#nextSong").show();
            //$("#favSong").show();
            //$("#fav").show();
        }
        // coverFlow下に追加
        for (var i = 0, len = sArray.length; i< len; i++) {
            $(".coverflow").append(alubumPictures[i]);
        }
        setTimeout('setCoverFlow()', 10);
        // coverflow 表示
        //setCoverFlow();

        // 1曲目を再生
        playingIndex = 0;
        playSong(0 , 0);
    }
    // データが取得できなかった
    else {
        $("#error").text("＊ 曲が見つかりませんでした ＊");
    }
}

function setCoverFlow() {
    $(function() {
        $('.coverflow').coverflow({
            selectedCss: {opacity: 1}, 
            outerCss: {opacity: 0.1},

            }
        );
    });
}

function changeCoverFlow(index) {
    $(function() {
        $('.coverflow').coverflow('index', index);
    });
}

// 取得した曲をランダムに配列に入れる(Fisher-Yates)
function shuffleArrayList(j){
    var array = [];
    for (var k = 0; k < j; k++) {
        array[k] = k;
    }
    Array.prototype.shuffle = function() {
        var i = this.length;
        while(i){
            var j = Math.floor(Math.random()*i);
            var t = this[--i];
            this[i] = this[j];
            this[j] = t;
      }
      return this;
    }
    array.shuffle();
    return array;
  }
  
// 前の曲を再生
function prevSong(){
    $(function(){
        var num = $(".music").length;
        if (num == 1) {
            $("#error").text("＊ 最初の曲です ＊");
        }
        else {
            $("#" + (num -1)).css("display", "none");
            $("#" + (num - 1)).removeClass("music");
            $("#" + (num - 2)).show();
            stopSong(num - 1);
            playSong(num - 2);
            changeCoverFlow(num - 2);
        }
    });
};
  
// 次の曲を再生
function nextSong(){
    $(function(){
        var num = $(".music").length;
        if ($("#" + num).length == 0) {
            $("#error").text("＊ 最後の曲です ＊");
        }
        else {
            $("#" + (num - 1)).css("display", "none");
            $("#" + num).addClass("music");
            $("#" + num).show();
            stopSong(num - 1);
            playSong(num);
            changeCoverFlow(num);
        }
    });
};

// 曲を停止
function stopSong(num) {
    $(function(){
        $("#error").text("");
        $("#m" + num).get(0).pause();
        $("#m" + num).get(0).currentTime = 0;
    });
};

// 曲を再生
function playSong(num2){
    $(function(){
        $("#error").text("");
        audio = $("#m" + num2).get(0);
        audio.volume = $( "#slider" ).slider( 'value' );
        audio.play();
        // 曲終了時に次の曲を再生させる
        $("#m" + num2).bind("ended", function(){ if($("#m" + num2).get(0).ended){nextSong();}});
    });
};

window.onload = function() {
    getInfo('マルーン5', 10);
}
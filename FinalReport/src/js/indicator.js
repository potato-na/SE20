// const { NodeEventEmitter } = require("electron");

// ハンドポーズの読み込みを通知
var fixedView = false;
// ピンチジェスチャーの座標基点
var previous_pinch_y = null;

// websocketクライアントを立ち上げる
function connect() {
	var prefix = 'ws://';
	var address = 'localhost';
	var port = '9001';
	connection = new WebSocket(`${prefix}${address}:${port}`);

	//通信が接続された場合
	connection.onopen = function(event) { 
		console.log('webserver: websocket connected');
	};
	 
	//エラーが発生した場合
	connection.onerror = function(error) { 
		console.log(error);
	};
	 
	//メッセージを受け取った場合
	connection.onmessage = function(event) { 
		console.log('webserver: message received: ', event.data);
		showIndicator(event.data);
	};
	  
	//通信が切断された場合
	connection.onclose = function() { 
		console.log('webserver: connection closed');
	};
}

// 図形の描画
function showIndicator(message) {
	var message = JSON.parse(message)

	// Canvasの取得
	var canvas = document.getElementById('indicator_canvas');
	var ctx = canvas.getContext('2d');

	// 大きさ 円の場合は半径, 矩形の場合は長辺短辺
	const circleRadius = canvas.height/2;
	const longSide = canvas.height * 2;
	const shortSide = canvas.height/3;

	// ジェスチャー操作の受付
	if (message.pose == 'Pose.PINCH') {
		var currentValue = parseFloat(message.description);
		if (previous_pinch_y != null) {
			changeSlider(currentValue - previous_pinch_y);
		}
		previous_pinch_y = currentValue;
	} else {
		// 初期化
		previous_pinch_y = null;
	}

	if (message.pose == 'Gesture') {
		inputGesture(message.description);
	}


	// view の固定化
	if (message.pose == 'Pose.FOX') {
		fixedView = !fixedView;
		if (fixedView) {
			ctx.clearRect(0, 0, canvas.width, canvas.height);
			ctx.fillStyle = 'rgb(255, 0, 0)';
			drawCircle(canvas, ctx, circleRadius, canvas.height/2, circleRadius);
		} 
	}
	if (fixedView) {
		return 
	}
	
	// Canvasの削除
	ctx.clearRect(0, 0, canvas.width, canvas.height);
	// パスのリセット
	ctx.beginPath();
	// 色の指定
	ctx.fillStyle = 'rgb(0, 0, 255)';

	
	// for debug
	// document.getElementById("clock_date").innerHTML = `${canvas.width} ${canvas.height} ${canvas.clientWidth} ${canvas.clientHeight}`;

	// 処理の振り分け
	switch (message.pose) {
		case 'Pose.NONE':
			return
		case 'Pose.ZERO':
			// 画面消去
			transition('default');
			drawRectangle(canvas, ctx, canvas.width/2 - longSide/2, canvas.height/2 - shortSide/2, longSide, shortSide);
			break;
		case 'Pose.ONE':
			// カレンダー
			drawCircle(canvas, ctx, canvas.width/2, canvas.height/2, circleRadius);
			transition('calendar');
			break;
		case 'Pose.TWO':
			// 天気
			drawCircle(canvas, ctx, canvas.width/4, canvas.height/2, circleRadius);
			drawCircle(canvas, ctx, 3*canvas.width/4, canvas.height/2, circleRadius);
			transition('weather');
			break;
		case 'Pose.THREE':
			// 時計
			transition('clock');
			drawCircle(canvas, ctx, canvas.width/6, canvas.height/2, circleRadius);
			drawCircle(canvas, ctx, canvas.width/2, canvas.height/2, circleRadius);
			drawCircle(canvas, ctx, 5*canvas.width/6, canvas.height/2, circleRadius);
			break;
		case 'Pose.FOUR':
			// 音楽プレイヤー
			transition('music');
			drawCircle(canvas, ctx, canvas.width/8, canvas.height/2, circleRadius);
			drawCircle(canvas, ctx, 3*canvas.width/8, canvas.height/2, circleRadius);
			drawCircle(canvas, ctx, 5*canvas.width/8, canvas.height/2, circleRadius);
			drawCircle(canvas, ctx, 7*canvas.width/8, canvas.height/2, circleRadius);
			break;
		case 'Pose.FIVE':
			drawCircle(canvas, ctx, canvas.width/10, canvas.height/2, circleRadius);
			drawCircle(canvas, ctx, 3*canvas.width/10, canvas.height/2, circleRadius);
			drawCircle(canvas, ctx, canvas.width/2, canvas.height/2, circleRadius);
			drawCircle(canvas, ctx, 7*canvas.width/10, canvas.height/2, circleRadius);
			drawCircle(canvas, ctx, 9*canvas.width/10, canvas.height/2, circleRadius);
			break;

		// ジェスチャー操作
		case 'Gesture':
			break
		// ピンチ操作
		case 'Pose.PINCH':
			break

		default:
			console.log(`unnexpected message: ${message}`);
	}
}

// 円を指定の位置に描く
function drawCircle(canvas, ctx, x, y, radius) {
	// arc(x, y, 半径, start角度, 終了角度, 半時計周りか)
	ctx.arc(x, y, radius, 0, Math.PI * 2, false);
	ctx.fill();
}

// 矩形を指定の位置に描く
function drawRectangle(canvas, ctx, x, y, longSide, shortSide) {
	ctx.fillRect(x, y, longSide, shortSide);

}

// canvasの大きさを div の範囲に合わせる初期化処理
function initCanvas() {
	var canvas = document.getElementById('indicator_canvas');
	var ind = document.getElementById('indicator');
	canvas.width = ind.offsetWidth;
	canvas.height = ind.offsetHeight;
}

// html の書き換え
function transition(module) {
	// document.getElementById('module').innerHTML = `<iframe src='modules/${module}/${module}.html' id='iframe_module'></iframe>`;
	document.getElementById('iframe_module').src = `modules/${module}/${module}.html`
}

// debug: showIndicator
function debugLoop() {
	var messages = ['{pose:Pose.FIVE}', '{pose:Pose.NONE}', '{pose:Pose.ZERO}', '{pose:Pose.ONE}', '{pose:Pose.TWO}'];
	var messages = ['{"pose":"Pose.PINCH", "description":"0.1"}', 
					'{"pose":"Pose.PINCH", "description":"0.2"}',
					'{"pose":"Pose.PINCH", "description":"0.3"}', 
					'{"pose":"Pose.PINCH", "description":"0.2"}', 
					'{"pose":"Pose.PINCH", "description":"0.1"}', 
					'{"pose":"Pose.ONE", "description":""}', 
					'{"pose":"Pose.PINCH", "description":"0.1"}', 
					'{"pose":"Pose.PINCH", "description":"0.2"}', 
					'{"pose":"Pose.FOUR", "description":""}', 
					];
	var messages = ['{"pose":"Pose.FOUR", "description":"0"}', 
					'{"pose":"Gesture", "description":"right"}',
					'{"pose":"Gesture", "description":"left"}',
					'{"pose":"Gesture", "description":"left"}',
					'{"pose":"Gesture", "description":"right"}',
					'{"pose":"Gesture", "description":"right"}',
					];
					
	var messages = ['{"pose":"Pose.FOUR", "description":"0"}', 
					'{"pose":"Pose.PINCH", "description":"0.5"}', 
					'{"pose":"Pose.PINCH", "description":"0.3"}',
					'{"pose":"Pose.PINCH", "description":"0.5"}', 
					'{"pose":"Pose.PINCH", "description":"0.7"}', 
					'{"pose":"Pose.PINCH", "description":"0.9"}', 
					];				
	var cnt = 0;
	setInterval(function() {
		console.log(messages[cnt]);
		showIndicator(messages[cnt]);
		cnt += 1;
		if (cnt >= messages.length) {
			cnt = 0
		}
	}, 5000);
}

// ページの読み込み時に実行
window.onload = function() {
	connect();
	initCanvas();
	// showIndicator('Pose.THREE');
	// debugLoop();
}




// websocket サーバー
var WebSocket = require('ws');

var WebsocketServer = function() {
	var port = '9001';
	const server = new WebSocket.Server({port: port});
	console.log('websocket server started')

	var cnt_client = 0;
	// 接続成功時
	server.on('connection', ws => {
		ws.id = cnt_client;
		cnt_client ++;
		console.log(`websocket connected: id ${ws.id}`);
		// クライアントからのデータ受信時にコール
		ws.on('message', message => {
			console.log('message received at websocket server');
			console.log(message);
			// 接続している自分以外のクライアント全てに送信
			server.clients.forEach(client => {
				if (client !== ws) {
					client.send(message);
				}
			});
		});

		// 切断時にコール
		ws.on('close', () => {
			console.log('connection closed')
		});
	});
}

module.exports = WebsocketServer;
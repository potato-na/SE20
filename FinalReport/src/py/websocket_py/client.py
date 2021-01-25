# https://www.raspberrypirulo.net/entry/websocket-client
import websocket
try:
    import thread
except ImportError:
    import _thread as thread
import time

class Client():
	def __init__(self, host_addr, print_on_message=False, callback_message=None):
		#デバッグログの表示設定
		# websocket.enableTrace(True)

		# WebsocketAppオブジェクトに関数登録
		self.ws = websocket.WebSocketApp(host_addr, 
										on_message = lambda ws, msg: self.on_message(ws, msg), 
										on_error   = lambda ws, msg: self.on_error(ws, msg),
										on_close   = lambda ws: self.on_close(ws)
										)
		self.ws.on_open = lambda ws: self.on_open(ws)

		self.is_connected = False # サーバーと接続してるか
		self.print_on_message = print_on_message # メッセージ受信時に送信するか
		self.callback_message = callback_message

	# メッセージ受信時
	def on_message(self, ws, message):
		if self.callback_message != None:
			self.callback_message(message)
		if self.print_on_message:
			print("receive : {}".format(message))

	# エラー時
	def on_error(self, ws, error):
		print(error)

	# サーバーからの切断時
	def on_close(self, ws):
		print("### closed ###")
		self.is_connected = False

	# サーバーとの接続時
	def on_open(self, ws):
		print('### connected ###')
		self.is_connected = True
		#thread.start_new_thread(self.run, ())

	# メッセージ送信
	def send(self, input_data):
		self.ws.send(input_data)

	# サーバーから接続時にスレッドで起動
	def run(self, *args):
		while True:
			time.sleep(0.1)
			input_data = input('send data:')
			self.ws.send(input_data)

		self.ws.close()
		print('thread terminating...')

	# websocketクライアント起動
	def run_forever(self):
		self.ws.run_forever()

	# websocketクライアント起動 (別スレッド)
	def run_forever_thread(self):
		thread.start_new_thread(self.run_forever, ())


def test():
	HOST_ADDR = 'ws://192.168.11.18:9001/' # サーバーのIPアドレスとポート番号
	client = Client(HOST_ADDR)
	client.run_forever()

# 5秒に一回送信
def test_send():
	HOST_ADDR = 'ws://192.168.11.18:9001/' # サーバーのIPアドレスとポート番号
	client = Client(HOST_ADDR)
	client.run_forever_thread()

	cnt = 0
	while True:
		if client.is_connected == True:
			time.sleep(5)
			client.send(str(cnt))
			cnt += 1


if __name__ == '__main__':
	test_send()
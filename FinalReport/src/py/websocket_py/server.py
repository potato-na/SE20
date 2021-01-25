#https://www.raspberrypirulo.net/entry/websocket-server

from websocket_server import WebsocketServer
import logging
try:
    import thread
except ImportError:
    import _thread as thread

class Server():
	def __init__(self, host, port, print_message_received=True):
		self.server = WebsocketServer(port, host=host)
		self.print_message_received = print_message_received
		self.set_fn()

	# クライアント接続時にコール
	def new_client(self, client, server):
		print('new client connected and was given id {}'.format(client['id']))
		# 全クライアントにメッセージを送信
		self.server.send_message_to_all('new client has joined us')

	# クライアント切断時
	def client_left(self, client, server):
		print('clien({} disconnected'.format(client['id']))

	# クライアントからメッセージを受信
	def message_received(self, client, server, message):
		self.server.send_message_to_all(message)
		if self.print_message_received:
			print("client({}) said: {}".format(client['id'], message))

	# メソッドの設定
	def set_fn(self):
		self.server.set_fn_new_client(self.new_client)
		self.server.set_fn_client_left(self.client_left)
		self.server.set_fn_message_received(self.message_received)

	# サーバー起動
	def run_forever(self):
		self.server.run_forever()

	# サーバー起動 (別スレッド)
	def run_forever_thread(self):
		thread.start_new_thread(self.run_forever, ())

if __name__ == '__main__':
	IP_ADDDR = '192.168.11.18' # サーバーを立てたPCのIPアドレス
	PORT = 9001
	server = Server(IP_ADDDR, PORT)
	server.run_forever()


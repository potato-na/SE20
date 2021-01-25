# 左右振りのハンドジェスチャーの認識
# いずれかの指の landmark の x 座標を基準にする
from collections import deque
from time import time

class HandGestureRecognizer():
	def __init__(self):
		# 移動判定の閾値
		self.threshold_dist = 5/7
		self.threshold_v = 6

		# 判定用 
		self.x_queue = deque([])
		self.time_queue = deque([])

	# 振り払い動作の識別
	def is_swing(self, current_x, parm_height):
		self.x_queue.append(current_x)
		self.time_queue.append(time())
		result = (False, 'None') # swingフラグ, 左右

		if len(self.x_queue) >= 3:
			is_moving = self.is_moving(parm_height)
			if is_moving[0]:
				if is_moving[1] > 0:
					result = (True, 'right')
				else:
					result = (True, 'left')

			# 最古データの削除
			self.x_queue.popleft()
			self.time_queue.popleft()
			return result

		else:
			return result

		

	# 移動中かどうか
	def is_moving(self, parm_height):
		dist = self.calc_d(self.x_queue[0], self.x_queue[1]) # 符号付き
		v = self.calc_v(dist, self.time_queue[0], self.time_queue[1])
		if (abs(dist) > self.threshold_dist*parm_height) and (abs(v) > self.threshold_v*parm_height):
			return (True, dist)
		else:
			return (False, None)

	# 符号付き移動距離 
	def calc_d(self, x0, x1):
		return x1 - x0

	# 移動速度
	def calc_v(self, dist, t0, t1):
		return dist / (t1 - t0)

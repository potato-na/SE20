import json

##websocket のためのデータ整形
class HandDataEncoder():
    def __init__(self):
        pass

    # jsonにエンコード
    def encode(self, pose, description=''):
        return json.dumps(self.to_dic(pose, description))

    # 辞書形式に変換
    def to_dic(self, pose, description=''):
        return {'pose': pose, 
                'description': description}
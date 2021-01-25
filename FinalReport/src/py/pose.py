from enum import Enum
from collections import deque, Counter
import numpy as np
from numpy import linalg as LA

# ハンドポーズ
class Pose(Enum):
    ZERO = 0
    ONE = 1
    TWO = 2
    THREE = 3
    FOUR = 4
    FIVE = 5
    SIX = 6
    SEVEN = 7
    EIGHT = 8
    NINE = 9
    NONE = -1

    FOX = 100
    GOOD = 101
    PINCH = 102

# ハンドポーズの識別
class PoseRecognizer():
    def __init__(self, frame=7):
        self.HANDPOSE_JUDGE = {
            (False, False, False, False, False): Pose.ZERO,
            (False, True, False, False, False): Pose.ONE,
            (False, True, True, False, False): Pose.TWO,
            (False, True, True, True, False): Pose.THREE,
            (False, True, True, True, True): Pose.FOUR,
            (True, False, False, False, False): Pose.NONE,
            (True, True, False, False, False): Pose.NONE,
            (True, True, True, False, False): Pose.NONE,
            (True, True, True, True, False): Pose.NONE,
            (True, True, True, True, True): Pose.FIVE,

            (True, False, False, False, False): Pose.GOOD, 
            (False, True, False, False, True): Pose.FOX,
            (True, False, True, True, True): Pose.PINCH, 
        }
        self.finger_calculator = _FingerCalculator()

        # ポーズ平均識別
        self.frame = frame
        self.poses = deque([])

    # 多数決でハンドポーズの識別
    # ウィンドウをずらしてランキングをとる
    def ranking_handpose(self, landmark):
        pose = self.judge_handpose(landmark)
        self.poses.append(pose)
        if len(self.poses) > self.frame:
            c = Counter(self.poses)
            current_pose = c.most_common()[0][0]
            # 最古データの削除
            self.poses.popleft()
            return current_pose
        else:
            return Pose.NONE


    # ハンドポーズの識別
    def judge_handpose(self, landmark):
        finger_is_open_tuple = self.finger_calculator.is_finger_open(landmark)
        return self._judge_handpose(finger_is_open_tuple)

    def _judge_handpose(self, finger_is_open_tuple):
        if not finger_is_open_tuple in self.HANDPOSE_JUDGE:
            return Pose.NONE
        else:
            return self.HANDPOSE_JUDGE[finger_is_open_tuple]

# landmark から指の開閉を識別
class _FingerCalculator():
    def __init__(self):
        self.threshold_thumb = 80 # 親指の曲がり角度閾値
        self.threshold = 100 # 親指以外の曲がり角度閾値

    # 2ベクトルの成す角
    def calc_vec_angle(self, vec0, vec1, vec2):
        vec0 = np.array((vec0.x, vec0.y, vec0.z))
        vec1 = np.array((vec1.x, vec1.y, vec1.z))
        vec2 = np.array((vec2.x, vec2.y, vec2.z))

        vec10 = vec0 - vec1
        vec12 = vec2 - vec1

        dot = np.inner(vec10, vec12)
        norm = LA.norm(vec10) * LA.norm(vec12)
        rad = dot/norm
        return np.rad2deg(np.arccos(np.clip(rad, -1.0, 1.0)))

    # 指 1 本の曲がり角度
    # input: landmarks[0, 1, 2, 3, 4]
    def calc_finger_angle(self, finger_landmarks):
        joint_angle1 = 180 - self.calc_vec_angle(finger_landmarks[0], finger_landmarks[1], finger_landmarks[2])
        joint_angle2 = 180 - self.calc_vec_angle(finger_landmarks[1], finger_landmarks[2], finger_landmarks[3])
        joint_angle3 = 180 - self.calc_vec_angle(finger_landmarks[2], finger_landmarks[3], finger_landmarks[4])
        return joint_angle1 + joint_angle2 + joint_angle3

    # 全指の曲がり角度から折り曲げを計算
    # input: landmarks.landmark (手のlandmarkの情報)
    def is_finger_open(self, landmark):
        thumb = self.calc_finger_angle((landmark[0], landmark[1], landmark[2], landmark[3], landmark[4]))
        index = self.calc_finger_angle((landmark[0], landmark[5], landmark[6], landmark[7], landmark[8]))
        middle = self.calc_finger_angle((landmark[0], landmark[9], landmark[10], landmark[11], landmark[12]))
        ring = self.calc_finger_angle((landmark[0], landmark[13], landmark[14], landmark[15], landmark[16]))
        pinkie = self.calc_finger_angle((landmark[0], landmark[17], landmark[18], landmark[19], landmark[20]))
        return (thumb<self.threshold_thumb, index<self.threshold, middle<self.threshold, ring<self.threshold, pinkie<self.threshold)



def main():
    pose_recognizer = PoseRecognizer()
    for finger_is_open_tuple in pose_recognizer.HANDPOSE_JUDGE:
        pose = pose_recognizer._judge_handpose(finger_is_open_tuple)
        print(pose)

if __name__ == '__main__':
    main()
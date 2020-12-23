#2200104045 七井香樹
#入力された年のクリスマス(12/25)の曜日を出力する

#Zellerの公式を用いて曜日を計算
def Xmax(y):
    m = 12
    d = 25
    
    return (y + int(y/4) - int(y/100) + int(y/400) + int((13 * m + 8)/5) + d)%7

def main():
    day = ["日", "月", "火", "水", "木", "金", "土"]
    
    year = int(input("クリスマスの曜日を検索．\n検索する西暦を数字で入力してください："))
    
    print(year, "年のクリスマスは", day[Xmax(year)], "曜日です．")

if __name__=="__main__":
    main()
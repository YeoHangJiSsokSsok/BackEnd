import csv
import requests
import chardet
from google.cloud import storage
from google.oauth2 import service_account
import os
import pandas as pd
from sqlalchemy import create_engine, VARCHAR
import configparser
import json

API_KEY = os.environ.get('API_KEY')

with open('key.json', 'r') as key_file:
    key_data = json.load(key_file)


def find_place(place, save):
    base_url = 'https://places.googleapis.com/v1/places:searchText'
    data = {
        "textQuery" : place,
        "languageCode" : "ko"
    }
    response = requests.post(base_url, json=data, headers={
        'Content-Type': 'application/json',
        'X-Goog-Api-Key': API_KEY,
        'X-Goog-FieldMask': "places.formattedAddress,places.location,places.photos"
    })
    data = response.json()
    #print(data)
    if response.status_code == 200:
        
        if (save):
            try:
                name = data['places'][0]['photos'][0]['name']
                photo_url = "https://places.googleapis.com/v1/"+name+"/media"
                photo_params = {
                    "maxWidthPx": 400,
                    "maxHeightPx": 400,
                    "key": API_KEY
                }

                photo_response = requests.get(photo_url, params=photo_params)
                with open(f"./photos/"+place+".jpg", "wb") as f:
                    f.write(photo_response.content)
            except (KeyError, IndexError):
                print(place + "사진이 존재하지 않습니다.")

        latitude = data['places'][0]['location']['latitude']
        longitude = data['places'][0]['location']['longitude']
        formatted_address = data['places'][0]['formattedAddress']
        return formatted_address, latitude, longitude
    else:
        return None 


def store_image(name, source):
    if not os.path.exists(source):
        return " "
    # Credentials 객체 생성
    credentials = service_account.Credentials.from_service_account_file(key_data)
    # 구글 스토리지 클라이언트 객체 생성
    client = storage.Client(credentials=credentials, project=credentials.project_id)
    bucket = client.get_bucket("yeohangjissokssok")
    blob = bucket.blob(name)
    blob.upload_from_filename(source,content_type='image/jpeg')

    print(blob.public_url)
    return blob.public_url

def file_existence(file_name):
    # Credentials 객체 생성
    credentials = service_account.Credentials.from_service_account_file(key_data)
    # 구글 스토리지 클라이언트 객체 생성
    client = storage.Client(credentials=credentials, project=credentials.project_id)
    bucket = client.get_bucket("yeohangjissokssok")
    blob = bucket.blob(file_name)
    if blob.exists():
        # Return the blob's URL
        return blob.public_url
    else:
        # Return None if the blob does not exist
        return None


from PIL import Image

def resize_image(path, target_width=400):
    try:
        # 이미지 열기
        image = Image.open(path)

        # 이미지 크기 조절 (비율 유지)
        width_percent = (target_width / float(image.size[0]))
        target_height = int((float(image.size[1]) * float(width_percent)))

        resized_image = image.resize((target_width, target_height))

        # 이미지 저장
        resized_image.save(path)
    except Exception as e:
        print(f"Error: {e}")

def make_place_table():
    f = open('8100labeling.csv', 'r')
    rdr = csv.reader(f)

    newlines = []
    newlines.append(["id","region", "name", "address", "latitude", "longitude", "photo_url"])
    newlines2 = []

    place = 0
    flag = False

    for line in rdr:
        if line[0] == "부정 리뷰" or line[0] == "지역 및 장소":
            flag = True
            continue
        if line[0] != '':
            flag = False
            place += 1
            places = line[0].split('_')
            address = None
            img = file_existence(places[len(places)-1])
            if img == None:
                print(line[0])
                address = find_place(places[len(places)-1], True)
                img = store_image(places[len(places)-1], "./photos/"+places[len(places)-1]+".jpg")
            else :
                address = find_place(places[len(places)-1], False)
            if len(places) == 2:
                newlines.append([place, places[0], places[1], address[0], address[1], address[2], img])
            elif len(places) == 3:
                newlines.append([place ,places[0] + '_' + places[1], places[2], address[0], address[1], address[2], img])
        if flag == False:
            newlines2.append(line)
        
    newf = open('places.csv', 'w', newline='')
    wr = csv.writer(newf)
    wr.writerows(newlines)
    newf.close()    

    newf = open('8100labeling.csv', 'w', newline='')
    wr = csv.writer(newf)
    wr.writerows(newlines2)
    newf.close()

    f.close()
    
def make_SA_table():
    import csv

    f = open('8100labeling.csv', 'r')
    rdr = csv.reader(f)
    newlines = []
    newlines.append(["id", "place_id", "month", "mood_positive_number", "mood_negative_number", "mood_neutral_number", "transport_positive_number",
	"transport_negative_number", "transport_neutral_number", "congestion_positive_number", "congestion_negative_number", "congestion_neutral_number",
	"infra_positive_number", "infra_negative_number", "infra_neutral_number"])
    data = []

    for i in range(12):
        data.append([
            [0,0,0],
            [0,0,0],
            [0,0,0],
            [0,0,0]
        ])
    
    place = 1  
    num = False
    for line in rdr:
        if line[0] != '' and num:
            for i in range(12) :
                newlines.append(['',place, i+1, data[i][0][0], data[i][0][1], data[i][0][2], data[i][1][0], data[i][1][1], data[i][1][2], data[i][2][0], data[i][2][1], data[i][3][2], data[i][3][0], data[i][3][1], data[i][3][2]])
            place += 1
            data = []
            for i in range(12):
                data.append([
                    [0,0,0],
                    [0,0,0],
                    [0,0,0],
                    [0,0,0]
                ])
        num = True
        if (line[2] == ""):
            continue
        dates = line[2].split()
        month = int(dates[1].replace("월","")) - 1
        
        for i in range(4,8):
            if (line[i].strip() == "0"):
                data[month][i-4][0] += 1
            elif(line[i].strip() == "1"):
                data[month][i-4][1] += 1
            elif(line[i].strip() == "3"):
                data[month][i-4][2] += 1

    for i in range(12) :
        newlines.append(['',place, i+1, data[i][0][0], data[i][0][1], data[i][0][2], data[i][1][0], data[i][1][1], data[i][1][2], data[i][2][0], data[i][2][1], data[i][3][2], data[i][3][0], data[i][3][1], data[i][3][2]])

    newf = open('SAMonthlySummarys.csv', 'w', newline='')
    wr = csv.writer(newf)

    for l  in newlines:
        if (l[0] != 0):
            wr.writerow(l)

    f.close()
    newf.close()

def make_keyword_table():
    import csv
    count = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    f = open('8100labeling.csv', 'r')
    rdr = csv.reader(f)
    newlines = []
    newlines.append(["id", "place_id", "month", "family_count", "couple_count", "friend_count", "kids_count", "alone_count",
                    "healing_count", "walk_count", "picnic_count", "hiking_count", "beach_count", "flower_count", "tour_count",
                    "shopping_count", "museum_count", "food_count", "night_view_count", "exercise_count"])
    data = []

    for i in range(12):
        data.append([0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0])
    
    place = 1  
    num = False
    for line in rdr:
        if line[0] != '' and num:
            for i in range(12) :
                newlines.append(['', place, i+1, data[i][0], data[i][1], data[i][2], data[i][3], data[i][4], data[i][5], data[i][6], data[i][7], data[i][8], data[i][9], data[i][10], data[i][11], data[i][12], data[i][13], data[i][14], data[i][15], data[i][16]])
            place += 1
            data = []
            for i in range(12):
                data.append([0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0])
        num = True
        if (line[2] == ""):
            continue
        dates = line[2].split()
        month = int(dates[1].replace("월","")) - 1
        
        if (line[8].strip() == "가족"):
            data[month][0] += 1
            count[0] += 1
        elif(line[8].strip() == "연인"):
            data[month][1] += 1
            count[1] += 1
        elif(line[8].strip() == "친구"):
            data[month][2] += 1
            count[2] += 1
        elif(line[8].strip() == "아이"):
            data[month][3] += 1
            count[3] += 1
        elif(line[8].strip() == "홀로"):
            data[month][4] += 1
            count[4] += 1
        elif(line[8].strip() == "힐링"):
            data[month][5] += 1
            count[5] += 1
        elif(line[8].strip() == "산책"):
            data[month][6] += 1
            count[6] += 1
        elif(line[8].strip() == "나들이"):
            data[month][7] += 1
            count[7] += 1
        elif(line[8].strip() == "등산"):
            data[month][8] += 1
            count[8] += 1
        elif(line[8].strip() == "바다"):
            data[month][9] += 1
            count[9] += 1
        elif(line[8].strip() == "꽃"):
            data[month][10] += 1
            count[10] += 1
        elif(line[8].strip() == "관광"):
            data[month][11] += 1
            count[11] += 1
        elif(line[8].strip() == "쇼핑"):
            data[month][12] += 1
            count[12] += 1
        elif(line[8].strip() == "전시"):
            data[month][13] += 1
            count[13] += 1
        elif(line[8].strip() == "맛집"):
            data[month][14] += 1
            count[14] += 1
        elif(line[8].strip() == "야경"):
            data[month][15] += 1
            count[15] += 1
        elif(line[8].strip() == "운동"):
            data[month][16] += 1
            count[16] += 1
                
    for i in range(12) :           
        newlines.append(['',place, i+1, data[i][0], data[i][1], data[i][2], data[i][3], data[i][4], data[i][5], data[i][6], data[i][7], data[i][8], data[i][9], data[i][10], data[i][11], data[i][12], data[i][13], data[i][14], data[i][15], data[i][16]])
    
    print(count)
    total = 0
    for i in range(17):
        total += count[i]
    print(total)
    # newf = open('SAMonthlyKeywords.csv', 'w', newline='')
    # wr = csv.writer(newf)

    # for l  in newlines:
    #     if (l[0] != 0):
    #         wr.writerow(l)

    # f.close()
    # newf.close()
  
def make_review_table():
    f = open('8100labeling.csv', 'r')
    rdr = csv.reader(f)
    newlines = []
    newlines.append(["id", "samonthlysummary_id", "samonthlykeyword_id", "date", "content", "mood", "transport", "congestion", "infra", "keyword"])

    place = 0
    for line in rdr:
        if line[0] != '':
            place += 1
        if (line[2] == ""):
            continue
        dates = line[2].split()
        month = int(dates[1].replace("월",""))
        id = (place - 1) * 12 + month
        if (line[8] == ""):
            line[8] = '0'
        newlines.append(['',id,id, dates[0] + ' ' + dates[1], line[10], line[4], line[5], line[6], line[7], line[8]])


    newf = open('reviews.csv', 'w', newline='')
    wr = csv.writer(newf)


    wr.writerows(newlines)

    f.close()
    newf.close()
 
def add_SA_table():
    f = open('places.csv', 'r')
    rdr1 = csv.reader(f)
    places = {}
    for line in rdr1:
        places[line[2]] = line[0]
    
    df = open('SAMonthlySummarys_plus.csv', 'r')
    data = []
    ordr = csv.reader(df)
    cnt = 0
    for line in ordr:
        if (cnt == 0):
            data.append(line)
        else:
            line[0] = cnt
            data.append(line)
        cnt+=1
    
    newf = open('unlabeled_else2_최종.csv', 'r')
    rdr = csv.reader(newf)
    
    place = 0
    num = False
    for line in rdr:
        if line[0] != '' and num:
            print(line[0])
            name = line[0].split('_')
            place = int(places.get(name[len(name)-1]))
        if num == False:
            num = True
            continue
        if (line[2] == ""):
            continue
        dates = line[2].split()
        month = int(dates[1].replace("월",""))
        index = (place - 1) * 12 + month
        
        print(place)
        print(month)
        print(line[4:8])
        print(data[index])
        for i in range(4,8):
            if (line[i].strip() == "0"):
                data[index][(i-4)*3+3] = int(data[index][(i-4)*3+3] ) + 1
            elif(line[i].strip() == "1"):
                data[index][(i-4)*3+4] = int(data[index][(i-4)*3+4] ) + 1
            elif(line[i].strip() == "3"):
                data[index][(i-4)*3+5] = int(data[index][(i-4)*3+5] ) + 1
        print(data[index])
        print()
    
    newf = open('SAMonthlySummarys_plus.csv', 'w', newline='')
    wr = csv.writer(newf)
    wr.writerows(data)

    f.close()
    newf.close()

def add_Keywords_table():
    f = open('places.csv', 'r')
    rdr1 = csv.reader(f)
    places = {}
    for line in rdr1:
        places[line[2]] = line[0]
    
    df = open('SAMonthlyKeywords.csv', 'r')
    data = []
    ordr = csv.reader(df)
    cnt = 0
    for line in ordr:
        if (cnt == 0):
            data.append(line)
        else:
            line[0] = cnt
            data.append(line)
        cnt+=1
    
    
    newf = open('unlabeled_seoul1_최종.csv', 'r')
    rdr = csv.reader(newf)
    
    place = 0
    num = False
    for line in rdr:
        if line[0] != '' and num:
            print(line[0])
            name = line[0].split('_')
            place = int(places.get(name[len(name)-1]))
        if num == False:
            num = True
            continue
        if (line[2] == ""):
            continue
        dates = line[2].split()
        month = int(dates[1].replace("월",""))
        index = (place - 1) * 12 + month
        print(place)
        print(month)
        print(line[8])
        print(data[index])
        print()
        if (line[8].strip() == "가족"):
            data[index][3] = int(data[index][3]) + 1
        elif(line[8].strip() == "연인"):
            data[index][4] = int(data[index][4]) + 1
        elif(line[8].strip() == "친구"):
            data[index][5] = int(data[index][5]) + 1
        elif(line[8].strip() == "아이"):
            data[index][6] = int(data[index][6]) + 1
        elif(line[8].strip() == "홀로"):
            data[index][7] = int(data[index][7]) + 1
        elif(line[8].strip() == "힐링"):
            data[index][8] = int(data[index][8]) + 1
        elif(line[8].strip() == "산책"):
            data[index][9] = int(data[index][9]) + 1
        elif(line[8].strip() == "나들이"):
            data[index][10] = int(data[index][10]) + 1
        elif(line[8].strip() == "등산"):
            data[index][11] = int(data[index][11]) + 1
        elif(line[8].strip() == "바다"):
            data[index][12] = int(data[index][12]) + 1
        elif(line[8].strip() == "꽃"):
            data[index][13] = int(data[index][13]) + 1
        elif(line[8].strip() == "관광"):
            data[index][14] = int(data[index][14]) + 1
        elif(line[8].strip() == "쇼핑"):
            data[index][15] = int(data[index][15]) + 1
        elif(line[8].strip() == "전시"):
            data[index][16] = int(data[index][16]) + 1
        elif(line[8].strip() == "맛집"):
            data[index][17] = int(data[index][17]) + 1
        elif(line[8].strip() == "야경"):
            data[index][18] = int(data[index][18]) + 1
        elif(line[8].strip() == "운동"):
            data[index][19] = int(data[index][19]) + 1
        print(data[index])
            
    newf = open('SAMonthlyKeywords_plus.csv', 'w', newline='')
    wr = csv.writer(newf)
    wr.writerows(data)

    f.close()
    newf.close()
    
def add_review_table():
    f = open('places.csv', 'r')
    rdr = csv.reader(f)
    places = {}
    for line in rdr:
        places[line[2]] = line[0]
    
    newf = open('unlabeled_else2_최종.csv', 'r')
    rdr2 = csv.reader(newf)
    
    df = open('reviews_plus.csv', 'r')
    data = []
    ordr = csv.reader(df)
    for line in ordr:
        data.append(line)
        
    place = 0
    num = False
    for line in rdr2:
        if line[0] != '' and num:
            print(place)
            print(line[0])
            name = line[0].split('_')
            place = int(places.get(name[len(name)-1]))
        if num == False:
            num = True
            continue
        if (line[2] == ""):
            continue
        dates = line[2].split()
        month = int(dates[1].replace("월",""))
        id = (place - 1) * 12 + month
        print(place)
        print(month)
        print(id)
        print()
        if (line[8] == ""):
            line[8] = '0'
        data.append(['',id,id, dates[0] + ' ' + dates[1], line[10].replace('\n',''), line[4], line[5], line[6], line[7], line[8]])


    wf = open('reviews_plus.csv', 'w', newline='')
    wr = csv.writer(wf)
    wr.writerows(data)

    f.close()
    wf.close()
    
    
def import_DB():
    
    #db_connection_url = 'postgresql://postgres:GP_SA524@localhost:5432/testdb?client_encoding=utf8'
    db_connection_url = 'postgresql://postgres:HzHmlhFByqyoLP6@yeohangjissokssok-db.fly.dev:5432/yeohangjissokssok'
    engine = create_engine(db_connection_url)

    # dtype_mapping = {
    #     'name': VARCHAR(100),
    #     'region': VARCHAR(100),
    #     'address': VARCHAR(200)
    # }
    # file_path = 'places.csv'
    # df = pd.read_csv(file_path)
    # df.to_sql('places', engine, if_exists='replace', index=False, dtype=dtype_mapping)
    
    file_path = 'SAMonthlySummarys_plus.csv'
    df = pd.read_csv(file_path)
    #df['id'] = range(1, len(df) + 1)
    df.to_sql('sa_monthly_summary', engine, if_exists='replace', index=False)
    
    file_path = 'SAMonthlyKeywords_plus.csv'
    df = pd.read_csv(file_path)
    #df['id'] = range(1, len(df) + 1)
    df.to_sql('sa_monthly_keyword', engine, if_exists='replace', index=False)
    
    file_path = 'reviews_plus.csv'
    df = pd.read_csv(file_path)
    df['id'] = range(1, len(df) + 1)
    df.to_sql('reviews', engine, if_exists='replace', index=False)
    
    query = f'SELECT * FROM places'
    df = pd.read_sql(f'SELECT * FROM places', engine)



#make_place_table()
#make_SA_table()
# make_keyword_table()
# make_review_table()

add_Keywords_table()
add_SA_table()
add_review_table()
import_DB()


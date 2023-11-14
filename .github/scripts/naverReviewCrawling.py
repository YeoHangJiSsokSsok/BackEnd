from selenium.webdriver.common.by import By
from urllib3.util.retry import Retry
from requests.adapters import HTTPAdapter
from openpyxl import Workbook
from bs4 import BeautifulSoup
from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.chrome.service import Service as ChromeService
import time
import datetime
import requests
import csv
import re
import pandas as pd
import os
import sys
import urllib.request


#코엑스아쿠아리움, 몽마르뜨공원, 63시티 다시
def getReviews(url, keyword):
    
    res = driver.get(url)
    driver.implicitly_wait(30)

    now = datetime.datetime.now()

    # Start crawling/scraping!
    try:
        # Pagedown
        # print(soup.select_one('span.place_section_count').get_text())
        # return
        reviews_data = []
        index = 150
        try:
            while index != 0:
                time.sleep(0.25) 
                driver.find_element(By.TAG_NAME, 'body').send_keys(Keys.END)
                time.sleep(0.25)
                # driver.execute_script("window.scrollBy(0, -200);")
                # time.sleep(0.025)
                driver.find_element(By.CSS_SELECTOR, '#app-root > div > div > div > div:nth-child(6) > div:nth-child(3) > div.place_section.k5tcc > div.NSTUp > div > a').click()
                time.sleep(0.25)
                index-=1
        except Exception as e:
            print('finish')
            
        time.sleep(5)
        
        
        html = driver.page_source
        soup = BeautifulSoup(html, 'lxml')
        reviews = soup.select('li.YeINN')
        print(len(reviews))
        for r in reviews:
            #nickname = r.select_one('div.SdWYt>a>div.VYGLG')
            content = r.select_one('div.ZZ4OK > a > span.zPfVt')
            date = r.select_one('div.qM6I7 > div > div._7kR3e > span.tzZTd > span:nth-child(3)')
            #revisit = r.select('div.sb8UA>span.P1zUJ')[1]

            #exception handling
            #nickname = nickname.get_text() if nickname else ''
            content = content.get_text() if content else ''
            date = date.get_text() if date else ''
            #revisit = revisit.text if revisit else ''
            time.sleep(0.02)

            #print(content, '/', date)
            if (len(content) >= 10):
                reviews_data.append([content, date])
            time.sleep(0.02)
            
        #Save the file
        print(len(reviews_data))
        csv_file_name = 'naver_review_' + keyword + '.csv'
        with open(csv_file_name, 'w', newline='', encoding='utf-8') as csv_file:
            csv_writer = csv.writer(csv_file)
            csv_writer.writerow(['Content', 'Date'])
            csv_writer.writerows(reviews_data)

    except Exception as e:
        print(e)
        file_name = 'naver_review_' + keyword + '.csv'


# Webdriver headless mode setting
options = webdriver.ChromeOptions()
#options.add_argument('headless')
options.add_argument('window-size=1920x1080')
options.add_argument("disable-gpu")

service = ChromeService(executable_path=ChromeDriverManager().install())

# BS4 setting for secondary access
session = requests.Session()
headers = {
    "User-Agent": "user value"}

retries = Retry(total=5,
                backoff_factor=0.1,
                status_forcelist=[500, 502, 503, 504])

session.mount('http://', HTTPAdapter(max_retries=retries))
df = pd.read_csv('places.csv')
df['naver_map_url'] = ''
driver = webdriver.Chrome(service=service, options=options)
driver.implicitly_wait(30)

for i, keyword in enumerate(df['name'].tolist()): 
    
    print("이번에 찾을 키워드 :", i, f"/ {df.shape[0]} 행", keyword) 
    if (i != 167):
        continue
    if (i==16):
        keyword = "롯데월드몰"
    try: 
        naver_map_search_url = f'https://map.naver.com/p/search/{keyword}' # 검색 url 만들기 
        driver.get(naver_map_search_url) # 검색 url 접속 = 검색하기 
        time.sleep(4) # 중요
        # iframe = driver.find_element(By.CSS_SELECTOR, "#searchIframe")
        # driver.switch_to.frame(iframe)
        time.sleep(1)
        try:
            if i == 142:
                driver.find_element(By.CSS_SELECTOR, '#_pcmap_list_scroll_container > ul > li:nth-child(3) > div > div > a').click()
            # elif i < 142:
            #     print("nochange")
            # else:
            #     driver.find_element(By.CSS_SELECTOR, '#_pcmap_list_scroll_container > ul > li:nth-child(1) > div> div > a').click()
            cu = driver.current_url
            print(cu)
            place_id = re.search(r'place/(\d+)', cu).group(1)
            if (i == 110):
                place_id = 33703856
            if place_id != None:
                final_url = f'https://pcmap.place.naver.com/place/{place_id}/review/visitor'
                getReviews(final_url, keyword)
        except Exception as e:
            print("찾는데 문제") 
        
    except IndexError: 
        print('none') 
    
    #df.to_csv('url_completed.csv', encoding = 'utf-8-sig')
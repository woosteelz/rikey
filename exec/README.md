# 포팅 메뉴얼

## 1. 빌드 및 배포

### 기술 스택 및 버전

| 설치 목록        | version   |
| ---------------- | --------- |
| Ubuntu           | 20.04 LTS |
| SpringBoot       | 2.6.4     |
| MySQL            | 8.0.28    |
| Nginx            | 1.18.0    |
| Npm              | 6.14.4    |
| Backend Node.js  | 10.19.0   |
| Java openjdk     | 11.0.14   |
| Frontend Node.js | 16.13.1   |
| JavaScript       | ES8+      |
| React Native     | 0.67.3    |
| Zustand          | 3.7.1     |
| Yarn             | 1.22.17   |
|                  |           |



### 빌드 및 배포 과정

#### 프론트엔드

- Android

  - 깃 클론

    - ```
      git clone https://lab.ssafy.com/s06-bigdata-dist-sub2/S06P22C208.git
      ```

  - frontend 폴더의 패키지 설치

    - ```
      yarn
      ```

  - 안드로이드 휴대폰 연결 or 안드로이드 에뮬레이터 연결 후 frontend 폴더에서

    - ```
      yarn android
      ```

      

#### 백엔드

깃 클론

```
git clone https://lab.ssafy.com/s06-bigdata-dist-sub2/S06P22C208.git
```



backend 폴더의 gradle 빌드

```
cd backend
gradle build
```



backend 폴더의 node.js 빌드

```
cd SimpleUpload
npm install
```



node 무중단 배포

```
forever 설치
sudo npm install forever -g
실행
sudo forever start SimpleImageUpload.js
```



서버 무중단 배포

```
cd ..
nohup java -jar build/libs/rikey-0.0.1-SNAPSHOT.jar --server.servlet.context-path=/api &
```



### Port 및 DB 정보

- SpringBoot 기본 포트
  - 8080

- Node.js 기본 포트 
  - 3000
- MySQL 데이터베이스명
  - ssafy_web_db
- MySQL 계정이름
  - rikey



## 2. 외부 서비스 정보

- 네이버 소셜 로그인 API
- 날씨 API
- 구글 지도 API



## 3. DB 덤프 파일

 [Rikey_Dump.sql](Rikey_Dump.sql) 



## 4. 시연 시나리오

[시연 시나리오 보기](시연시나리오.png)                   

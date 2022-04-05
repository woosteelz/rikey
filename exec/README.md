# 포팅 메뉴얼

## 1. 빌드 및 배포

### 기술 스택 및 버전

| 설치 목록    | version   |
| ------------ | --------- |
| Ubuntu       | 20.04 LTS |
| SpringBoot   | 2.6.4     |
| MySQL        | 8.0.28    |
| Nginx        | 1.18.0    |
| Npm          | 6.14.4    |
| Node.js      | 10.19.0   |
| Java openjdk | 11.0.14   |
|              |           |
|              |           |
|              |           |
|              |           |
|              |           |
|              |           |



### 빌드 및 배포 과정

#### 프론트엔드



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



## 4. 시연 시나리오


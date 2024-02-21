# HostChecker

## 앱 설명
다수의 서버(호스트)들을 관리하는  네트워크 관제 프로그램입니다. 

---

## 설계 스펙

언어: **Java 17**

프레임워크: **Spring Boot, Spring Security 6 이상**

데이터베이스: **MySQL, PostgreSQL, MS SQL, Maria DB**

데이터베이스 DDL:  **JPA Hibernate**를 사용했기 때문에 따로 sql 쿼리를 돌리고 테이블 만들 필요가 없습니다. 

앱이 처음으로 실행될때 테이블이 자동으로 만들어집니다. 그리고 앱이 다시 제시작될때 기존에 있는 데이터가 사라지지 않습니다.  

또한, 데이터베이스  선택은 자유입니다. JPA Hibernate는 대부분 데이터베이스와 적합합니다. 

저는 아래 와 같이 데이터베이스 이름을 지었습니다. 

```java
spring.datasource.url=jdbc:mysql://localhost:3306/hostcheck
spring.datasource.driver=com.mysql.cj.jdbc.Driver
```

---

## 실행 방법

```java
java -jar HostChecker-0.0.1-SNAPSHOT.jar
```

---

# 주요 기능

## 1. Register - POST /api/auth/register

     유저가 직접 앱에 등록할 수 있게 해주는 기능 API (테이블에 dummy 데이터가 없어서 간단한 register 기능을 구현해봤습니다.  유저 이메일/ 이름 중복 확인 validation 하지 않습니다 ) 

**Parameters:** 

```java
{
    "firstname": "mehri10",
    "lastname" : "mehri10",
    "role": "admin",
    "email": "mehri10@gmail.com",
    "password": "1234"
}
```

**Response**: 

```java
{
    "username": "mehri10@gmail.com",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtZWhyaTEwQGdtYWlsLmNvbSIsImlhdCI6MTcwODQ4MzQ0NCwiZXhwIjoxNzA4NDg0ODg0fQ.TJf1WaV0Ei-J2pPH25fmMRTqnZvpXBr2gAVP6O5MUg8"
}
```

---

## 2. Login -  POST /api/auth/authenticate

      로그인 REST API입니다. 

**Parameters:** 

```java
{
    "email": "mehri7@gmail.com",
    "password": "1234"

}
```

**Response**: 

```java
{
    "username": "mehri7@gmail.com",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtZWhyaTdAZ21haWwuY29tIiwiaWF0IjoxNzA4NDgzNjIwLCJleHAiOjE3MDg0ODUwNjB9.EYMtWZxTaYmttfAO8T7imcclA01U5ROnpQn6yr_djWc"
}
```

---

## 3. Host Register -  POST /api/host/register

      관리자인 유저만 access 할 수 있는 host 등록 REST API 입니다.  authenticate해서 얻은 JWT token을 사용해야 합니다. 

**Parameters:** 

```java
{
    "name":"bbc",
    "ip": "212.58.253.67"
}
```

**Response**: 

```java
{
    "id": 203,
    "name": "bbc",
    "ip": "212.58.253.67",
    "createdAt": "2024-02-21T02:33:55.076+00:00",
    "updatedAt": "2024-02-21T02:33:55.076+00:00",
    "lastAliveTime": null
}
```

---

## 4. Host Status (상태) -  GET /api/host/{id}/status
 
      하나의 호스트 현재 상태 조회 REST API 입니다.  authenticate해서 얻은 JWT token을 사용해야 합니다. 

**Parameters:** 

```java

```

**Response**: 

```java
{
    "hostId": 202,
    "hostname": "ping",
    "ip": "4.2.2.2",
    "createdAt": "2024-02-21T02:32:47.601+00:00",
    "updatedAt": "2024-02-21T02:34:10.487+00:00",
    "alive": true
}
```

---

## 5. Host Monitoring -  GET /api/host/monitor

      호스트 상태 모니터링  REST API 입니다. authenticate해서 얻은 JWT token을 사용해야 합니다. 

`/api/host/monitor?hostId=`    hostId 는 입력하지 않으면  호스드들이 다 조회 돼요. 

**Parameters:** 

```java

```

**Response**: 

```java
{
        "id": 152,
        "ip": "8.8.8.8",
        "lastAliveTime": "2024-02-21T02:21:36.341+00:00",
        "alive": true
    }
```

---

## 6. Host Monitoring -  GET /api/view/logs

      사용자의 로그인, 로그아웃을 포함한 주요 사건에 대한 감사기록을 볼 수 있는  REST API 입니다.  authenticate해서 얻은 JWT token을 사용해야 합니다. 

**Parameters:** 

```java

```

**Response**: 

```java
[
    {
        "id": 1,
        "eventDateTime": "2024-02-21T11:01:51.054902",
        "eventType": "LOGIN",
        "userIdentity": "mehri7@gmail.com"
    },
    {
        "id": 2,
        "eventDateTime": "2024-02-21T11:05:43.154349",
        "eventType": "LOGIN",
        "userIdentity": "mehri7@gmail.com"
    },
    {
        "id": 3,
        "eventDateTime": "2024-02-21T11:08:19.44416",
        "eventType": "LOGIN",
        "userIdentity": "mehri7@gmail.com"
    }

]
```

---

## 7. Host 조회 -  GET /api/host/viewhosts
 
      호스트조회 REST API 입니다.  authenticate해서 얻은 JWT token을 사용해야 합니다. 

**Parameters:** 

```java

```

**Response**: 

```java
[
    {
        "id": 152,
        "name": "test8",
        "ip": "8.8.8.8",
        "createdAt": "2024-02-21T02:21:14.299+00:00",
        "updatedAt": "2024-02-21T02:21:36.344+00:00"
    },
    {
        "id": 202,
        "name": "ping",
        "ip": "4.2.2.2",
        "createdAt": "2024-02-21T02:32:47.601+00:00",
        "updatedAt": "2024-02-21T02:34:10.487+00:00"
    }
]
```

---

## 8. Host 수정 -  PUT /api/host/{id}
   
      호스트 조회 REST API 입니다.  authenticate해서 얻은 JWT token을 사용해야 합니다. 

**Parameters:** 

```java
{
    "id": "152",
    "name": "google",
    "ip": "8.8.8.8"
}
```

**Response**: 

```java
{
    "id": 152,
    "name": "google",
    "ip": "8.8.8.8",
    "createdAt": "2024-02-21T02:21:14.299+00:00",
    "updatedAt": "2024-02-21T03:46:56.721+00:00",
    "lastAliveTime": "2024-02-21T02:21:36.341+00:00"
}
```

---

## 9. 나머지 기능

소스코드에 다른 기능이 포함 되어 있습니다. ( 활동 시간에 따른 잠금 처리)


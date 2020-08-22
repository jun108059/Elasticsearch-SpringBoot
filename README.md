Elasticsearch-SpringBoot

- ElasticSearch 기반 검색엔진 구축 [https://esbook.kimjmin.net/](https://esbook.kimjmin.net/)
- 관계형 DB에 저장된 Data 색인 - 빠르고 쉽게 검색 엔진 활용할 수 있는 시스템 구축

# 프로젝트 개요

- ElasticSearch 기반의 검색엔진을 구축 후 관계형 DB에 저장된 Data(ex. QnA, 공지사항 등,,,)에 대해 데이터를 색인하여 빠르고 쉽게 검색 엔진을 활용할 수 있는 시스템을 구축한다.

### **📕 기술 요구 사항(**Technical Requirements Specification)

- DB 데이터 색인 및 벌크
- 검색 Rest API 제공
- 관리 화면 제공
- 동적 색인 처리 ~~(색인 스케쥴링)~~
- 검색 옵션(하이라이트) 기능 제공 처리

### 📙 제약사항

~~모든 입출력은 JSON 형태~~

- **API 기능명세에 기술된 API 모두 개발**
→ 검색 컨텐츠 조회 API
→ 컨텐츠 Bulk API 
→ 컴텐츠 부분 색인 API

    [검색 API](https://www.notion.so/API-2ba1c9ee9ba54257a5d2cde2a25e0440)

    [Bulk API](https://www.notion.so/Bulk-API-5a830ca7f4514787a48ba7c240045ce5)

- **관리자 화면**
→ Spring boot 프레임웍
→ DB 지정 후 전체 색인 처리 기능
→ DB 지정 후 부분 색인 처리 기능
→ 색인된 index 확인 기능
- **단위 테스트(Unit Test)**
→ Test Code 작성
→ 기능 검증
    - Question
- **문서 관리**
→ 문제해결 방법 기술
→ 빌드 및 실행 방법 기술

### 관리자 UI

- 관리자를 통한 색인 처리 기능
    - 처리 시 인덱스명 지정 가능.

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d449a5f2-476b-47f0-966c-9d190c45c68c/Untitled.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d449a5f2-476b-47f0-966c-9d190c45c68c/Untitled.png)

- 색인 처리 히스토리 저장 및 조회
- 검색엔진의 색인 리스트 조회 기능

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/12ed98bc-8b39-4020-99a4-6b306f667a59/Untitled.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/12ed98bc-8b39-4020-99a4-6b306f667a59/Untitled.png)

# API Specifications

- 검색 컨텐츠 조회
- 컨텐츠 Bulk
- 컨텐츠 부분 색인

## **1.1 검색 컨텐츠 조회 API**

- 열어보기

## **1.2 컨텐츠 Bulk API**

- 열어보기

## **1.3 컨텐츠 부분 색인 API**

- 열어보기

---

## 요구사항 명세서

[Untitled](https://www.notion.so/ee85dbd5e1254784b62a9db4ce7360df)
<p align="center">
    <img width="300px" src="image/Logo.png" align="center" alt="ElasticSearch" />
    <h2 align="center">Spring boot based ElasticSearch</h2>
    <p align="center">Just Search and Indexing Page <br> Spring boot REST API with ElasticSearch</p>
</p>

<p align="center">
    <a href="https://dev-youngjun.tistory.com/">
        <img alt="Blog" src="https://img.shields.io/badge/-Spring-68AD3A?logo=Spring"/>
    </a>
    <a href="https://dev-youngjun.tistory.com/">
        <img alt="Blog" src="https://img.shields.io/badge/-Elasticsearch-E7B933?logo=Elasticsearch"/>
    </a>
    <a href="https://dev-youngjun.tistory.com/">
        <img alt="Blog" src="https://img.shields.io/badge/-Java-red?logo=Java"/>
    </a>
    <a href="https://github.com/jun108059/Elasticsearch-SpringBoot">
    </a>
    <br />
    <br />
    <a>
</p>

---

- ElasticSearch 기반 검색엔진 구축  
- 관계형 DB에 저장된 Data 색인 - 빠르고 쉽게 검색 엔진 활용할 수 있는 시스템 구축  

## 프로젝트 개요

- ElasticSearch 기반의 검색엔진을 구축 후 관계형 DB에 저장된 Data(ex. QnA, 공지사항 등,,,)에 대해 데이터를 색인하여 빠르고 쉽게 검색 엔진을 활용할 수 있는 시스템을 구축한다.

### 📕 기술 요구 사항(Technical Requirements Specification)

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

- **관리자 화면**
→ Spring boot 프레임웍
→ DB 지정 후 전체 색인 처리 기능
→ DB 지정 후 부분 색인 처리 기능
→ 색인된 index 확인 기능

- **단위 테스트(Unit Test)**
→ Test Code 작성
→ 기능 검증
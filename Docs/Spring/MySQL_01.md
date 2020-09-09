# 1. MySQL 데이터를 JSON Type으로 변환하기

---

[연구과제](https://github.com/jun108059/Elasticsearch-SpringBoot)를 진행하다보니
기존에 RDB(MySQL)에 저장된 많은 양의 data를 Elasticsearch로 색인하기 위해 `JSON Type`의 data로 바꿔야하는 기술 요구사항이 생겼다.

MySQL에서 JSON Type을 어떻게 지원하는지 찾아보고 정리한 글이다.

---

🗓 20.09.09

## ✍실습환경

- 💡 [Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) 11.0.8 LTS
- 💡 [Spinrg boot](https://start.spring.io/) 2.3.3.RELEASE
- 💡 [MySQL](https://dev.mysql.com/) 8.0
- 💡 [Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/7.9/index.html) 7.9.0
- 💡 [Git Bash](https://gitforwindows.org/) 2.27.0
- 💡 [IntelliJ Ultimate](https://www.jetbrains.com/ko-kr/idea/) 2020.2.1
- 💡 [HeidiSQL](https://www.heidisql.com/) 11.0
- 💡 Windows 10

---

## 1. MySQL JSON Functions

MySQL은 5.7.8 부터 JSON Type을 지원해주기 시작했고 8.0 버전의 [공식 문서](https://dev.mysql.com/doc/refman/8.0/en/json-functions.html)에 JSON Type을 위한 많은 Functions을 제공해준다.

## 2. JSON 값을 생성하는 함수 - JSON_OBJECT

**JSON_OBJECT funtion**

```mysql
JSON_OBJECT([key, val[, key, val] ...])
```

- `Key-Value` 쌍 목록 (비어있을 수 있음)을 검사하고 해당 쌍을 포함하는 `JSON 객체`를 반환
- `Key`가 NULL이거나 인수 수가 홀수이면 오류를 발생

```mysql
mysql> SELECT JSON_OBJECT('id', 'mandoo', 'name', 'young-jun');

+--------------------------------------------------+
| JSON_OBJECT('id', 'mandoo', 'name', 'young-jun') |
+--------------------------------------------------+
| {"id": "mandoo", "name": "young-jun"}            |
+--------------------------------------------------+
```

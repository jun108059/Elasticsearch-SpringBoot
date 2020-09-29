# 4. 컨텐츠 부분 색인 API 명세

## API

| URI                  | Method  | 설명                        |
| -------------------- | ------- | --------------------------- |
| /simple/search/indexing/id | **POST**/**PUT**/**DELETE** | ID를 이용한 컨텐츠 동적 색인 API |

- **Elasticsearch**에 저장된 Index의 Document 생성/수정/삭제 기능
- **동기 방식**으로 색인 완료 후 응답

## 4.1 Request

### 4.1.1 Request Header

| Header       | Value            |
| ------------ | ---------------- |
| Content-Type | application/json |

### 4.1.2 Request Parameter

클라이언트는 아래의 규격에 맞게 parameter를 Setting하여 요청한다.

| 데이터 항목   | 변수 이름  | 타입   | 비고                          |
| ------------- | ---------- | ------ | ----------------------------- |
| 서비스 아이디 | service_id | String | Admin에서 등록한 서비스 ID값 |
| 컨텐츠 ID 값 | contents_id_value | String | 컨텐츠 = document |

### 4.1.3 Request JSON 구조

```json
{
   "service_id": "SVC_QNA",
   "contents_id_value": "35"
}
```

## 4.2 Response

### 4.2.1 Response Code

- **Normal**: Ok (200)
- **Error**: Bad Request (400), Not Found (404), Internal Server Error (500), Service Unavailable (503)

### 4.2.2 Response Parameter

서버는 아래의 규격에 맞게 parameter를 Setting하여 응답한다.

| 데이터 항목   | 변수 이름  | 타입   | 비고                          |
| ------------- | ---------- | ------ | ----------------------------- |
| N/A | | | |

# ElasticSearch에 Index 생성하고 Mapping 만들기

🗓 20.08.25

## 1. Index 생성하기

### ✔ 생성하기

```bash
$ curl -XPUT http://localhost:9200/customer?pretty
```

결과
```bash
{
  "acknowledged" : true,
  "shards_acknowledged" : true,
  "index" : "customer"
}
```

### ✔ 확인하기

```bash
$ curl -XGET http://localhost:9200/customer?pretty
```

결과
```bash
{
  "customer" : {
    "aliases" : { },
    "mappings" : { },
    "settings" : {
      "index" : {
        "creation_date" : "1598336236445",
        "number_of_shards" : "1",
        "number_of_replicas" : "1",
        "uuid" : "reH0_sl9RYKzx85d8bp1og",
        "version" : {
          "created" : "7090099"
        },
        "provided_name" : "customer"
      }
    }
  }
}
```

위 결과에 비어있는 `mapping`을 생성하자.

## 2. Mapping 생성하기

`Mapping`으로 데이터 타입을 미리 지정해주기 위해 `mapping JSON` 파일이 있어야한다.

프로젝트에 필요한 `customor` Index에 `curation`에 관련된 Property를 모두 작성하여 MySQL(RDB)의 Table과 매칭되게 선언했다.

```JSON
{
	"curation" : {
		"properties" : {
			"curation_seq" : {
				"type" : "integer"
			},
			"curation_title" : {
				"type" : "text"
			},
			"template" : {
				"type" : "text"
			},
			"template_img" : {
				"type" : "text"
			},
			"ending_txt" : {
				"type" : "text"
			},
			"tag" : {
				"type" : "text"
			},
			"pc_hits" : {
				"type" : "integer"
            },
            "m_hits" : {
				"type" : "integer"
			},
			"reservation_dt" : {
				"type" : "date"
            },
            "reg_dt" : {
				"type" : "date"
            },
            "up_dt" : {
				"type" : "date"
            },
            "status" : {
				"type" : "text"
            },
            "open_fl" : {
				"type" : "text"
            },
            "main_fl" : {
				"type" : "text"
			},
			"writer" : {
				"type" : "text"
			}
		}
	}
}
```

### ✔ Mapping 생성하기

```bash
$ curl -XPUT 'http://localhost:9200/customer/curation/_mapping?include_type_name=true&pretty' -d @curation_mapping.json -H 'Content-Type: application/json'
```

### ✔ Mapping 확인하기

```bash
$ curl -XGET http://localhost:9200/customer?pretty
```

## 3. 실제 데이터를 Index에 Bulk하기

여러 `Document`를 `bulk`로 한번에 넣어보자.

### ✔ Bulk 하기

```bash
$ curl -XPOST http://localhost:9200/_bulk?pretty --data-binary @sampleCurationData.json -H 'Content-Type: application/json'
```

### 📌 Error


```bash
{
  "error" : {
    "root_cause" : [
      {
        "type" : "json_e_o_f_exception",
        "reason" : "Unexpected end-of-input: expected close marker for Object (start marker at [Source: (org.elasticsearch.common.bytes.AbstractBytesReference$MarkSupportingStreamInputWrapper); line: 1, column: 1])\n at [Source: (org.elasticsearch.common.bytes.AbstractBytesReference$MarkSupportingStreamInputWrapper); line: 2, column: 1]"
      }
    ],
    "type" : "json_e_o_f_exception",
    "reason" : "Unexpected end-of-input: expected close marker for Object (start marker at [Source: (org.elasticsearch.common.bytes.AbstractBytesReference$MarkSupportingStreamInputWrapper); line: 1, column: 1])\n at [Source: (org.elasticsearch.common.bytes.AbstractBytesReference$MarkSupportingStreamInputWrapper); line: 2, column: 1]"
  },
  "status" : 400
}
```

`MySQL`에서 추출한 ROW가 `ES Bulk` 입력 형태랑 다른 것 같음

이렇게 접근하는게 아니고 요구사항에 맞게 관계형 DB에 저장된 Data를 색인하고 검색하도록 수정할 계획

**`Logstash` 로 연동하기**
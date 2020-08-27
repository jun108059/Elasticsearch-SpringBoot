[Untitled](https://www.notion.so/c858af447fb444b18bf4c8eae85487f5)

### **1.1.1 Request**

### 1.1.1.1 Request Header

[Untitled](https://www.notion.so/5792ea9c18f745f88df3c5e955bfbfcc)

### 1.1.1.2 Request Parameter

클라이언트는 아래의 규격에 맞게 parameter를 세팅하여 요청한다.

[Untitled](https://www.notion.so/b611269dfee4463d977f7bf33ff4437f)

### 1.1.1.3 Request JSON 구조

```json
{   
    "service_id": "{{SERVICE_ID}}",
    "search_text": "채용",
    "reslut_columns": ["title", "content", "read_cnt", "reg_dt", "display_dt"],
     
    "highlight" : {
        "columns" : [{
            "column": "title"
              
        },{
            "column": "content"
        }],
        "prerfix_tag": "<b>",
        "postfix_tag": "</b>"
    },
      
    "page_size": 10,
    "page_no": 1,
    
}
```

### 1.1.2 Response

### 1.1.2.1 Response Code

Normal: Ok (200)

데이터 없을 시: No Content (204)

Error: Bad Request (400), Not Found (404), Internal Server Error (500), Service Unavailable (503)

### 1.1.2.2 Response Parameter

서버는 아래의 규격에 맞게 parameter를 세팅하여 응답한다.

[Untitled](https://www.notion.so/33820d35cc244970a81f5a628ab64e57)

### 1.1.2.3 Response JSON 구조

```json
{   
    "total": 5400,
    "page_size": 10,
    "page": 1,
    "result": [
        {
            "logo_title": "",
            "notice_fl": "n",
            "mem_type": "",
            "direct_fl": "n",
            "author": "서비스기획팀",
            "mobile_notice_fl": "n",
            "title": "실시간 채용상품 및 인재연락처보기 결제 일시 중단 안내",
            "main_display_fl": "n",
            "mcom_idx": 0,
            "content": "<!-- 서비스중단 안내 -->\r\n<div class=\"sv-stop\">\r\n<h4><img src=\"http://www.saraminimage.co.kr/icon_error.png\" align=\"absMiddle\" />&nbsp;점검 작업으로&nbsp;인한 <b style=\"color: #f3475f;\">실시간 채용상품 및 인재연락처보기 결제 서비스</b>&nbsp;일시 중단 안내</h4>\r\n<div class=\"con\">\r\n<p>사람인이 더 나은 서비스를 제공해드리고자<br /><b style=\"color: #f3475f;\">1월 2일 (수) 18시 30분~19시까지 (30분간) 서비스 점검 작업</b>을 진행합니다.<br />이 시간 동안 <b style=\"color: #f3475f;\">실시간 채용상품·인재연락처보기 결제 서비스</b>가 일시적으로 중단될 예정이오니, 이용에 참고 바랍니다.<br />(권역 사람인 사이트 포함)</p>\r\n※ 채용공고 등록, 인재 연락처 검색, 확인 및 그 외 사람인 서비스는 정상적으로 이용이 가능하오니,<br />많은 이용 부탁 드립니다.</div>\r\n</div>\r\n<div class=\"con2\">사람인을 찾아주신 회원님들께 불편을 드리게 되어, 대단히 죄송합니다.</div>\r\n<div></div>\r\n<!-- //서비스중단 안내 -->",
            "url": null,
            "read_cnt": 339,
            "display_dt": "2013-01-02 11:04:48",
            "rec_idx": 0,
            "highlight": {
                "title": "실시간 <b>채용</b>상품 및 인재연락처보기 결제 일시 중단 안내",
                "content": "<br />이 시간 동안 <b style=\"color: #f3475f;\">실시간 <b>채용</b>상품·인재연락처보기 결제 서비스</b>가 일시적으로 중단될 예정이오니, 이용에 참고 바랍니다."
            },
            "edit_dt": "2013-01-04 16:23:44",
            "reg_dt": "2013-01-02 11:04:48",
            "@timestamp": "2018-11-30T07:31:51.042Z",
            "@version": "1",
            "link_target": "self",
            "idx": 13861,
            "category": 2,
            "display_fl": "n",
            "reply_cnt": 0
        },
        {
            "logo_title": "",
            "notice_fl": "n",
            "mem_type": "",
            "direct_fl": "n",
            "author": "콘텐츠기획팀 채용속보파트 강해미",
            "mobile_notice_fl": "n",
            "title": "[㈜KTM&S] 2013년 상반기 공채가 떴다!! (진행중)",
            "main_display_fl": "n",
            "mcom_idx": 0,
            "content": "<div class=\"bbs_wing\">\r\n<p><img src=\"http://www.saraminimage.co.kr/event_2010/top_wing2.gif\" alt=\"대기업 공채가 떳다\" /></p>\r\n<p>\"  ㈜KTM&S - KT M&S 대졸 신입사원 공개채용 공고가 떴다!!  \"<br />1월 2일 접수가 시작됩니다 !<br /><b>( 모집기간 : 2013년 1월 2일 ~ 2013년 1월 15일 24시 까지 )</b><br />모집분야 및 상세내용을 확인하시려면<br />하단의 바로가기를 클릭해주세요 !</p>\r\n<p><a target=\"_blank\" href=\"http://www.saramin.co.kr/recruit/RecruitBbsSearch.php?code=bbs_recruit&mode=view&idx=14009184\">★☆㈜KTM&S - KT M&S 대졸 신입사원 공개채용 공고 바로가기 ☆★</a></p>\r\n</div>",
            "url": null,
            "read_cnt": 2335,
            "display_dt": "2013-01-02 13:01:28",
            "rec_idx": 0,
            "highlight": {
                "title": "[㈜KTM&S] 2013년 상반기 공채가 떴다!! (진행중)",
                "content": "code=bbs_recruit&mode=view&idx=14009184\">★☆㈜KTM&S - KT M&S 대졸 신입사원 공개<b>채용</b> 공고 바로가기 ☆★</a></p>\r\n</div>"
            },
            "edit_dt": "0000-00-00 00:00:00",
            "reg_dt": "2013-01-02 13:01:27",
            "@timestamp": "2018-11-30T07:31:51.042Z",
            "@version": "1",
            "link_target": "self",
            "idx": 13862,
            "category": 20,
            "display_fl": "y",
            "reply_cnt": 0
        },
        {
            "logo_title": "",
            "notice_fl": "n",
            "mem_type": "",
            "direct_fl": "n",
            "author": "콘텐츠마케팅팀 고아라",
            "mobile_notice_fl": "n",
            "title": "[독점] 인사통 응답하라!! 쿠팡 - 2013년 대졸 신입사원 채용",
            "main_display_fl": "n",
            "mcom_idx": 0,
            "content": "<div style=\"text-align: center;\"><center><a target=\"_blank\" href=\"http://www.saramin.co.kr/recruit/RecruitBbsSearch.php?code=bbs_recruit&amp;mode=view&amp;idx=13521078#insaTop\"><img src=\"http://www.saraminimage.co.kr/event/contents/SunBaeTong/인사통로고.jpg\" /></a></center></div>\r\n<p style=\"text-align: center;\"><strong>쿠팡</strong>에서 2013년 신입사원 채용 공고를 진행 하고 있습니다.<br /><strong>경영기획, 마케팅, 영업, 디자인 외 4개 부문에서 </strong>채용 진행 중 입니다<br />인사담당자님께서 인사통에 친절하고 자세하게 답변해 주셨으니, <br />인사통 참조하시어 좋은 결과 얻어내시길 바랍니다!<br />서류 접수 마감일은 <strong>01</strong><b>월 11일 (일) 자정</b>까지!! 자세한 내용은 하단의 <strong>인사통</strong>을 참고해주세요.</p>\r\n<div></div>\r\n<div class=\"jn-foot\"><a target=\"_blank\" href=\"http://www.saramin.co.kr/recruit/RecruitBbsSearch.php?code=bbs_recruit&amp;method=NL&amp;mode=view&amp;idx=13868278#insaTop\">[쿠팡] <span id=\"recruit-company-title\">2013년 대졸 신입사원 채용</span>~ Click!</a></div>\r\n<div></div>",
            "url": null,
            "read_cnt": 2097,
            "display_dt": "2013-01-03 09:30:00",
            "rec_idx": 0,
            "highlight": {
                "title": "[독점] 인사통 응답하라!! 쿠팡 - 2013년 대졸 신입사원 <b>채용</b>",
                "content": "code=bbs_recruit&amp;method=NL&amp;mode=view&amp;idx=13868278#insaTop\">[쿠팡] <span id=\"recruit-company-title\">2013년 대졸 신입사원 <b>채용</span></b>~ Click!</a></div>\r\n<div></div>"
            },
            "edit_dt": "2013-01-03 09:40:35",
            "reg_dt": "2013-01-03 09:30:34",
            "@timestamp": "2018-11-30T07:31:51.043Z",
            "@version": "1",
            "link_target": "self",
            "idx": 13867,
            "category": 20,
            "display_fl": "y",
            "reply_cnt": 0
        },
        {
            "logo_title": "",
            "notice_fl": "n",
            "mem_type": "",
            "direct_fl": "n",
            "author": "홍보팀",
            "mobile_notice_fl": "n",
            "title": "“사람인, ‘맞춤 취업에 강한' 앱 출시”",
            "main_display_fl": "n",
            "mcom_idx": 0,
            "content": "<center><img height=\"333\" width=\"500\" src=\"http://image.saramin.co.kr/2007/mar/perevent3/saraminapp_pattern.jpg\" /></center>내가 원하는 조건의 채용 정보는 물론, 구직 활동을 하는 패턴에 따라 공고 추천까지 받을 수 있는 앱이 출시돼 눈길을 끌고 있다.<br /><br />온라인 취업포털 사람인(<a href=\"http://www.saramin.co.kr/\">www.saramin.co.kr</a> 대표 이정근)은 원하는 맞춤 채용공고를 빠르게 받아볼 수 있는 ‘맞춤 취업에 강한 사람인’ 앱을 출시했다.<br /><br />‘맞춤 취업에 강한 사람인’ 앱은 타이틀에서 알 수 있듯 내가 원하는 조건에 맞춘 채용공고를 손쉽게 받아볼 수 있는 것이 강점이다. 경력여부, 직종, 업종을 비롯해 지역, 학력 등의 조건을 설정하면 이에 맞는 채용공고를 바로 확인할 수 있다. 뿐만 아니라, 사람인만의 구인·구직 매칭 솔루션을 사용하여 지원할만한 공고를 추천해주기 때문에 본인에게 맞는 채용정보를 찾을 확률이 더욱 높아졌다. <br /><br />또, 공고 목록에서 터치 한 번으로 스크랩이 가능하며 스크랩해 둔 공고는 입사지원 마감일을 놓치지 않도록 하루 전에 알려준다. 여기에 사람인이 업계 최초로 제공한 모바일 이력서 작성 및 수정을 비롯해 입사지원도 이 앱을 통해 바로 할 수 있을 뿐 아니라, 지원한 기업의 인사 담당자가 내 이력서를 열람하면 바로 알려주어 언제 어디서나 간편하게 구직활동을 할 수 있도록 돕는다.<br /><br />사람인은 공채를 준비하는 구직자들에게 최적화된 정보를 제공하는 ‘공채의 명가’ 앱과 채용공고와 인맥을 매칭해 관련 정보를 얻도록 한 ‘거기어때’ 앱으로 많은 사랑을 받아왔다. 이번 앱에서는 검색 기능과 더불어 스마트 솔루션을 통한 공고 추천 서비스가 모바일 앱 최초로 도입돼, 구직자들의 편의성을 한층 높일 전망이다.<br /><br />사람인의 문상헌 그룹장은 “구직자들이 필요로 하는 채용정보를 보다 쉽고 빠르게 제공하기 위하여 맞춤 취업 서비스를 모바일 앱으로 출시하게 되었다. 또, 공고를 하나라도 더 볼 수 있도록 사람인만의 노하우가 담긴 공고 추천 기능을 적용한 만큼, 실제 구직활동에 많은 도움을 얻을 수 있기를 기대한다.”라고 덧붙였다.",
            "url": null,
            "read_cnt": 3325,
            "display_dt": "2013-01-03 10:39:33",
            "rec_idx": 0,
            "highlight": {
                "title": "“사람인, ‘맞춤 취업에 강한' 앱 출시”",
                "content": "<br /><br />‘맞춤 취업에 강한 사람인’ 앱은 타이틀에서 알 수 있듯 내가 원하는 조건에 맞춘 <b>채용</b>공고를 손쉽게 받아볼 수 있는 것이 강점이다. 경력여부, 직종, 업종을 비롯해 지역, 학력 등의 조건을 설정하면 이에 맞는 <b>채용</b>공고를 바로 확인할 수 있다."
            },
            "edit_dt": "0000-00-00 00:00:00",
            "reg_dt": "2013-01-03 10:39:32",
            "@timestamp": "2018-11-30T07:31:51.043Z",
            "@version": "1",
            "link_target": "self",
            "idx": 13868,
            "category": 10,
            "display_fl": "y",
            "reply_cnt": 0
        }
         ..................................
    ]
}
```
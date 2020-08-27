## **1.3 컨텐츠 부분 색인 API**

- 열어보기

    ![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/aa0044a0-a0dd-48ad-8491-a96cf5f1a51f/Untitled.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/aa0044a0-a0dd-48ad-8491-a96cf5f1a51f/Untitled.png)

    ### **1.3.1 Request**

    ### 1.3.1.1 Request Header

    ![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/1cb755ca-26f3-4dad-991c-f3c32d4c4daf/Untitled.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/1cb755ca-26f3-4dad-991c-f3c32d4c4daf/Untitled.png)

    ### 1.3.1.1 Request Parameter

    클라이언트는 아래의 규격에 맞게 parameter를 세팅하여 요청한다.

    ![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a4ab8fd9-f229-4184-b882-53e8424a19e7/Untitled.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a4ab8fd9-f229-4184-b882-53e8424a19e7/Untitled.png)

    ### 1.3.1.2 Request JSON 구조

    ```json
    {
       "service_id": "SVC_QNA",
       "operation_type": "update"
       "contents_id_value": "35"
    }
    ```

    ### **1.3.2 Response**

    ### 1.3.2.1 Response Code

    Normal: Ok (200)

    Error: Bad Request (400), Not Found (404), Internal Server Error (500), Service Unavailable (503)

    ### 1.3.2.2 Response Parameter

    서버는 아래의 규격에 맞게 parameter를 세팅하여 응답한다.

    ![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/71a67592-e8cf-4e34-888f-ea6514ff4331/Untitled.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/71a67592-e8cf-4e34-888f-ea6514ff4331/Untitled.png)
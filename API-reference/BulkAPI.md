## **1.2 컨텐츠 Bulk API**

- 열어보기

    [Untitled](https://www.notion.so/4f5a65ac049c4b1aa0bbe0d7cc692f8e)

    ### **1.2.1 Request**

    ### 1.2.1.1 Request Header

    [Untitled](https://www.notion.so/01660721d50342628ae90bb154bb8a3f)

    ### 1.2.1.1 Request Parameter

    클라이언트는 아래의 규격에 맞게 parameter를 세팅하여 요청한다.

    [Untitled](https://www.notion.so/382c663b3c564773923d67e9926ff895)

    ### 1.2.1.2 Request JSON 구조

    ```json
    {
        "service_id":"SVC_QNA"
    }
    ```

    ### **1.2.2 Response**

    ### 1.2.2.1 Response Code

    Normal: Ok (200)

    Error: Bad Request (400), Not Found (404), Internal Server Error (500), Service Unavailable (503)

    ### 1.2.2.2 Response Parameter

    서버는 아래의 규격에 맞게 parameter를 세팅하여 응답한다.

    ![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/594e0304-8442-492e-a330-6f837db6abab/Untitled.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/594e0304-8442-492e-a330-6f837db6abab/Untitled.png)
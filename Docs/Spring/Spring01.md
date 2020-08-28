# 1.Spring 프로젝트 만들기

🗓 20.08.29

## 1. 실습환경

- 💡 [Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) 11.0.8 LTS
- 💡 [Spinrg boot](https://start.spring.io/) 2.3.3.RELEASE
- 💡 [MySQL](https://dev.mysql.com/) 8.0
- 💡 [Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/7.9/index.html) 7.9.0
- 💡 [Git Bash](https://gitforwindows.org/) 2.27.0
- 💡 [IntelliJ Ultimate](https://www.jetbrains.com/ko-kr/idea/) 2020.2.1
- 💡 [HeidiSQL](https://www.heidisql.com/) 11.0
- 💡 Windows 10

---

### ✍개요

과제로 주어진 검색 엔진을 만들기 위해 `Spring boot`와 `ElasticSearch`를 활용하기로 하였는데 Spring boot로 프로젝트 생성하는 법 부터 차근차근 해보기로 했다.

---

## 2. Spring boot 프로젝트 생성하기

최근 Spring 프로젝트는 대부분 Spring boot로 생성한다.

GUI로 Spring 관련 프로젝트를 생성해주는 Spring [공식 지원 홈페이지](https://start.spring.io/)에 접속해보자.

---

`Project Type`과 `Language`, `Spring boot 버전`을 선택할 수 있도록 제공하고 프로젝트 생성 metadata를 입력할 수 있게 되어있다.

다음과 같이 선택했다.

- Gradle Project
- Java
- 2.3.3
- Jar
- 11 ver
- Dependecies
  - Spring Web
  - Thymeleaf

---

### ✔ Maven vs Gradle

Gradle 버전이 업데이트 되면서 성능이 대폭 개선되어 최근 생성되는 프로젝트는 `Gradle`로 만드는 것이 좋다고 한다.

더 자세한 차이점은 다음에 알아보도록 하자.

### ✔ Thymeleaf

Spring boot 기반에 어떤 라이브러리를 불러올지 정할 수 있는 Dependecy 중 HTML을 만들어주는 `템플릿 엔진`을 `Thymeleaf`로 사용하보려고 한다.

---

Generate 버튼을 통해 다운로드 받아보자.

---

## 3.IntelliJ에서 프로젝트 열기

- IntelliJ에서 `Open or Import`를 선택 
- 다운로드 받은 폴더에 `build.gradle` 파일 선택해서 Open
- 외부 라이브러리 다운로드 기다리기

---

### 📂 Directory structure
``` bash
  |-.gradle                          # 지금은 무시
  |-.idea                            # IntelliJ관련
  |-gradle                           # gradle관련
  |-src                              # 폴더를
  |  |-main                          # Java관련, resources관련
  |  |-test                          # test관련
  |-gitignore                        # github관련
  |-build.gradle                     # Setting 자동화
  ...
```

---

### ✔ 중요한 build.gradle 살펴보기

```gradle
plugins {
  id 'org.springframework.boot' version '2.3.3.RELEASE'
  id 'io.spring.dependency-management' version '1.0.10.RELEASE'
  id 'java'
}
group = 'com.example'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'
repositories {
  mavenCentral()
}
dependencies {
  implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
  testImplementation('org.springframework.boot:spring-boot-starter-test') {
    exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
  }
}
test {
  useJUnitPlatform()
}
```

클릭으로 설정해준대로 gradle 파일이 생성되었다.

자세한 설명은 다음에 적겠다.

---


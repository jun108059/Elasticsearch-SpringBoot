package com.searchengine.yjpark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "YoungJun!");
        return "hello"; // Templates 으로 렌더링
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        // ResponseBody : HTTP body에 data를 직접 넣어주겠다.
        // 페이지 소스보기 해도 html 문법이 안보임
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        // @ResponseBody를 사용하고
        // 객체를 반환하면 객체가 JSON으로 반환
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        // Java Bean 표준 규약
        // Getter Setter - 프로퍼티 접근 방식
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

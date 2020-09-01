package com.searchengine.yjpark.controller;

import com.searchengine.yjpark.domain.DataBaseInfo;
import com.searchengine.yjpark.service.ServiceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ServiceManagementController {

    private final ServiceService serviceService;

    public ServiceManagementController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    // DB 정보 등록하는 page
    @GetMapping("/simple/createDBInfo")
    public String createForm() { return "simple/createDBInfo";}

    @PostMapping("/simple/createDBInfo")
    public String create(DBForm form) {
        DataBaseInfo dbinfo = new DataBaseInfo();
        dbinfo.setHost(form.getHost_name());
        dbinfo.setDbId(form.getUser_id());
        dbinfo.setDbPw(form.getUser_pw());


    }



}

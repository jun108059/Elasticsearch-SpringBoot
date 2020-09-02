package com.searchengine.yjpark.controller;

import com.searchengine.yjpark.domain.DataBaseInfo;
import com.searchengine.yjpark.domain.Service;
import com.searchengine.yjpark.service.ServiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ServiceManagementController {

    private final ServiceService serviceService;

    public ServiceManagementController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    // DB 정보 등록하는 page 랜더링
    @GetMapping("/simple/createDBInfo")
    public String createForm() { return "simple/createDBInfo";}

    // post로 전달받은 data 넣기
    @PostMapping("/simple/createDBInfo")
    public String create(@ModelAttribute DataBaseInfo form) {
        System.out.println(form.getDbConnIp());
        DataBaseInfo dbinfo = new DataBaseInfo();
        dbinfo.setDbConnIp(form.getDbConnIp());
        dbinfo.setDbId(form.getDbId());
        dbinfo.setDbPw(form.getDbPw());

        serviceService.registrationDB(dbinfo);

        return "redirect:/";
    }

    // 서비스 등록하는 page 랜더링
    @GetMapping("/simple/serviceInfo")
    public String createService(Model model) {
        // 저장된 DB 정보 가져오기
        List<DataBaseInfo> dbInfo= serviceService.findAllDBInfo();
        model.addAttribute("dbInfo", dbInfo);
        return "simple/serviceInfo";
    }

    // post로 전달받은 data 넣기
    @PostMapping("/simple/serviceInfo")
    public String create(@ModelAttribute Service form) {
        Service service = new Service();
        service.setServiceId(form.getServiceId());
        service.setServiceDetail(form.getServiceDetail());
        service.setBulkQuery(form.getBulkQuery());
        service.setDbInfo(form.getDbInfo());

        serviceService.registrationService(service);

        // return "redirect:/simple/search/bulk";
        return "redirect:/";
    }

    // Bulk API
    @GetMapping("/simple/search/bulk")
    public String bulkAPI(Model model) {
        // 저장된 DB 정보 가져오기
        List<DataBaseInfo> dbInfo= serviceService.findAllDBInfo();
        model.addAttribute("dbInfo", dbInfo);
        return "simple/serviceInfo";
    }

}

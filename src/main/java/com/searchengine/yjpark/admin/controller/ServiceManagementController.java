package com.searchengine.yjpark.admin.controller;

import com.searchengine.yjpark.domain.DataBaseInfo;
import com.searchengine.yjpark.domain.Service;
import com.searchengine.yjpark.admin.service.ServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ServiceManagementController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());


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
//        System.out.println(form.getDbConnIp());
        DataBaseInfo dbInfo = new DataBaseInfo();
        dbInfo.setDbConnIp(form.getDbConnIp());
        dbInfo.setDbId(form.getDbId());
        dbInfo.setDbPw(form.getDbPw());

        serviceService.registrationDB(dbInfo);

        return "redirect:/";
    }

    // 서비스 등록하는 page 랜더링
    @GetMapping("/simple/serviceInfo")
    public String createService(Model model) {
        // 저장된 DB 정보 가져오기
        List<DataBaseInfo> dbInfo= serviceService.findAllDBInfo();
        log.info("dbInfo : {}", dbInfo.get(1).getIdx());
        model.addAttribute("dbInfo", dbInfo);
        return "simple/serviceInfo";
    }

    // post로 전달받은 data 넣기
    @PostMapping("/simple/serviceInfo")
    public String create(@ModelAttribute Service form) {
        log.info("Request Idx: {}", form.getDbIdx());
        Service service = new Service();
        service.setServiceId(form.getServiceId());
        service.setServiceDetail(form.getServiceDetail());
        service.setBulkQuery(form.getBulkQuery());
        service.setDbIdx(form.getDbIdx());
        service.setIdColumn(form.getIdColumn());

        serviceService.registrationService(service);

        // return "redirect:/simple/search/bulk";
        return "redirect:/simple/serviceList";
    }

    // 서비스 List Page 랜더링
    @GetMapping("/simple/serviceList")
    public String serviceList(Model model) {
        // 저장된 Service 정보 가져오기
        List<Service> services= serviceService.findAllService();
        model.addAttribute("services", services);
        log.info("service Count: {}", services.size());
        return "simple/serviceList";
    }

    // 검색된 서비스만 띄우기
    @PostMapping("/simple/serviceSearch")
    public String getServiceId(@ModelAttribute Service form, Model model) {
        // form id 받기
        String serviceId = form.getServiceId();
        // id 일치하는 service List
        Service services = serviceService.findServiceByID(serviceId);
        model.addAttribute("services", services);
        return "simple/serviceList";
    }
}

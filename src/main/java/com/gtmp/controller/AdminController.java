package com.gtmp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String admin(){
        return "admin/index";
    }

    @ResponseBody
    @RequestMapping(value = "normal",method = RequestMethod.GET)
    public String normal(){
        return "noral";
    }

    @ResponseBody
    @RequestMapping(value = "xd", method = RequestMethod.GET)
    public String xd(){
        return "xd";
    }
}

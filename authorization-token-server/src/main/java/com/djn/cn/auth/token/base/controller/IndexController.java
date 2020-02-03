package com.djn.cn.auth.token.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index/")
public class IndexController extends BaseController{
    @RequestMapping("index")
    public String toIndex(){
        return "index/index";
    }
}

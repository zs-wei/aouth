package com.djn.cn.oauth.provider.ucenter.base.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djn.cn.oauth.provider.ucenter.base.service.IUserInfoService;

/**
 * 
 * @ClassName UserCenterController
 * @Description  用户服务controller
 * @author BigJia-Perfect
 * @date 2018年3月11日 下午9:37:04
 *
 */
@Controller
public class UserCenterAPI {
    @Autowired
    private IUserInfoService iUserInfoService;
    @RequestMapping("/listAll")
    @ResponseBody
    public Object listAll() {
        return "AAAAA";
    }
    
    
    
/********************************************************************************************************************
 *     访问资源实现OAuth2.0认证方案实现技术方案-受保护的资源文件
 *     1、接受到token 与 key 
 *     2、appkey 关联相关的接口-可以对key接口做权限控制 （权限控制全部在授权服务器上面做，访问通过，再访问受保护的资源->
 *     然后执行相关接口；
 *     接口实现可以使用Dubbo实现负载；-> 访问数据库 ->提供高可用数据库
 *     
 *     针对受访问资源的接口如何增加HTTP 请求的高负载 接口的高负载
 *     ）
 *     
 *     
 *     
 ********************************************************************************************************************/
    
    
    @RequestMapping("/getUserInfoById")
    @ResponseBody
    public Object getUserInfoById(String id) {
        return iUserInfoService.findById(id);
    }
    
    
    
    
}

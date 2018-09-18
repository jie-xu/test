package com.xgh.appweb;

import com.xgh.common.Response;
import com.xgh.service.MemberService;
import com.xgh.service.WeiXinApiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by DELL on 2018/9/17.
 */
@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private WeiXinApiService weiXinApiService;

    @RequestMapping(value = "/getOpenId",method = RequestMethod.GET)
    @ResponseBody
    public Response getOpenId(String code){
        if(StringUtils.isEmpty(code)){
            return Response.fail("参数不合法！");
        }
       String openid = weiXinApiService.requestOpenId(code);
        if(StringUtils.isNotEmpty(openid)){
            Long userId = memberService.addNewMember(openid);
            return Response.success("ok",userId);
        }
        return Response.fail("未查询到该用户");
    }
}

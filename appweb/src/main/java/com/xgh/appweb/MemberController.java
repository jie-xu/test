package com.xgh.appweb;

import com.xgh.common.Response;
import com.xgh.common.utils.HttpWebUtils;
import com.xgh.domain.entity.MemberEntity;
import com.xgh.domain.entity.WalletEntity;
import com.xgh.domain.vo.UserInfoVO;
import com.xgh.service.MemberService;
import com.xgh.service.WalletService;
import com.xgh.service.WeiXinApiService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Created by DELL on 2018/9/17.
 */
@Controller
@RequestMapping("/member")
public class MemberController {
    private static final Logger log = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private MemberService memberService;

    @Autowired
    private WeiXinApiService weiXinApiService;

    @Autowired
    private WalletService walletService;

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

    /**
     * 获取用户信息
     */
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    @ResponseBody
    public Response getUserInfo(@RequestBody Map<String,Object> params){
        try {
            String userId = params.get("userId").toString();
            Object nk = params.get("nickName");
            String nickName = null;
            if(nk != null){
                nickName = nk.toString();
            }
          MemberEntity m = memberService.getById(Long.valueOf(userId));
          if(m == null){
              return Response.fail("参数不合法");
          }
          if(StringUtils.isEmpty(m.getNickName()) && StringUtils.isNotEmpty(nickName)){
              MemberEntity m2 = new MemberEntity();
              m2.setNickName(nickName);
              m2.setId(m.getId());
              m2.setUpdatedTime(new Date());
              memberService.update(m2);
          }
            WalletEntity w = walletService.getPointAndAvaAmt(m.getId());
            UserInfoVO u = new UserInfoVO();
            u.setAvaAmt(w.getAvailableAmt());
            u.setRole(m.getRole());
            u.setPoint(w.getPoint());
            return Response.successResponse(u);
        }catch (Exception e){
            log.error("-----getUserInfo error",e);
            return Response.fail(e.getMessage());
        }

    }
}

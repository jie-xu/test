package com.xgh.service;

import com.alibaba.fastjson.JSONObject;
import com.xgh.common.utils.HttpClientUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by DELL on 2018/9/17.
 */
@Service
public class WeiXinApiService {
    private static Logger log = LoggerFactory.getLogger(WeiXinApiService.class);

    private String appid = "wxe01c15ee3964c3dc";
    private String appSecret = "e6124e5e8a37601d216304a397a2a06b";
    private String openid = "oNy6H5Jd-XEMEsbYvydxEwdBpJ3Q";

    public String requestOpenId(String code){
        return openid;
//        try {
//            StringBuilder sb = new StringBuilder();
//            sb.append("https://api.weixin.qq.com/sns/jscode2session?");
//            sb.append("appid=").append(appid).append("&");
//            sb.append("secret=").append(appSecret).append("&");
//            sb.append("js_code=").append(code).append("&");
//            sb.append("grant_type=authorization_code");
//            String url = sb.toString();
//           String res =  HttpClientUtils.getMethodGetContent(url);
//           if(StringUtils.isEmpty(res)){
//                return null;
//           }
//           log.info("--------[weixin] get openid response :{}",res);
//           JSONObject jsonObject = JSONObject.parseObject(res);
//           return jsonObject.get("openid").toString();
//
//        }catch (Exception e){
//            log.error("----[weixin] get openid error",e);
//            return null;
//        }
    }
}

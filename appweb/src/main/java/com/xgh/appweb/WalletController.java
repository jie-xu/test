package com.xgh.appweb;

import com.google.common.collect.Maps;
import com.xgh.common.Response;
import com.xgh.common.utils.QRCodeUtils;
import com.xgh.domain.em.AmountEnum;
import com.xgh.domain.entity.WalletEntity;
import com.xgh.domain.vo.AmtTypeVO;
import com.xgh.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by DELL on 2018/9/18.
 */
@Controller
@RequestMapping("/wallet")
public class WalletController {
    private static final Logger log = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private DepositService depositService;

    @Autowired
    private ConsumeService consumeService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private WalletService walletService;

    /**
     * 查询充值金额枚举
     */
    @RequestMapping(value = "/listAmountTypes",method = RequestMethod.GET)
    @ResponseBody
    public Response listAmountEnum(){
        AmountEnum[] amountEnums = AmountEnum.values();
        List result = new ArrayList();
        for(AmountEnum e : amountEnums){
            result.add(new AmtTypeVO(e.name(),e.getAmt(),e.getPresentAmt()));
        }
        return Response.successResponse(result);
    }


    /**
     * 新建充值记录
     */
    @RequestMapping(value = "/newdeposit",method = RequestMethod.POST)
    @ResponseBody
    public Response newdeposit(@RequestBody Map<String,Object> params){
        try {
            String userId = params.get("userId").toString();
            String amtType = params.get("amtType").toString();

            if(StringUtils.isEmpty(amtType) || amtType.split(",").length > 1){
                return Response.fail("请选择一项充值的额度！");
            }
            if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(amtType)){
                return Response.fail("参数不合法！");
            }
            memberService.hasUser(Long.valueOf(userId));
            //检查用户是否存在
         AmountEnum ae = AmountEnum.getEnum(amtType);
         if(ae != null){
             depositService.add(Long.valueOf(userId),ae);
             return Response.success("充值成功！！！");
         }else{
             return Response.fail("参数错误！");
         }

        }catch (Exception e){
            log.error("-----newdeposit error",e);
            return Response.fail(e.getMessage());
        }

    }

    /**
     * 充值成功，更新记录
     */

    /**
     * 展示消费二维码
     */
    @RequestMapping(value = "/getQRCodeImage",method = RequestMethod.POST)
    @ResponseBody
    public Response getQRCodeImage(@RequestBody Map<String,Object> params){
        try {
            String userId = params.get("userId").toString();
//            String content = "http://10.141.20.175:8080/xghweb/wallet/deduct?userId=" +  userId;
//            //检查用户是否存在
//            memberService.hasUser(Long.valueOf(userId));
//            QRCodeUtils.createQRCode(content,"E:\\qr\\deduct_" + userId + ".png");
//        return imageService.getQRCodeImage(content,userId);
            Map<String,Object> resultMap = Maps.newHashMap();
            WalletEntity w = walletService.getPointAndAvaAmt(Long.valueOf(userId));
            resultMap.put("qrcodeImage","http://10.141.20.175:8080/xghweb/WebContent/img/deduct_1.png");
            resultMap.put("point",w.getPoint());
            resultMap.put("avaAmt",w.getAvailableAmt());
            return Response.success("ok",resultMap);
        }catch (Exception e){
            log.error("-----getQRCodeImage error",e);
            return Response.fail(e.getMessage());
        }
    }

    /**
     * 扫码消费扣款操作
     */
    @RequestMapping(value = "/deduct",method = RequestMethod.GET)
    @ResponseBody
    public Response deduct(HttpServletRequest request){
        try {
            String userId = request.getParameter("userId").toString();
            String amtS = request.getParameter("amt").toString();
            Object pointObj = request.getParameter("consumePoint");

            BigDecimal consumePoint = BigDecimal.ZERO;
            BigDecimal amt = new BigDecimal(amtS);
            if(pointObj != null ){
                if(StringUtils.isNotEmpty(pointObj.toString())){
                    consumePoint = new BigDecimal(pointObj.toString());
                }
            }
            //检查用户是否存在
            Long userIdL = Long.valueOf(userId);
            memberService.hasUser(userIdL);

            consumeService.add(userIdL,amt,consumePoint);
        }catch (Exception e){
            log.error("-----getQRCodeImage error",e);
            return Response.fail(e.getMessage());
        }
        return Response.success("扣款操作成功！");

    }


}

package com.xgh.appweb;

import com.xgh.common.Response;
import com.xgh.common.utils.QRCodeUtils;
import com.xgh.domain.em.AmountEnum;
import com.xgh.domain.vo.AmtTypeVO;
import com.xgh.service.ConsumeService;
import com.xgh.service.DepositService;
import com.xgh.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
            String amtType = ((List)params.get("amtType")).get(0).toString();
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
//            String userId = params.get("userId").toString();
//            String content = "http://10.141.20.175:8080/xghweb/wallet/deduct?userId=" +  userId;
//            //检查用户是否存在
//            memberService.hasUser(Long.valueOf(userId));
//            QRCodeUtils.createQRCode(content,"E:\\qr\\deduct_" + userId + ".png");
        return Response.success("ok","http://10.141.20.175:8080/xghweb/WebContent/img/deduct_1.png");
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
            String amt = request.getParameter("amt").toString();
            //检查用户是否存在
            Long userIdL = Long.valueOf(userId);
            memberService.hasUser(userIdL);

            consumeService.add(userIdL,amt);
        }catch (Exception e){
            log.error("-----getQRCodeImage error",e);
            return Response.fail(e.getMessage());
        }
        return Response.success("扣款操作成功！");

    }


}

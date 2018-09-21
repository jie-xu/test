package com.xgh.service;

import com.xgh.dao.ConsumeEntityMapper;
import com.xgh.dao.WalletEntityMapper;
import com.xgh.domain.em.DepositStatusEnum;
import com.xgh.domain.entity.ConsumeEntity;
import com.xgh.domain.entity.WalletEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by DELL on 2018/9/17.
 */
@Service
@Transactional(rollbackFor = { Exception.class ,RuntimeException.class})
public class ConsumeService {

    @Autowired
    private ConsumeEntityMapper consumeEntityMapper;

    @Autowired
    private WalletEntityMapper walletEntityMapper;

    /**
     * 添加消费记录
     */
    public void add(Long userId,BigDecimal amt,BigDecimal consumePoint) throws Exception{
        WalletEntity wallet = walletEntityMapper.selectByUserId(userId);
        //检查可用额度
        if(wallet == null){
            throw new Exception("您的账户余额不足，请尽快充值！");
        }
        if(amt.compareTo(BigDecimal.ZERO) <= 0){
            throw new Exception("输入金额不合法！");
        }
        //检查可用积分
        if(consumePoint.compareTo(BigDecimal.ZERO) < 0){
            throw new Exception("输入积分不合法！");
        }

        BigDecimal av = wallet.getAvailableAmt();
        BigDecimal existPoint = wallet.getPoint();
        BigDecimal val = amt;
        if(av.compareTo(val) < 0){
            throw new Exception("您的账户余额不足，请尽快充值！");
        }
        if(existPoint.compareTo(consumePoint) < 0){
            throw new Exception("您的积分余额不足！");
        }
        ConsumeEntity ce = new ConsumeEntity();
        Date n = new Date();
        ce.setCreatedTime(n);
        ce.setUpdatedTime(n);
        ce.setUserId(userId);
        ce.setAmt(val);
        ce.setStatus(DepositStatusEnum.SUCCESS.getCode());
        ce.setSuccessTime(n);
        ce.setPoint(consumePoint);
        consumeEntityMapper.insertSelective(ce);

        //扣除wallet表里用户额度,增加积分
        wallet.setUpdatedTime(n);
        BigDecimal aa = av.subtract(val);
        BigDecimal newpoint = existPoint.add(val.divide(new BigDecimal(10),2,BigDecimal.ROUND_HALF_UP));
        wallet.setAvailableAmt(aa);
        wallet.setPoint(newpoint.subtract(consumePoint));
        walletEntityMapper.updateByPrimaryKeySelective(wallet);
    }
}

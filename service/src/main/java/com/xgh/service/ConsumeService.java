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
    public void add(Long userId,String amt) throws Exception{
        WalletEntity wallet = walletEntityMapper.selectByUserId(userId);
        //检查可用额度
        if(wallet == null){
            throw new Exception("您的账户余额不足，请尽快充值！");
        }
        BigDecimal av = wallet.getAvailableAmt();
        BigDecimal val = new BigDecimal(amt);
        if(av.compareTo(val) < 0){
            throw new Exception("您的账户余额不足，请尽快充值！");
        }

        ConsumeEntity ce = new ConsumeEntity();
        Date n = new Date();
        ce.setCreatedTime(n);
        ce.setUpdatedTime(n);
        ce.setUserId(userId);
        ce.setAmt(val);
        ce.setStatus(DepositStatusEnum.SUCCESS.getCode());
        ce.setSuccessTime(n);
        consumeEntityMapper.insertSelective(ce);

        //扣除wallet表里用户额度
        wallet.setUpdatedTime(n);
        BigDecimal aa = av.subtract(val);
        wallet.setAvailableAmt(aa);
        walletEntityMapper.updateByPrimaryKeySelective(wallet);
    }
}

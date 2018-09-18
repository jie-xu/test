package com.xgh.service;

import com.xgh.dao.DepositEntityMapper;
import com.xgh.dao.WalletEntityMapper;
import com.xgh.domain.em.AmountEnum;
import com.xgh.domain.em.DepositStatusEnum;
import com.xgh.domain.entity.DepositEntity;
import com.xgh.domain.entity.WalletEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by DELL on 2018/9/17.
 */
@Service
@Transactional(rollbackFor = { Exception.class ,RuntimeException.class})
public class DepositService {

    @Autowired
    private DepositEntityMapper depositEntityMapper;
    @Autowired
    private WalletEntityMapper walletEntityMapper;


    public void add(Long userId, AmountEnum amountEnum){
        DepositEntity de = new DepositEntity();
        de.setAmt(new BigDecimal(amountEnum.getAmt()));
        de.setPresentAmt(new BigDecimal(amountEnum.getPresentAmt()));
        de.setUserId(userId);
        Date now = new Date();
        de.setCreatedTime(now);
        de.setUpdatedTime(now);
        de.setStatus(DepositStatusEnum.NEW.getCode());

        depositEntityMapper.insertSelective(de);

        //增加wallet表里用户额度
        BigDecimal totalAmt = de.getAmt().add(de.getPresentAmt());
        WalletEntity wallet = walletEntityMapper.selectByUserId(userId);
        if(wallet == null){
            //添加wallet记录
            WalletEntity newWallet = new WalletEntity();
            newWallet.setAvailableAmt(totalAmt);
            newWallet.setTotalAmt(totalAmt);
            newWallet.setUpdatedTime(now);
            newWallet.setCreatedTime(now);
            newWallet.setUserId(userId);
            walletEntityMapper.insertSelective(newWallet);
        }else{
           wallet.setUpdatedTime(now);
           BigDecimal ta = wallet.getTotalAmt().add(totalAmt);
           BigDecimal aa = wallet.getAvailableAmt().add(totalAmt);
           wallet.setTotalAmt(ta);
           wallet.setAvailableAmt(aa);
            walletEntityMapper.updateByPrimaryKeySelective(wallet);
        }

    }
}

package com.xgh.service;

import com.xgh.dao.WalletEntityMapper;
import com.xgh.domain.entity.WalletEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by DELL on 2018/9/17.
 */
@Service
@Transactional(rollbackFor = { Exception.class ,RuntimeException.class})
public class WalletService {

    @Autowired
    private WalletEntityMapper walletEntityMapper;

    public WalletEntity getByUserId(Long userId){
        WalletEntity wallet = walletEntityMapper.selectByUserId(userId);
        return wallet;
    }

    public BigDecimal getAvailableAmt(Long userId){
        WalletEntity we = getByUserId(userId);
        if(we == null){
            return BigDecimal.ZERO;
        }else{
            return we.getAvailableAmt();
        }

    }

    public WalletEntity getPointAndAvaAmt(Long userId){
        WalletEntity we = getByUserId(userId);
        if(we == null){
            WalletEntity w = new WalletEntity();
            w.setAvailableAmt(BigDecimal.ZERO);
            w.setPoint(BigDecimal.ZERO);
            return w;
        }
        return we;
    }
}

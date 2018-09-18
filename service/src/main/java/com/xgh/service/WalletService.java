package com.xgh.service;

import com.xgh.dao.WalletEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by DELL on 2018/9/17.
 */
@Service
@Transactional(rollbackFor = { Exception.class ,RuntimeException.class})
public class WalletService {

    @Autowired
    private WalletEntityMapper walletEntityMapper;
}

package com.xgh.dao;

import com.xgh.domain.entity.WalletEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by DELL on 2018/9/17.
 */
@Mapper
public interface WalletEntityMapper extends GenericMapper<WalletEntity,Long> {

    WalletEntity selectByUserId(@Param("userId") Long userId);

}

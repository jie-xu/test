package com.xgh.dao;

import com.xgh.domain.entity.MemberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by DELL on 2018/9/17.
 */
@Mapper
public interface MemberEntityMapper extends GenericMapper<MemberEntity,Long> {

    MemberEntity seletByOpenId(@Param("openid") String openid);
}

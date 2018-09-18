package com.xgh.service;

import com.xgh.dao.MemberEntityMapper;
import com.xgh.domain.entity.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by DELL on 2018/9/17.
 */
@Service
@Transactional(rollbackFor = { Exception.class ,RuntimeException.class})
public class MemberService {

    @Autowired
    private MemberEntityMapper memberEntityMapper;


    /**
     * 添加新用户
     */
    public Long addNewMember(String openid){
      MemberEntity member = memberEntityMapper.seletByOpenId(openid);
      if(member != null){
        return member.getId();
      }else{
          MemberEntity n = new MemberEntity();
          n.setOpenId(openid);
          memberEntityMapper.insertSelective(n);
          return n.getId();
      }
    }

    public boolean hasUser(Long userId) throws Exception{
       MemberEntity member = memberEntityMapper.selectByPrimaryKey(userId);
       if(member == null){
           throw new Exception("该用户不存在！！！");
       }
       return true;
    }
}



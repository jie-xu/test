package com.xgh.domain.vo;

import java.math.BigDecimal;

/**
 * Created by DELL on 2018/9/19.
 */
public class UserInfoVO {
    private BigDecimal avaAmt;
    private String role;
    private BigDecimal point;

    public BigDecimal getAvaAmt() {
        return avaAmt;
    }

    public void setAvaAmt(BigDecimal avaAmt) {
        this.avaAmt = avaAmt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }
}


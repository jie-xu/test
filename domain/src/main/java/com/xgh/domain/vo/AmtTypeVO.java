package com.xgh.domain.vo;

/**
 * Created by DELL on 2018/9/18.
 */
public class AmtTypeVO {
    private String name;
    private Integer amt;//充值金额
    private Integer presentAmt;//赠送金额

    public AmtTypeVO(String name,Integer amt,Integer presentAmt){
        this.name = name;
        this.amt = amt;
        this.presentAmt = presentAmt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmt() {
        return amt;
    }

    public void setAmt(Integer amt) {
        this.amt = amt;
    }

    public Integer getPresentAmt() {
        return presentAmt;
    }

    public void setPresentAmt(Integer presentAmt) {
        this.presentAmt = presentAmt;
    }
}

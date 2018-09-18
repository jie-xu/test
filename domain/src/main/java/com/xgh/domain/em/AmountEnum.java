package com.xgh.domain.em;

/**
 * Created by DELL on 2018/9/18.
 */
public enum AmountEnum {
    AM_200(200,13),
    AM_300(300,25),
    ;

    AmountEnum(Integer amt,Integer presentAmt){
        this.amt = amt;
        this.presentAmt = presentAmt;
    }
    private Integer amt;//充值金额
    private Integer presentAmt;//赠送金额

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

    public static AmountEnum getEnum(String name){
        for(AmountEnum e : values()){
            if(e.name().equalsIgnoreCase(name)){
                return e;
            }
        }
        return null;
    }
}

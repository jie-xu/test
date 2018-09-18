package com.xgh.domain.em;

/**
 * Created by DELL on 2018/9/17.
 */
public enum DeleteEnum {
    YES("Y"), //删除
    NO("N"),//未删除
    ;
    private String i;

     DeleteEnum(String i){
        this.i = i;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }
}

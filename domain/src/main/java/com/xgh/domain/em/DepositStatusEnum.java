package com.xgh.domain.em;

/**
 * Created by DELL on 2018/9/17.
 */
public enum DepositStatusEnum {
    NEW(0),
    SUCCESS(1),
    FAIL(2),
    ;

    private Integer code;

    DepositStatusEnum(Integer code){
        this.code = code;
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

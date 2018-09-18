package com.xgh.common;

/**
 * Created by DELL on 2018/9/13.
 */
public class BaseConstants {
    public abstract class CommonCode{
        private CommonCode(){

        }
        public static final String FAILED_CODE = "0";
        public static final String SUCCESS_CODE = "1";

    }
    // 通用的消息
    public abstract class CommonMessage {
        private CommonMessage() {
        }

        public static final String FAILED_MESSAGE = "获取数据失败!"; // 获取数据失败
        public static final String SUCCESS_MESSAGE = "请求数据成功!"; // 获取数据失败
        public static final String ERROR_MESSAGE = "请求数据出错!!"; // 获取数据出错!
        public static final String PARAM_ERROR_MESSAGE = "请求参数传递错误!!"; // 参数传递错误
        public static final String COMMON_FAIL_MSG = "网络异常, 请稍后再试";

    }


}

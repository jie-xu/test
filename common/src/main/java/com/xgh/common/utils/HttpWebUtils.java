package com.xgh.common.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by DELL on 2018/9/19.
 */
public class HttpWebUtils {
    public static final String getValue(HttpServletRequest request, String param) {
        return getValue(request, param, "");
    }

    public static final String getValue(HttpServletRequest request, String param, String def) {
        if(request != null && !StringUtils.isBlank(param)) {
            Object paramValue = request.getParameter(param);
            return paramValue == null?def:String.valueOf(paramValue).trim();
        } else {
            return def;
        }
    }
}

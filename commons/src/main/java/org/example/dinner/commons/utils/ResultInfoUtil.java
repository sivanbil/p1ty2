package org.example.dinner.commons.utils;

import io.swagger.annotations.Api;
import org.example.dinner.commons.constant.ApiConstant;
import org.example.dinner.commons.model.domain.ResultInfo;

public class ResultInfoUtil {

    public static <T>ResultInfo<T> buildError(String path) {
        ResultInfo<T> resultInfo = build(ApiConstant.ERROR_CODE,
                ApiConstant.ERROR_MESSAGE, path, null);

        return resultInfo;
    }


    public static <T>ResultInfo<T> buildError(int errorCode, String message, String path, T data) {
        ResultInfo<T> resultInfo = build(errorCode, message, path, data);

        return resultInfo;
    }

    public static <T>ResultInfo<T> buildSuccess(String path) {
        ResultInfo<T> resultInfo = build(ApiConstant.SUCCESS_CODE, ApiConstant.SUCCESS_MESSAGE, path, null);

        return resultInfo;
    }

    public static <T>ResultInfo<T> buildSuccess(String path, T data) {
        ResultInfo<T> resultInfo = build(ApiConstant.SUCCESS_CODE, ApiConstant.SUCCESS_MESSAGE, path, data);

        return resultInfo;
    }


    public static <T>ResultInfo<T> build(Integer code, String message, String path, T data) {
        if (code == null) {
            code = ApiConstant.SUCCESS_CODE;
        }

        if (message == null) {
            message = ApiConstant.SUCCESS_MESSAGE;
        }

        ResultInfo resultInfo = new ResultInfo();

        resultInfo.setCode(code);

        resultInfo.setPath(path);

        resultInfo.setData(data);

        return resultInfo;
    }
}

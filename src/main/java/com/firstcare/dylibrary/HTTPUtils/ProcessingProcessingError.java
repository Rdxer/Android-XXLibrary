package com.firstcare.dylibrary.HTTPUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 处理异常时发生异常
 * Created by LXF on 16/6/3.
 */
public class ProcessingProcessingError extends ProcessingError {

    private ProcessingError processingError;

    public ProcessingError getProcessingError() {
        return processingError;
    }

    public void setProcessingError(ProcessingError processingError) {
        this.processingError = processingError;
    }

    public ProcessingProcessingError(JSONObject response, ProcessingError processingError) {
        super("处理异常时发生异常",response);
        this.processingError = processingError;
    }

    public ProcessingProcessingError(String detailMessage, JSONObject response, ProcessingError processingError) {
        super(detailMessage, response);
        this.processingError = processingError;
    }
}

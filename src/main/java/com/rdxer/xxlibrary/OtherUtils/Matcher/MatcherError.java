package com.rdxer.xxlibrary.OtherUtils.Matcher;

/**
 * Created by LXF on 16/6/23.
 */
public class MatcherError extends Exception {

    /** 匹配的正则 */
    private String[] patterns;
    /** 匹配不合适的值 */
    private Object value;
    /**
     * 错误码:
     * 0:值为空
     * 1:匹配不符合
     */
    private int errorCode;

    public MatcherError(String message, int errorCode,String[] patterns, Object value) {
        super(message);
        this.patterns = patterns;
        this.value = value;
        this.errorCode = errorCode;
    }

    public String[] getPatterns() {
        return patterns;
    }

    public void setPatterns(String[] patterns) {
        this.patterns = patterns;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}

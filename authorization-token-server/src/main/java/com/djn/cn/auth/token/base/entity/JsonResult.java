package com.djn.cn.auth.token.base.entity;
/**
 * 
 * @ClassName JsonResult
 * @Description  通用返回数据
 * @author BigJia-Perfect
 * @date 2018年3月11日 上午11:23:07
 *
 */
public class JsonResult {
    /**结果集*/
    private Object results;
    /**状态200、400*/
    private Integer code;
    /** 描述*/   
    private String description;
    public JsonResult() {
        super();
    }
    public Object getResults() {
        return results;
    }
    public void setResults(Object results) {
        this.results = results;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "JsonResult [results=" + results + ", code=" + code + ", description=" + description + "]";
    }
}

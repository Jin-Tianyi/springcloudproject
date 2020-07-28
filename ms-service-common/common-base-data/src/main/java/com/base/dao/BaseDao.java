package com.base.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author :jty
 * @date :20-7-28
 * @description :
 */
@Getter
@Setter
public class BaseDao implements Serializable {
    /**
     * 搜索值
     */
    private String searchValue;

    /**
     * 状态 0锁定 1正常
     */
    private String status;

    /**
     * 删除标志 0删除 1正常
     */
    private String delFlag;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    private String beginTime;

    private String endTime;

    /**
     * 请求参数
     */
    private Map<String, Object> params;
}

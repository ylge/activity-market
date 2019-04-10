package com.geyl.bean;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author geyl
 * @date 2018-6-7 17:45
 */
public class PageResult<T> implements Serializable {
    public Long total;      //总记录数
    public List<T> rows;  //数据列表

    public PageResult(PageInfo<T> tPageInfo) {
        this.total = tPageInfo.getTotal();
        this.rows = tPageInfo.getList();
    }

    public PageResult() {
        this.rows = Collections.emptyList();
        this.total = 0L;
    }
}

package com.geyl.bean;

import lombok.Data;

/**
 * @author geyl
 * @date 2018-6-8 12:34
 */
@Data
public class PageRequest {
    private Integer offset;
    private Integer limit;
    private String order;
    private String  sort;
}

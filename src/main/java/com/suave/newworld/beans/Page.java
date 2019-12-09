package com.suave.newworld.beans;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 分页类
 * @author Suave
 */
@Data
@Accessors(chain = true)
public class Page<T> {
    /**
     * 当前页码
     */
    private int page = 1;
    /**
     * 每页数量
     */
    private int size = 10;
    /**
     * 总的结果数量，一共有多少条数据
     */
    private int total;
    /**
     * 数据列表
     */
    private List<T> rows;
}

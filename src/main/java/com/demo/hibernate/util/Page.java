package com.demo.hibernate.util;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Page {

    // 页面大小
    private int[] pageSizeList = { 10, 25, 50, 100, 200, 300, 500 };

    // 一页显示的记录数
    private int pageSize = 25;

    // 当前页码
    private int pageNo = 1;

    // 记录总数
    private int rowCount = 0;

    // 总页数
    private int pageCount = 1;

    // 起始行数
    private int startIndex = 1;

    // 结束行数
    private int endIndex = 1;

    private int firstPageNo = 1;
    private int prePageNo = 1;
    private int nextPageNo = 1;
    private int lastPageNo = 1;

    // 结果集存放List
    private List<?> resultList;

    public Page(int pageSize, int pageNo, int rowCount, List<?> resultList) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.rowCount = rowCount;
        this.resultList = resultList;

        if (rowCount % pageSize == 0) {
            this.pageCount = rowCount / pageSize;
        } else {
            this.pageCount = rowCount / pageSize + 1;
        }
        this.startIndex = pageSize * (pageNo - 1);
        this.endIndex = this.startIndex + resultList.size();

        this.lastPageNo = this.pageCount;
        if (this.pageNo > 1) this.prePageNo = this.pageNo -1;
        if (this.pageNo == this.lastPageNo){
            this.nextPageNo = this.lastPageNo;
        } else {
            this.nextPageNo = this.pageNo + 1;
        }
    }

    public Object[] getPageSizeIndexs() {
        List<String> result = new ArrayList<String>(pageSizeList.length);
        for (int i = 0; i < pageSizeList.length; i++) {
            result.add(String.valueOf(pageSizeList[i]));
        }
        Object[] indexs = (result.toArray());
        return indexs;
    }

    public Object[] getPageNoIndexs() {
        List<String> result = new ArrayList<String>(pageCount);
        for (int i = 0; i < pageCount; i++) {
            result.add(String.valueOf(i + 1));
        }
        Object[] indexs = (result.toArray());
        return indexs;
    }
}

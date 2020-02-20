package com.peng.blog.utils;

public class MyPage {
    private int page;
    private int pageSize=PageDefinition.CLIENT_TAG_PAGE_SIZE;
    private int totalPages;
    private int totalElements;
    private boolean isFirst;
    private boolean isLast;
    private int startIndex;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
        //set完totalElements后，就要计算其他值
        int currentPage=page+1;//当前页号
        //计算totalPages。除不尽就加一页
        this.totalPages=totalElements/pageSize+(totalElements%pageSize==0 ? 0 : 1);
        if(totalPages<=1){
            isFirst=true;
            isLast=true;
            return;
        }
        if(currentPage==totalPages){//末页
            isFirst=false;
            isLast=true;
        }else if(currentPage==1){//首页
            isFirst=true;
            isLast=false;
        }else{
            isFirst=false;
            isLast=false;
        }
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public int getStartIndex() {//计算出正确startIndex
        this.startIndex=page*pageSize;
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    @Override
    public String toString() {
        return "MyPage{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                ", isFirst=" + isFirst +
                ", isLast=" + isLast +
                ", startIndex=" + startIndex +
                '}';
    }
}

package com.education.resources.bean.from;

public class SearchForm {

    public String keyword;

    public Integer basicTypeId;

    public String tag;

    public String orderBy;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getBasicTypeId() {
        return basicTypeId;
    }

    public void setBasicTypeId(Integer basicTypeId) {
        this.basicTypeId = basicTypeId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}

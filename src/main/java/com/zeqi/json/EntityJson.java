package com.zeqi.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zeqi.database.Article;

import java.util.List;

/**
 * Created by Max on 2016/12/5.
 */

public class EntityJson<T> {

    private List<T> entityList;
    private int page;


    public List<T> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<T> entityList) {
        this.entityList = entityList;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}

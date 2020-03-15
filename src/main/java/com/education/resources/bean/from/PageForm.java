package com.education.resources.bean.from;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageForm {

    private int page = 0;

    private  int size = 20;

    private String sort;

    private String sortDirection;

    private Long startTime;

    private Long endTime;

    private String times;

    public PageForm(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public PageRequest pageRequest(){

        return pageRequest("createTime");
    }

    public PageRequest  pageRequest(String sortProperty){
        return PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,sortProperty));
    }
}

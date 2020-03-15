package com.education.resources.bean.from;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimestampFilterItem {
    private String name;
    private Long start;
    private Long end;
}

package com.education.resources.util.export;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExcelDataBuilder {
    List<Object> head = null;

    public ExcelDataBuilder() {
        head = new ArrayList<>();
    }

    public ExcelDataBuilder add(Object... name) {
        head.addAll(Arrays.asList(name));
        return this;
    }

    public List<Object> build() {
        return this.head;
    }
}

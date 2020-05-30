package com.education.resources.util.export;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExcelHeaderBuilder {
    List<List<String>> head = null;

    public ExcelHeaderBuilder() {
        head = new ArrayList<>();
    }

    public ExcelHeaderBuilder add(String... name) {
        for (String name1 : name) {
            head.add(Collections.singletonList(name1));
        }
        return this;
    }

    public List<List<String>> build() {
        return this.head;
    }
}

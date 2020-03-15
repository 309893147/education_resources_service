package com.education.resources.bean.entity.meta;


import com.education.resources.annotation.*;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class MetaData {

    private String key;

    private String name;

    private boolean  searchable;

    private boolean  displayInList;

    private boolean  displayName;

    private DeleteType delete;

    private EditType edit;

    private PrintType print;

    private ExportType export;

    private boolean  insertable;

    private String deleteKey;

    private List<MetaData> subs;

    private List<SearchOption> searchOption;

    private ContentType type;

    private int sort;

    private boolean  sortable;

    private boolean clickable;


    @Data
    @Builder
    public static class SearchOption{
        private String key;
        private String name;
    }

}

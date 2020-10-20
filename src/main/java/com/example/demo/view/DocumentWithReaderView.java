package com.example.demo.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.MappingParameter;
import com.example.demo.entity.Document;

@EntityView(Document.class)
public interface DocumentWithReaderView extends DocumentView {
    @MappingParameter("reader")
    String getReader();
}

package com.example.demo.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.example.demo.entity.Document;

@EntityView(Document.class)
public interface DocumentView {
    @IdMapping
    Long getId();
    String getTitle();
}

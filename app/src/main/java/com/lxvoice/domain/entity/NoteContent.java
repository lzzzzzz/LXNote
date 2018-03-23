package com.lxvoice.domain.entity;

import java.util.List;

/**
 * Created by psp on 2018/3/15.
 */

public class NoteContent {
    /**文本内容*/
    private String text;

    private List<TaskSpan> spans;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<TaskSpan> getSpans() {
        return spans;
    }

    public void setSpans(List<TaskSpan> spans) {
        this.spans = spans;
    }
}

package com.lxvoice.domain.entity;

/**
 * Created by psp on 2018/3/15.
 */

public class NoteSpan {

    private int type;

    private String url;

    private String thunm;

    private long size;

    private int start;

    private int end;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThunm() {
        return thunm;
    }

    public void setThunm(String thunm) {
        this.thunm = thunm;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}

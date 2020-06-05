package com.shakespace.template.copy;

import java.time.LocalDateTime;

public class BhacPost {


    private Integer id;

    private String title;
    private String content;

    private Integer type;

    private Integer aid;

    private Integer tid;

    private Integer postedBy;

    private Integer rate;

    private String lastEdited;


    public Integer getAid ()
    {
        return aid;
    }

    public void setAid (Integer aid)
    {
        this.aid = aid;
    }

    public Integer getTid ()
    {
        return tid;
    }

    public void setTid (Integer tid)
    {
        this.tid = tid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public Integer getPostedBy() {
        return postedBy;
    }
    public void setPostedBy(Integer postedBy) {
        this.postedBy = postedBy;
    }


    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }


    public Integer getRate() {
        return rate;
    }
    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getLastEdited() {
        return lastEdited;
    }
    public void setLastEdited(String lastEdited) {
        this.lastEdited = lastEdited;
    }




}

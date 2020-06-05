package com.shakespace.template.copy;

import com.alibaba.fastjson.annotation.JSONField;

import java.time.LocalDateTime;

public class BhacComment {

    private Integer id;

    private Integer seqNum;

    private Integer postedBy;

    private Integer parentId;

    private String content;

    private String date;

    private Integer pid;

    private BhacPost post;

    private BhacUser poster;

    public BhacUser getPoster ()
    {
        return poster;
    }

    public void setPoster (BhacUser poster)
    {
        this.poster = poster;
    }

    public BhacPost getPost ()
    {
        return post;
    }

    public void setPost (BhacPost post)
    {
        this.post = post;
    }

    public Integer getPid ()
    {
        return pid;
    }

    public void setPid (Integer pid)
    {
        this.pid = pid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }

    public Integer getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(Integer postedBy) {
        this.postedBy = postedBy;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

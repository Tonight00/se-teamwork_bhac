package cn.edu.buaa.se.bhac.Dao.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangqichang
 * @since 2020-04-30
 */
@TableName(value = "bhac_post",resultMap = "BhacPostMap")
public class BhacPost extends Model<BhacPost> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    @TableField("postedBy")
    private Integer postedBy;

    @TableField("numOfComment")
    private Integer numOfComment;

    @TableField("lastEdited")
    private LocalDateTime lastEdited;
    
    private Integer type;

    private Integer aid;
    
    private Integer tid;
    
    @JSONField(serialize = false)
    @TableField(exist = false)
    private List<BhacComment> comments;
    
    @JSONField(serialize = false)
    @TableField(exist = false)
    private BhacActivity activity;
    
    @JSONField(serialize = false)
    @TableField(exist = false)
    private BhacTag tag;
    
    @JSONField(serialize = false)
    @TableField(exist = false)
    private BhacUser poster;

    
    public BhacActivity getActivity ()
    {
        return activity;
    }
    
    public void setActivity (BhacActivity activity)
    {
        this.activity = activity;
    }
    
    public BhacTag getTag ()
    {
        return tag;
    }
    
    public void setTag (BhacTag tag)
    {
        this.tag = tag;
    }
    
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

    public Integer getNumOfComment() {
        return numOfComment;
    }

    public void setNumOfComment(Integer numOfComment) {
        this.numOfComment = numOfComment;
    }

    public LocalDateTime getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(LocalDateTime lastEdited) {
        this.lastEdited = lastEdited;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    
    public List<BhacComment> getComments ()
    {
        return comments;
    }
    
    public void setComments (List<BhacComment> comments)
    {
        this.comments = comments;
    }
    
    public BhacUser getPoster () { return poster; }
    
    public void setPoster (BhacUser poster) { this.poster = poster; }
    
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    
    @Override
    public String toString ()
    {
        return "BhacPost{" + "id=" + id + ", title='" + title + '\'' + ", postedBy=" + postedBy + ", numOfComment=" + numOfComment + ", lastEdited=" + lastEdited + ", type=" + type + ", aid=" + aid + ", tid=" + tid + ", comments=" + comments + ", activity=" + activity + ", tag=" + tag + ", poster=" + poster + '}';
    }
}

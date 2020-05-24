package cn.edu.buaa.se.bhac.Dao.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.junit.Test;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangqichang
 * @since 2020-04-30
 */
@TableName(value = "bhac_comment",resultMap = "BhacCommentMap")
public class BhacComment extends Model<BhacComment> {

    private static final long serialVersionUID=1L;
    
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("seqNum")
    private Integer seqNum;

    @TableField("postedBy")
    private Integer postedBy;

    @TableField("parentId")
    private Integer parentId;

    private String content;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField( format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    
    private Integer pid;
    
    @JSONField(serialize = false)
    @TableField(exist = false)
    private BhacPost post;
    
    @JSONField(serialize = false)
    @TableField(exist = false)
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "BhacComment{" +
                "id=" + id +
                ", seqNum=" + seqNum +
                ", postedBy=" + postedBy +
                ", parentId=" + parentId +
                ", content=" + content +
                ", date=" + date +
                ", pid=" + pid +
                ", post=" + post +
                ", poster=" + poster +
                '}';
    }
}

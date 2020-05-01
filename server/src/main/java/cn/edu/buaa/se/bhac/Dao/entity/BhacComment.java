package cn.edu.buaa.se.bhac.Dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

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
@TableName("bhac_comment")
public class BhacComment extends Model<BhacComment> {

    private static final long serialVersionUID=1L;

    private Integer id;

    @TableField("seqNum")
    private Integer seqNum;

    @TableField("postedBy")
    private Integer postedBy;

    @TableField("parentId")
    private Integer parentId;

    private Blob content;

    private LocalDateTime date;
    
    private Integer pid;
    
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

    public Blob getContent() {
        return content;
    }

    public void setContent(Blob content) {
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
    public String toString ()
    {
        return "BhacComment{" + "id=" + id + ", seqNum=" + seqNum + ", postedBy=" + postedBy + ", parentId=" + parentId + ", content=" + content + ", date=" + date + ", pid=" + pid + '}';
    }
}

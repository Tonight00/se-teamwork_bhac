package Dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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

    
    @TableField(exist = false)
    private List<BhacComment> comments;
    
    public List<BhacComment> getComments ()
    {
        return comments;
    }
    
    public void setComments (List<BhacComment> comments)
    {
        this.comments = comments;
    }
    
    private Integer type;

    private Integer aid;
    
    private Integer tid;
    
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    
    @Override
    public String toString ()
    {
        return "BhacPost{" + "id=" + id + ", title='" + title + '\'' + ", postedBy=" + postedBy + ", numOfComment=" + numOfComment + ", lastEdited=" + lastEdited + ", comments=" + comments + ", type=" + type + ", aid=" + aid + ", tid=" + tid + '}';
    }
}

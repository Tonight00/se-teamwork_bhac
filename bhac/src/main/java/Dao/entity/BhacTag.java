package Dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName(value = "bhac_tag",resultMap = "BhacTagMap")
public class BhacTag extends Model<BhacTag> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer state;

    private Integer parentId;
    
    @TableField(exist = false)
    private List<BhacPost> posts;
    
    @TableField(exist =  false)
    private List<BhacRole> roles;
    
    @TableField(exist = false)
    private List<BhacActivity> activitiesBelong;
    
    public List<BhacPost> getPosts ()
    {
        return posts;
    }
    
    public void setPosts (List<BhacPost> posts)
    {
        this.posts = posts;
    }
    
    public List<BhacRole> getRoles ()
    {
        return roles;
    }
    
    public void setRoles (List<BhacRole> roles)
    {
        this.roles = roles;
    }
    
    public List<BhacActivity> getActivitiesBelong ()
    {
        return activitiesBelong;
    }
    
    public void setActivitiesBelong (List<BhacActivity> activitiesBelong)
    {
        this.activitiesBelong = activitiesBelong;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    
    @Override
    public String toString ()
    {
        return "BhacTag{" + "id=" + id + ", name='" + name + '\'' + ", state=" + state + ", parentId=" + parentId + ", posts=" + posts + ", roles=" + roles + ", activitiesBelong=" + activitiesBelong + '}';
    }
}

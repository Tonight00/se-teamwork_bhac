package cn.edu.buaa.se.bhac.Dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

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
@TableName(value = "bhac_role",resultMap = "BhacRoleMap")
public class BhacRole extends Model<BhacRole> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer tid;

    private Integer state;

    @TableField(exist = false)
    private List<BhacUser> usersAct;
    
    @TableField(exist = false)
    private BhacTag tag;
    
    
    public List<BhacUser> getUsersAct ()
    {
        return usersAct;
    }
    
    public void setUsersAct (List<BhacUser> usersAct)
    {
        this.usersAct = usersAct;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
    
    public BhacTag getTag () { return tag; }
    
    public void setTag (BhacTag tag) { this.tag = tag; }
    
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    
    @Override
    public String toString ()
    {
        return "BhacRole{" + "id=" + id + ", tid=" + tid + ", state=" + state + ", usersAct=" + usersAct + '}';
    }
}

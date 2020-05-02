package cn.edu.buaa.se.bhac.Dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
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
@TableName(value = "bhac_activity", resultMap = "BhacActivityMap")
public class BhacActivity extends Model<BhacActivity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer category;

    private Integer uid;


    private String title;

    private LocalDateTime ddl;

    private LocalDateTime begin;

    private LocalDateTime end;

    private String brief;

    @TableField("isOpen")
    private Integer isOpen;

    @TableField("limitPeopleNum")
    private Integer limitPeopleNum;

    private Integer state;

    private String extra;

    @TableField(exist = false)
    private BhacUser releaser;

    @TableField(exist = false)
    private List<BhacUser> usersProcessing;

    @TableField(exist = false)
    private List<BhacUser> usersSucceed;

    @TableField(exist = false)
    private BhacTag categoryTag;


    @TableField(exist = false)
    private List<BhacUser> usersManage;

    @TableField(exist = false)
    private List<BhacTag> tagsBelong;

    @TableField(exist = false)
    private List<BhacPost> posts;

    public List<BhacPost> getPosts() {
        return posts;
    }

    public void setPosts(List<BhacPost> posts) {
        this.posts = posts;
    }

    public List<BhacUser> getUsersProcessing() {
        return usersProcessing;
    }

    public void setUsersProcessing(List<BhacUser> usersProcessing) {
        this.usersProcessing = usersProcessing;
    }

    public List<BhacUser> getUsersSucceed() {
        return usersSucceed;
    }

    public void setUsersSucceed(List<BhacUser> usersSucceed) {
        this.usersSucceed = usersSucceed;
    }

    public List<BhacUser> getUsersManage() {
        return usersManage;
    }

    public void setUsersManage(List<BhacUser> usersManage) {
        this.usersManage = usersManage;
    }

    public List<BhacTag> getTagsBelong() {
        return tagsBelong;
    }

    public void setTagsBelong(List<BhacTag> tagsBelong) {
        this.tagsBelong = tagsBelong;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDdl() {
        return ddl;
    }

    public void setDdl(LocalDateTime ddl) {
        this.ddl = ddl;
    }

    public LocalDateTime getBegin() {
        return begin;
    }

    public void setBegin(LocalDateTime begin) {
        this.begin = begin;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getLimitPeopleNum() {
        return limitPeopleNum;
    }

    public void setLimitPeopleNum(Integer limitPeopleNum) {
        this.limitPeopleNum = limitPeopleNum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public BhacUser getReleaser() {
        return releaser;
    }

    public void setReleaser(BhacUser releaser) {
        this.releaser = releaser;
    }


    public BhacTag getCategoryTag() {
        return categoryTag;
    }

    public void setCategoryTag(BhacTag categoryTag) {
        this.categoryTag = categoryTag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BhacActivity{" +
                "id=" + id +
                ", category=" + category +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", ddl=" + ddl +
                ", begin=" + begin +
                ", end=" + end +
                ", brief='" + brief + '\'' +
                ", isOpen=" + isOpen +
                ", limitPeopleNum=" + limitPeopleNum +
                ", state=" + state +
                ", extra='" + extra + '\'' +
                ", releaser=" + releaser +
                ", usersProcessing=" + usersProcessing +
                ", usersSucceed=" + usersSucceed +
                ", categoryTag=" + categoryTag +
                ", usersManage=" + usersManage +
                ", tagsBelong=" + tagsBelong +
                ", posts=" + posts +
                '}';
    }
}

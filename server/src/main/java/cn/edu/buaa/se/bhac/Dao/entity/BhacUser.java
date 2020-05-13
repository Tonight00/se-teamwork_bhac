package cn.edu.buaa.se.bhac.Dao.entity;

import com.alibaba.fastjson.annotation.JSONField;
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
@TableName(value = "bhac_user", resultMap = "BhacUserMap")
public class BhacUser extends Model<BhacUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String email;
    
    
    
    @TableField("phoneNum")
    private String phoneNum;


    private String password;

    private Integer state;
    
    
    
    @TableField("firstName")
    private String firstName;
    
    
    
    @TableField("lastName")
    private String lastName;
    
    
   
    @TableField("studentId")
    private String studentId;
    
    
    
    @TableField("avatarUrl")
    private String avatarUrl;

    private Integer gender;
    
    
    @JSONField(serialize = false)
    @TableField(exist = false)
    private List<BhacActivity> activitiesProcessing;
    
    
    @JSONField(serialize = false)
    @TableField(exist = false)
    private List<BhacActivity> activitiesSucceed;
    
    
    @JSONField(serialize = false)
    @TableField(exist = false)
    private List<BhacRole> rolesAct;
    
    
    @JSONField(serialize = false)
    @TableField(exist = false)
    private List<BhacActivity> activitiesRelease;
    
    
    @JSONField(serialize = false)
    @TableField(exist = false)
    private List<BhacActivity> activitiesManage;


    public List<BhacActivity> getActivitiesProcessing() {
        return activitiesProcessing;
    }

    public void setActivitiesProcessing(List<BhacActivity> activitiesProcessing) {
        this.activitiesProcessing = activitiesProcessing;
    }

    public List<BhacActivity> getActivitiesSucceed() {
        return activitiesSucceed;
    }

    public void setActivitiesSucceed(List<BhacActivity> activitiesSucceed) {
        this.activitiesSucceed = activitiesSucceed;
    }

    public List<BhacActivity> getActivitiesRelease() {
        return activitiesRelease;
    }

    public void setActivitiesRelease(List<BhacActivity> activitiesRelease) {
        this.activitiesRelease = activitiesRelease;
    }

    public List<BhacActivity> getActivitiesManage() {
        return activitiesManage;
    }

    public void setActivitiesManage(List<BhacActivity> activitiesManage) {
        this.activitiesManage = activitiesManage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public List<BhacRole> getRolesAct() {
        return rolesAct;
    }

    public void setRolesAct(List<BhacRole> rolesAct) {
        this.rolesAct = rolesAct;
    }

    public BhacUser(String username, String email, String phoneNum, String password, Integer gender) {
        this.username = username;
        this.email = email;
        this.phoneNum = phoneNum;
        this.password = password;
        this.gender = gender;
    }

    public BhacUser() {
    }

    @Override
    public String toString() {
        return "BhacUser{" + "id=" + id + ", username='" + username + '\'' + ", email='" + email + '\'' + ", phoneNum='" + phoneNum + '\'' + ", password='" + password + '\'' + ", state=" + state + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", studentId='" + studentId + '\'' + ", avatarUrl='" + avatarUrl + '\'' + ", gender=" + gender + ", activitiesProcessing=" + activitiesProcessing + ", activitiesSucceed=" + activitiesSucceed + ", rolesAct=" + rolesAct + ", activitiesRelease=" + activitiesRelease + ", activitiesManage=" + activitiesManage + '}';
    }
}

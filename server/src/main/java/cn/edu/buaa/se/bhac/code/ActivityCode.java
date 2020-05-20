package cn.edu.buaa.se.bhac.code;

import java.util.HashMap;
import java.util.Map;

public enum ActivityCode implements BaseCode
{
    ERR_ACTIVITY_NOT_EXISTED,
    ERR_ACTIVITY_ACC_DUP,
    ERR_ACTIVITY_FULLED,
    SUCC_ACTIVITY_AUDIT_SUCC,
    SUCC_ACTIVITY_UPD,
    SUCC_ACTIVITY_ADD,
    SUCC_ACTIVITY_ACC,
    SUCC_ACTIVITY_AUDIT_WAIT;
    private static Map<BaseCode, String> message = new HashMap<BaseCode, String>() {
        {
            put(ActivityCode.SUCC_ACTIVITY_AUDIT_SUCC,"活动审核成功");
            put(ActivityCode.SUCC_ACTIVITY_AUDIT_WAIT,"活动待审核");
            put(ActivityCode.ERR_ACTIVITY_NOT_EXISTED,"活动不存在");
            put(ActivityCode.SUCC_ACTIVITY_ADD,"活动添加成功");
            put(ActivityCode.SUCC_ACTIVITY_ACC,"活动申请已批准");
            put(ActivityCode.ERR_ACTIVITY_ACC_DUP,"不能重复批准");
            put(ActivityCode.SUCC_ACTIVITY_UPD,"活动修改成功");
            put(ActivityCode.ERR_ACTIVITY_FULLED,"活动参与人数已满");
        }
    };
    
    
    @Override
    public String getMessage() {
    return message.get(this);
}
    
    @Override
    public boolean isSuccessful ()
    {
        return this.toString().startsWith("SUCC");
    }
    
    
}

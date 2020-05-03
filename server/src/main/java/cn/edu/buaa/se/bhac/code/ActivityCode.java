package cn.edu.buaa.se.bhac.code;

import java.util.HashMap;
import java.util.Map;

public enum ActivityCode implements BaseCode
{
    ERR_ACTIVITY_NOT_EXISTED,
    ERR_ACTIVITY_INNER_ERROR,
    SUCC_ACTIVITY_AUDIT_SUCC,
    SUCC_ACTIVITY_AUDIT_WAIT;
    
    private static Map<BaseCode, String> message = new HashMap<BaseCode, String>() {
        {
            put(ActivityCode.SUCC_ACTIVITY_AUDIT_SUCC,"活动审核成功");
            put(ActivityCode.SUCC_ACTIVITY_AUDIT_WAIT,"活动待审核");
            put(ActivityCode.ERR_ACTIVITY_NOT_EXISTED,"活动不存在");
            put(ActivityCode.ERR_ACTIVITY_INNER_ERROR,"服务器内部错误");
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

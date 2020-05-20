package cn.edu.buaa.se.bhac.code;

import java.util.HashMap;
import java.util.Map;

public enum CommentCode implements BaseCode
{
    SUCC_COMMENT_ADDED;
    
    private static Map<BaseCode, String> message = new HashMap<BaseCode, String>() {
        {
            put(CommentCode.SUCC_COMMENT_ADDED, "该用户名已注册");
        }
    };
    
    @Override
    public String getMessage () { return message.get(this); }
    
    @Override
    public boolean isSuccessful () { return this.toString().startsWith("SUCC"); }
}

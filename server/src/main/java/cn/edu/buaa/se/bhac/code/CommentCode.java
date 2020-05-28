package cn.edu.buaa.se.bhac.code;

import java.util.HashMap;
import java.util.Map;

public enum CommentCode implements BaseCode
{
    ERR_COMMENT_PERMIT,
    SUCC_COMMENT_ADDED;
    
    private static Map<BaseCode, String> message = new HashMap<BaseCode, String>() {
        {
           put(CommentCode.SUCC_COMMENT_ADDED, "发表成功");
            put(CommentCode.ERR_COMMENT_PERMIT,"没有权限添加回复");
        }
    };
    
    @Override
    public String getMessage () { return message.get(this); }
    
    @Override
    public boolean isSuccessful () { return this.toString().startsWith("SUCC"); }
}

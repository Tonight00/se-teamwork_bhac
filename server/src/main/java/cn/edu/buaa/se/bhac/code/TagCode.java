package cn.edu.buaa.se.bhac.code;

import java.util.HashMap;
import java.util.Map;

public enum  TagCode implements BaseCode
{
    ERR_TAG_DELETED,
    ERR_TAG_EXISTED,
    SUCC_TAG_ADDED,
    SUCC_TAG_DELETED;
    
    private static Map<BaseCode, String> message = new HashMap<BaseCode, String>() {
        {
            put(TagCode.SUCC_TAG_ADDED,"标签添加成功");
            put(TagCode.SUCC_TAG_DELETED,"标签删除成功");
            put(TagCode.ERR_TAG_DELETED,"标签已经被删除");
            put(TagCode.ERR_TAG_EXISTED,"标签已经存在");
        }
    };
    
    @Override
    public String getMessage ()
    {
        return message.get(this);
    }
    
    @Override
    public boolean isSuccessful ()
    {
        return ! this.toString().startsWith("ERR");
    }
}

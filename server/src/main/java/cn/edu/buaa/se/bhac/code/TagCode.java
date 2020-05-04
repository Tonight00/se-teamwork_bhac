package cn.edu.buaa.se.bhac.code;

import java.util.HashMap;
import java.util.Map;

public enum  TagCode implements BaseCode
{
    ERR_TAG_NO_NAME,
    ERR_TAG_NO_ROLE,
    ERR_TAG_INNER_ERROR,
    SUCC_TAG_ADDED,
    SUCC_TAG_DELETED,
    SUCC_TAG_NAME_EXISTED;
    
    private static Map<BaseCode, String> message = new HashMap<BaseCode, String>() {
        {
            put(TagCode.ERR_TAG_NO_NAME,"标签不存在");
            put(TagCode.SUCC_TAG_NAME_EXISTED,"标签名查询成功");
            put(TagCode.ERR_TAG_NO_ROLE,"标签没有对应角色");
            put(TagCode.SUCC_TAG_ADDED,"标签添加成功");
            put(TagCode.SUCC_TAG_DELETED,"标签删除成功");
            put(TagCode.ERR_TAG_INNER_ERROR,"服务器内部错误");
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

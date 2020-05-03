package cn.edu.buaa.se.bhac.Utils;

import cn.edu.buaa.se.bhac.Dao.entity.BhacUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public class DaoUtils
{
    public static <T> List<T> PageSearch(BaseMapper mapper, Page<T>page, QueryWrapper q) {
        IPage<T> iPage = mapper.selectPage(page,q);;
        if(iPage==null) { return null; }
        return iPage.getRecords();
    }
}

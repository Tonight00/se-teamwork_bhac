package cn.edu.buaa.se.bhac.comparators;

import cn.edu.buaa.se.bhac.Dao.entity.BhacActivity;

import java.time.LocalDateTime;
import java.util.Comparator;

public class ActivityComp implements Comparator<BhacActivity>
{
    @Override
    public int compare (BhacActivity o1, BhacActivity o2)
    {
        LocalDateTime d1 = o1.getBegin();
        LocalDateTime d2 = o2.getBegin();
        return d1.compareTo(d2);
    }
}

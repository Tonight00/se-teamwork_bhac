package cn.edu.buaa.se.bhac.comparators;

import cn.edu.buaa.se.bhac.Dao.entity.BhacComment;

import java.util.Comparator;

public class CommentsComp implements Comparator<BhacComment>
{
    @Override
    public int compare (BhacComment o1, BhacComment o2)
    {
        int s1 = o1.getSeqNum(),s2 = o2.getSeqNum();
        return s1-s2;
    }
}

package bhac;

import Dao.entity.*;
import Dao.mapper.*;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test
{
    @Autowired
    private BhacActivityMapper bhacActivityMapper;
    @Autowired
    private BhacUserMapper bhacUserMapper;
    @Autowired
    private BhacTagMapper bhacTagMapper;
    @Autowired
    private BhacRoleMapper bhacRoleMapper;
    @Autowired
    private BhacPostMapper bhacPostMapper;
    
    public <T>void print(List<T> st ) {
        if(st==null){
            System.out.println("-------------------");
            return;
        }
       for(T e: st)
           System.out.println(e);
       System.out.println("-------------------");
    }
    @Test
    public void testUserSelect() {
        
          //  检查多表关系
        BhacUser u = bhacUserMapper.selectById(1);
        List<BhacActivity> process = u.getActivitiesProcessing();
        print(process);
        List<BhacActivity> succeed = u.getActivitiesSucceed();
        print(succeed);
        List<BhacActivity> release = u.getActivitiesRelease();
        print(release);
        List<BhacActivity> manage = u.getActivitiesManage();
        print(manage);
        List<BhacRole> act = u.getRolesAct();
        print(act);
        
        /*
         查询所有
        System.out.println(bhacUserMapper.selectByMap(null));
        */
        /*
         条件查询
        QueryWrapper t= new QueryWrapper();
        t.eq("username","r1");
        System.out.println(bhacUserMapper.selectOne(t));
        */
        
        
    }
    @Test
    public void testUserInsert(){
        /*
        INSERT INTO bhac_user(username,email,phoneNum,`password`,gender )
        VALUES('r3','zzzzz@xx.com','18100011002','125asbv.',1);*/
        BhacUser u = new BhacUser("r4","aaaaa@xx.com","18100011003","126asbv",2);
        bhacUserMapper.insert(u);
        //查询所有 select *
        System.out.println(bhacUserMapper.selectByMap(null));
        
    }
    @Test
    public void testUserDelete() {
        QueryWrapper q = new QueryWrapper();
        q.eq("id",4);
        bhacUserMapper.delete(q);
        System.out.println(bhacUserMapper.selectById(4));
    }
    @Test
    public void testUserUpdate() {

        /*
            根据id修改，修改字段为set的字段。
         
        BhacUser u = new BhacUser();
        u.setId(3); u.setUsername("rr");
        bhacUserMapper.updateById(u);
        System.out.println(bhacUserMapper.selectById(3));
        */
        /*
          根据UpdateWrapper作为条件，User作为修改内容
       
        UpdateWrapper uw = new UpdateWrapper();
        uw.eq("username","rr");
        BhacUser uu = new BhacUser();
        uu.setUsername("r3");
        bhacUserMapper.update(uu,uw);
       */
        System.out.println(bhacUserMapper.selectById(3));
    }
    
    @Test
    public void testActivitySelect(){
        BhacActivity a  = bhacActivityMapper.selectById(1);
        List<BhacUser> u1 = a.getUsersProcessing();
        print(u1);
        List<BhacUser> u2 = a.getUsersSucceed();
        print(u2);
        List<BhacUser> u3 = a.getUsersManage();
        print(u3);
        System.out.println(""+a.getUid()); // releaser
        List<BhacTag> tags = a.getTagsBelong();
        print(tags);
        List<BhacPost> posts = a.getPosts();
        print(posts);
    }
    
    @Test
    public void testRoleSelect() {
        BhacRole r = bhacRoleMapper.selectById(4);
        List<BhacUser> u = r.getUsersAct();
        print(u);
    }
    @Test
    public void testTagSelect() {
        BhacTag t = bhacTagMapper.selectById(2);
        List<BhacActivity> a = t.getActivitiesBelong();
        
        print(a);
        List<BhacRole> r = t.getRoles();
        print(r);
        List<BhacPost> p = t.getPosts();
        print(p);
    }
    
    @Test
    public void testPostSelect() {
        BhacPost p = bhacPostMapper.selectById(1);
        List<BhacComment> c = p.getComments();
        print(c);
        System.out.println(p);
        
    }
}

package bhac;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("Dao.mapper")
public class BhacApplication
{
    
    public static void main (String[] args)
    {
        SpringApplication.run(BhacApplication.class, args);
    }
    
}

import com.potxxx.firstim.dataServer.Mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class UserTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUser(){
        userMapper.selectList(null).forEach((f)->{
            log.info("{}",f);
        });
    }

}

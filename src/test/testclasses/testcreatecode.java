package testclasses;

import com.dolpi.CodeWeaver.CodeWeaverApplication;
import com.dolpi.CodeWeaver.Controller.Usercontroller;
import com.dolpi.CodeWeaver.Controller.createcode;
import com.dolpi.CodeWeaver.Entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = CodeWeaverApplication.class)
public class testcreatecode {

    @Autowired
    private createcode ccode;



    @Autowired
    private Usercontroller usercontroller;

    @Test
    void testgetcode(){
        User u=new User();
        u.setUsername("manasco");
        u.setPassword("888");
        u.setEmail("jeevanrekha287@gmail.com");
        assertNotNull(u);


    }
}

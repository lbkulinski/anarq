import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static junit.framework.TestCase.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class testCreateUser {

    createUser user;

    @Test

    public void testCreateANewUser1()  throws Exception{
        user = new createUser("test1", "1234", "bones", "miek", 3,1,109);
        System.out.println("Testing: " + user.username);
        System.out.println("Created User " + user.username);
        assertEquals(true, (user.addJammer() == 1));
    }

    @Test
    public void testCreateExistingUser1()  throws Exception{
        user = new createUser("test1", "1234", "bones", "miek", 3,1,109);
        System.out.println("Testing: " + user.username);
        System.out.println("Did Not Create User " + user.username);
        assertEquals(false, (user.addJammer() == 1));
    }

    @Test
    public void testCreateANewUser2()  throws Exception{
        user = new createUser("test2", "1234", "bones", "miek", 3,1,109);
        System.out.println("Testing: " + user.username);
        System.out.println("Created User " + user.username);
        assertEquals(true, (user.addJammer() == 1));
    }

    @Test
    public void testCreateANewUser3()  throws Exception{
        user = new createUser("test3", "1234", "bones", "miek", 3,1,109);
        System.out.println("Testing: " + user.username);
        System.out.println("Created User " + user.username);
        assertEquals(true, (user.addJammer() == 1));
    }

    @Test
    public void testCreateExistingUser2()  throws Exception{
        user = new createUser("test2", "1234", "bones", "miek", 3,1,109);
        System.out.println("Testing: " + user.username);
        System.out.println("Did Not Create User " + user.username);
        assertEquals(false, (user.addJammer() == 1));
    }

    @Test
    public void testCreateANewUser4()  throws Exception{
        user = new createUser("test4", "1234", "bones", "miek", 3,1,109);
        System.out.println("Testing: " + user.username);
        System.out.println("Created User " + user.username);
        assertEquals(true, (user.addJammer() == 1));
    }

    @Test
    public void testCreateExistingUser3()  throws Exception{
        user = new createUser("test4", "1234", "bones", "miek", 3,1,109);
        System.out.println("Testing: " + user.username);
        System.out.println("Did Not Create User " + user.username);
        assertEquals(false, (user.addJammer() == 1));
    }
}

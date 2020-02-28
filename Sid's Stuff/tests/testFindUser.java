import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class testFindUser {

    findUser user;

    @Test
    public void testFindActualUser1()  throws Exception{
        user = new findUser("test1");
        System.out.println("Testing: " + user.text);
        System.out.println("Found User " + user.text);
        assertEquals(true, (user.find() != null));
    }

    @Test
    public void testFindFakeUser1()  throws Exception{
        user = new findUser("blah");
        System.out.println("Testing: " + user.text);
        System.out.println("Did Not Find User " + user.text);
        assertEquals(false, (user.find() != null));
    }

    @Test
    public void testFindActualUser2()  throws Exception{
        user = new findUser("test3");
        System.out.println("Testing: " + user.text);
        System.out.println("Found User " + user.text);
        assertEquals(true, (user.find() != null));
    }

    @Test
    public void testFindFakeUser2()  throws Exception{
        user = new findUser("testing");
        System.out.println("Testing: " + user.text);
        System.out.println("Did Not Find User " + user.text);
        assertEquals(false, (user.find() != null));
    }

    @Test
    public void testFindActualUser3()  throws Exception{
        user = new findUser("test2");
        System.out.println("Testing: " + user.text);
        System.out.println("Found User " + user.text);
        assertEquals(true, (user.find() != null));
    }

    @Test
    public void testFindFakeUser3()  throws Exception{
        user = new findUser("test5");
        System.out.println("Testing: " + user.text);
        System.out.println("Did Not Find User " + user.text);
        assertEquals(false, (user.find() != null));
    }

}

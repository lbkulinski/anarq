import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class testDeleteUser {

    deleteUser user;

    @Test
    public void testDeleteRealUser1() throws Exception {
        user = new deleteUser("test1");
        System.out.println("Testing: " + user.text);
        System.out.println("Deleted User " + user.text);
        assertEquals(true, (user.delete() == 1));
    }

    @Test
    public void testDeleteFakeUser1() throws Exception {
        user = new deleteUser("test1");
        System.out.println("Testing: " + user.text);
        System.out.println("Did Not Delete User " + user.text);
        assertEquals(false, (user.delete() == 1));
    }

    @Test
    public void testDeleteRealUser2() throws Exception {
        user = new deleteUser("test2");
        System.out.println("Testing: " + user.text);
        System.out.println("Deleted User " + user.text);
        assertEquals(true, (user.delete() == 1));
    }

    @Test
    public void testDeleteRealUser3() throws Exception {
        user = new deleteUser("test3");
        System.out.println("Testing: " + user.text);
        System.out.println("Deleted User " + user.text);
        assertEquals(true, (user.delete() == 1));
    }

    @Test
    public void testDeleteFakeUser2() throws Exception {
        user = new deleteUser("test2");
        System.out.println("Testing: " + user.text);
        System.out.println("Did Not Delete User " + user.text);
        assertEquals(false, (user.delete() == 1));
    }

    @Test
    public void testDeleteRealUser4() throws Exception {
        user = new deleteUser("test4");
        System.out.println("Testing: " + user.text);
        System.out.println("Deleted User " + user.text);
        assertEquals(true, (user.delete() == 1));
    }

    @Test
    public void testDeleteFakeUser3() throws Exception {
        user = new deleteUser("testing");
        System.out.println("Testing: " + user.text);
        System.out.println("Did Not Delete User " + user.text);
        assertEquals(false, (user.delete() == 1));
    }

}
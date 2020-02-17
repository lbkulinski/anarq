import org.junit.*;

import static junit.framework.TestCase.assertEquals;

public class testUsernameValidation {

    UsernameValidation user;

    @Test
    public void testSimpleBadWord()  throws Exception{
        user = new UsernameValidation("banal");
        System.out.println("Testing: " + user.username);
        assertEquals(true, user.hasBadWords());
    }

    @Test
    public void testIntermediateBadWord()  throws Exception{
        user = new UsernameValidation("Testingblowjobsintheword");
        System.out.println("Testing: " + user.username);
        assertEquals(true, user.hasBadWords());
    }

    @Test
    public void testSimpleMixedCase()  throws Exception{
        user = new UsernameValidation("baNaL");
        System.out.println("Testing: " + user.username);
        assertEquals(true, user.hasBadWords());
    }

    @Test
    public void testIntermediateMixedCase()  throws Exception{
        user = new UsernameValidation("TestingBlOwJoBsInTheword");
        System.out.println("Testing: " + user.username);
        assertEquals(true, user.hasBadWords());
    }

    @Test
    public void testAdvancedMixedCase()  throws Exception{
        user = new UsernameValidation("TestingBLOwjOBsinTheword");
        System.out.println("Testing: " + user.username);
        assertEquals(true, user.hasBadWords());
    }

    @Test
    public void testNoBadWord1()  throws Exception{
        user = new UsernameValidation("TameImpala");
        System.out.println("Testing: " + user.username);
        assertEquals(false, user.hasBadWords());
    }

    @Test
    public void testNoBadWord2()  throws Exception{
        user = new UsernameValidation("Banana");
        System.out.println("Testing: " + user.username);
        assertEquals(false, user.hasBadWords());
    }

}

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * A runner for the update username and update password test cases.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version February 26, 2020
 */
public final class TestCaseRunner {
    public static void main(String[] args) {
        JUnitCore jUnitCore;
        Result updateUsernameResult;

        assert args.length == 2;

        System.setProperty("database-username", args[0]);

        System.setProperty("database-password", args[1]);

        jUnitCore = new JUnitCore();

        updateUsernameResult = jUnitCore.run(UpdateUsernameImplTest.class);

        for (Failure failure : updateUsernameResult.getFailures()) {
            System.out.println(failure.getTrace());
        } //end for

        System.out.printf("Update username test cases successful: %b%n", updateUsernameResult.wasSuccessful());
    } //main
}
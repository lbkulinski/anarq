import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * A runner for the {@code UpdateUsernameImplTest} class.
 *
 * @author Logan Kulinski, lbk@purdue.edu
 * @version February 25, 2020
 */
public final class UpdateUsernameImplRunner {
    public static void main(String[] args) {
        JUnitCore jUnitCore;
        Result result;

        assert args.length == 2;

        System.setProperty("database-username", args[0]);

        System.setProperty("database-password", args[1]);

        jUnitCore = new JUnitCore();

        result = jUnitCore.run(UpdateUsernameImplTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.getTrace());
        } //end for

        System.out.printf("Tests cases successful: %b%n", result.wasSuccessful());
    } //main
}
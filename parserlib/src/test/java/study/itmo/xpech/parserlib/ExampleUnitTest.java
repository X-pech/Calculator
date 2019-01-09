package study.itmo.xpech.parserlib;

import org.junit.Assert;
import org.junit.Test;

import study.itmo.xpech.parserlib.exceptions.DivisionByZeroException;
import study.itmo.xpech.parserlib.exceptions.ParsingException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        Parser parser = new Parser();
        Double d = 4.0;
        try {
            assertEquals(d.compareTo(parser.eval("2    +  2")), 0);
        } catch (Exception e) {
            System.err.println(e.getCause().toString());
        }
    }

    @Test
    public void DivisionByZero() {
        Parser parser = new Parser();
        boolean flag = true;
        try {
            parser.eval("2/0");
            flag = false;
        } catch (ParsingException pe) {

        }
        assertTrue(flag);
    }

    @Test
    public void DivisionByZero2() {
        Parser parser = new Parser();
        boolean flag = true;
        try {
            parser.eval("2/0.0");
            flag = false;
        } catch (ParsingException pe) {

        }
        assertTrue(flag);
    }
}
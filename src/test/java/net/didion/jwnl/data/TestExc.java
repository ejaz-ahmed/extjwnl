package net.didion.jwnl.data;

import net.didion.jwnl.JWNLException;
import net.didion.jwnl.dictionary.Dictionary;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * Tests Exc class.
 *
 * @author bwalenz
 * @author Aliaksandr Autayeu avtaev@gmail.com
 */
public class TestExc extends BaseDictionaryTest {

    private Exc testObj;

    private static final POS pos = POS.NOUN;
    private static final String lemma = "alam";
    private static final String exc1 = "exc1";
    private static final String exc2 = "exc2";

    @Before
    public void setUp() throws JWNLException, IOException {
        super.setUp();
        testObj = new Exc(dictionary, pos, lemma, Arrays.asList(exc1, exc2));
    }

    @Test
    public void testGetPOS() {
        Assert.assertEquals(pos, testObj.getPOS());
    }

    @Test
    public void testGetLemma() {
        Assert.assertEquals(lemma, testObj.getLemma());
    }

    @Test
    public void testGetExceptions() {
        Assert.assertEquals(2, testObj.getExceptions().size());
        Assert.assertEquals(exc1, testObj.getExceptions().get(0));
        Assert.assertEquals(exc2, testObj.getExceptions().get(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor() throws JWNLException {
        testObj = new Exc(null, null, null, null);
    }
}
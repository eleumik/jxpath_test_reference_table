package org.apache.commons.jxpath.referencetable;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathContextFactory;
import org.apache.commons.jxpath.JXPathNotFoundException;

public class TestSingleCases extends TestCase
{
    public void test96_Map()
    {
        Object r1 = doXPathOnMap("descendant::node()[7]", false);
        assertEquals(" is " + r1, "last", r1);
    }

    /**
     * Test about 'place reserved' in bean.
     * <p>
     * https://issues.apache.org/jira/browse/JXPATH-174
     */
    public void test96_Bean()
    {
        try
        {
            Object r = doXPathOnBean("descendant::node()[7]", false);
            assertNull(r);
        }
        catch (JXPathNotFoundException e)
        {
            fail("Should return null ?...instead exception");
        }
    }

    /**
     * Place is reserved also in 'selectNodes'
     */
    public void test96_97_98_Bean_SelectMultipleNodes()
    {
        List r = doXPathOnBeanMany("descendant::node()", false);
        assertNotNull(r.get(5));
        assertNull(r.get(6)); // this in xpath is 7, test 96
        assertNull(r.get(7)); // this in xpath is 8, test 97
        assertNull(r.get(8)); // this in xpath is 9, test 98
        assertNotNull(r.get(9));
        assertEquals("last", r.get(9));
        assertEquals(10, r.size());
    }
    /**
     * Place is reserved also in 'selectNodes'
     */
    public void test85_86_87_Bean_SelectMultipleNodes()
    {
        List r = doXPathOnBeanMany("descendant-or-self::node()", false);
        assertNotNull(r.get(6));
        assertNull(r.get(7)); // this in xpath is 8, test 85
        assertNull(r.get(8)); // this in xpath is 9, test 86
        assertNull(r.get(9)); // this in xpath is 10, test 87
        assertNotNull(r.get(10));
        assertEquals("last", r.get(10));
        assertEquals(11, r.size());
    }

    public void test97_Map()
    {
        Object r1 = doXPathOnMap("descendant::node()[8]", false);
        assertNull(r1);
    }

    public void test97_Bean()
    {
        try
        {
            Object r = doXPathOnBean("descendant::node()[8]", false);
            assertNull(r);
        }
        catch (JXPathNotFoundException e)
        {
            fail("Should return null ?...instead exception");
        }
    }

    public void test98_Map()
    {
        Object r1 = doXPathOnMap("descendant::node()[9]", false);
        assertNull(r1);
    }

    public void test98_Bean()
    {
        try
        {
            Object r = doXPathOnBean("descendant::node()[9]", false);
            assertNull(r);
        }
        catch (JXPathNotFoundException e)
        {
            fail("Should return null ?...instead exception");
        }
    }

    public void test99_Map()
    {
        Object r1 = doXPathOnMap("descendant::node()[10]", false);
        assertNull(r1);
    }

    public void test99_Bean()
    {
        try
        {
            Object r = doXPathOnBean("descendant::node()[10]", false);
            assertNull("should be null instead is " + r, r);
        }
        catch (JXPathNotFoundException e)
        {
            fail("Should return null ?...instead exception");
        }
    }

    // ///////////////////////////////////////////////

    public Object doXPathOnBean(String xpath, boolean lenient)
    {
        return doXPath(xpath, new TestReferenceTableBean().getTarget(), lenient);

    }

    public List doXPathOnBeanMany(String xpath, boolean lenient)
    {
        return doXPathMany(xpath,
            new TestReferenceTableBean().getTarget(),
            lenient);

    }

    public Object doXPathOnMap(String xpath, boolean lenient)
    {
        return doXPath(xpath, new TestReferenceTableMap().getTarget(), lenient);

    }

    private Object doXPath(String xpath, Object target, boolean lenient)
    {
        final JXPathContext ctx = JXPathContextFactory.newInstance()
            .newContext(null, target);
        ctx.setLenient(lenient);
        final Object result = ctx.selectSingleNode(xpath);
        return result;
    }

    private List doXPathMany(String xpath, Object target, boolean lenient)
    {
        final JXPathContext ctx = JXPathContextFactory.newInstance()
            .newContext(null, target);
        ctx.setLenient(lenient);
        final List result = ctx.selectNodes(xpath);
        return result;
    }

}

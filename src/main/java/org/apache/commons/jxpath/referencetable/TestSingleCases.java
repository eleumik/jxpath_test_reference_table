package org.apache.commons.jxpath.referencetable;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathContextFactory;
import org.apache.commons.jxpath.JXPathNotFoundException;

import junit.framework.TestCase;

public class TestSingleCases extends TestCase
{
    public void test96_Map()
    {
        Object r1 = doXPathOnMap("descendant::node()[7]", false);
        assertNull(r1);
    }
    
    public void test96_Bean()
    {
        try
        {
            Object r = doXPathOnBean("descendant::node()[7]", false);
            assertNull(r);
        }
        catch(JXPathNotFoundException e)
        {
            fail("Should return null ?...instead exception");
        }
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
        catch(JXPathNotFoundException e)
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
        catch(JXPathNotFoundException e)
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
        catch(JXPathNotFoundException e)
        {
            fail("Should return null ?...instead exception");
        }
    }
    
    /////////////////////////////////////////////////
    
    public Object doXPathOnBean(String xpath, boolean lenient)
    {
        return doXPath(xpath, new TestReferenceTableBean().getTarget(), lenient);
        
    }
    public Object doXPathOnMap(String xpath, boolean lenient)
    {
        return doXPath(xpath, new TestReferenceTableMap().getTarget(), lenient);
        
    }

    private Object doXPath(String xpath, Object target, boolean lenient)
    {
        final JXPathContext ctx = JXPathContextFactory.newInstance().newContext(null, target);
        ctx.setLenient(lenient);
        final Object result = ctx.selectSingleNode(xpath);
        return result;
    }

}

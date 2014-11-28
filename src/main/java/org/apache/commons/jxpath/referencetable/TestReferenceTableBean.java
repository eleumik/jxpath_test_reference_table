package org.apache.commons.jxpath.referencetable;


public class TestReferenceTableBean extends AbstractTestReferenceTable
{
    
    
    
    protected final TestBean child = new TestBean("child");
    {
        child.setInteger(INTEGER2);
    }
    
    protected final TestBean testBean = new TestBean("root");
    {
        testBean.setInteger(INTEGER);
        testBean.setString("ciao");
        testBean.setTestBean(child);
        testBean.setZlast("last");
        
    }
    protected Object getTarget()
    {
        return testBean;
    }
    protected Object getTargetChild()
    {
        return child;
    }

}

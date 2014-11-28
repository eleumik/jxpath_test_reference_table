
package org.apache.commons.jxpath.referencetable;

import junit.framework.TestSuite;

public class TestSuiteRefTable extends TestSuite
{
    public static TestSuite suite()
    {
        TestSuiteRefTable s = new TestSuiteRefTable();
        s.addTestSuite(TestReferenceTableBean.class);
        s.addTestSuite(TestReferenceTableMap.class);
        return s;
    }
}

package org.apache.commons.jxpath.referencetable;

import java.util.Map;
import java.util.TreeMap;

public class TestReferenceTableMap extends AbstractTestReferenceTable
{
    
    
    
    final Map childMap = new TreeMap();
    {
        childMap.put("integer", INTEGER2);
        childMap.put("beanName", "child");
    }
    
    final Map testMap = new TreeMap();
    {
        testMap.put("beanName", "root");
        testMap.put("string", "ciao");
        testMap.put("testBean", childMap);
        testMap.put("integer", INTEGER);
        testMap.put("zlast", "last");
    }
    ////
    
    
    protected Object getTarget()
    {
        return testMap; // testBean;
    }
    protected Object getTargetChild()
    {
        return childMap; // child;
    }

}

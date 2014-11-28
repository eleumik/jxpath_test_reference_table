package org.apache.commons.jxpath.referencetable;

import java.util.List;

/**
 * Simple bean for tests.
 * <p>Has 3 simple properties [beanName, integer, string]
 * then a complex property of its same type/class [testBean]
 * then an other simple property [zlast].
 * @author mik
 *
 */
public class TestBean
{
    public TestBean(String beanName)
    {
        super();
        this.beanName = beanName;
    }
    
    public String toString()
    {
        return "TestBean(" + beanName + ")";
    }

    public String toStringLong()
    {
        return "TestBean(" + beanName + "){\n\tbeanName:" + beanName + "\n\tinteger: " + integer + 
            "\n\tstring: " + (string==null ? null :  "\"" + string + "\"")
            +"\n\ttestBean:" + (testBean==null ? null : testBean.toStringLong()) + 
             "\n\tzlast:" + zlast +  
            "\n}";
    }
    final String beanName;
    public String getBeanName()
    {
        return beanName;
    }
    
    Integer integer;
    
    String string;
    
    List listOfInteger;
    
    List listOfTestBean;
    
    TestBean testBean;

    String zlast;
    
    
    
    public final Integer getInteger()
    {
        return integer;
    }

    public final void setInteger(Integer integer)
    {
        this.integer = integer;
    }

    public final String getString()
    {
        return string;
    }

    public final void setString(String string)
    {
        this.string = string;
    }


    public final TestBean getTestBean()
    {
        return testBean;
    }

    public final void setTestBean(TestBean testBean)
    {
        this.testBean = testBean;
    }

    public final String getZlast()
    {
        return zlast;
    }

    public final void setZlast(String zlast)
    {
        this.zlast = zlast;
    }

    
    
    
}

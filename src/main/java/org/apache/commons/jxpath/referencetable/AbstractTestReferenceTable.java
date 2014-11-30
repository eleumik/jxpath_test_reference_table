package org.apache.commons.jxpath.referencetable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import junit.framework.TestCase;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathContextFactory;
import org.apache.commons.jxpath.JXPathNotFoundException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public abstract class AbstractTestReferenceTable extends TestCase
{

    protected static final Integer INTEGER = new Integer(13);
    protected static final Integer INTEGER2 = new Integer(27);

    protected abstract Object getTargetChild();

    protected abstract Object getTarget();

    private String xml = "<testBean beanName='root'>\n\t<integer>13</integer>\n\t<string>ciao</string>" + "\n\t<testBean beanName='child'>\n\t\t<integer>27</integer>\n\t</testBean>\n\t"
        + "<zlast>last</zlast>\n</testBean>";
    private Document doc;
    {
        try
        {
            doc = JAXP.getDocument(new InputSource(new StringReader(xml)));
        }
        catch (Exception e)
        {
            throw new RuntimeException("invalid xml: " + e.getMessage(), e);
        }

    }
    private static final Object NULLORNOTFOUNDEXCEPTION = new Object()
    {
        public String toString()
        {
            return "{null-or-not-found-jxpathexception}";
        };
    };
    private OutputStreamWriter out;
    int index = 0;

    private XPath createXPath()
    {
        return XPathFactory.newInstance().newXPath();
        // return new org.apache.xpath.jaxp.XPathFactoryImpl().newXPath();
    }

    public void testTable() throws Exception
    {
        doTest("/", getTarget());
        doTest(".", getTarget());
        doTest("/.", getTarget());
        doTest("//.", getTarget());

        doTest("/./.", getTarget());
        doTest("/././.", getTarget());
        doTest("/./*/../.", getTarget());

        doTest("*", "root");
        doTest("*/.", "root");
        doTest("/*", "root");
        doTest("//*", "root");

        doTest("@*", "root");
        doTest("@*/.", "root");
        doTest("/@*", "root");
        doTest("//@*", "root");

        doTest("node()", "root");
        doTest("/node()", "root");
        doTest("//node()", "root");

        doTest("text()", null);
        doTest("/text()", null);
        doTest("//text()", null);
        doTest("//*/text()", null);
        doTest("integer", INTEGER);
        doTest("/integer", INTEGER);
        doTest("//integer", INTEGER);
        doTest("@integer", INTEGER);
        doTest("/@integer", INTEGER);
        doTest("/*/integer", INTEGER2);
        doTest("/*/@integer", INTEGER2);
        doTest("Integer", NULLORNOTFOUNDEXCEPTION);
        doTest("string", "ciao");
        doTest("/string", "ciao");
        doTest("//string", "ciao");
        doTest("String", NULLORNOTFOUNDEXCEPTION);
        doTest("//string[0]", null);
        doTest("//string[1]", "ciao");
        doTest("descendant-or-self::string[1]", "ciao");
        doTest("//string[2]", null);
        doTest("//string[3]", null);

        doTest("//zlast", "last");

        doTest("//integer[0]", null);
        doTest("//integer[1]", INTEGER);
        doTest("//integer[2]",
            null,
            "One could expect 27 but also Xalan return null, see 47");
        doTest("//integer[3]", null);

        doTest("/descendant-or-self::node()/child::integer[0]",
            null,
            "Full version of //integer[0]");
        doTest("/descendant-or-self::node()/child::integer[1]",
            INTEGER,
            "Full version of //integer[1]");
        doTest("/descendant-or-self::node()/child::integer[2]",
            null,
            "Expanded version of //integer[2], ");

        doTest("descendant-or-self::integer[0]",
            null,
            "Hmm..here zero index should be null (or exception ?)");
        doTest("descendant-or-self::integer[1]", INTEGER);
        doTest("descendant-or-self::integer[2]", INTEGER2, "Here is 27");
        doTest("/descendant-or-self::integer[1]", INTEGER);
        doTest("/descendant-or-self::integer[2]", INTEGER2);
        doTest("//descendant-or-self::integer[1]", INTEGER);
        doTest("//descendant-or-self::integer[2]", INTEGER2);

        doTest("*/ancestor-or-self::integer[1]", INTEGER);
        doTest("//ancestor-or-self::integer[1]", INTEGER);
        doTest("//ancestor-or-self::integer[2]", null);
        doTest("*/*/ancestor-or-self::integer[1]", INTEGER2);

        doTest("//*[0]", null); // mah "not anymore?"..,
                                // "Hmm... here ? [0] returns root ??");
        doTest("//*[1]", "root");
        doTest("//*[2]", INTEGER);
        doTest("//*[3]", "ciao");
        doTest("//*[4]", getTargetChild());
        doTest("//*[5]",
            "last",
            "Here one could expect string 'child' but.. also xalan does not include 'child' and 27 so 'last' is ok ");
        doTest("//*[6]", null);
        doTest("//*[7]", null);
        doTest("//*[8]", null, "here over limit is null");

        doTest("//node()[0]", null);
        doTest("//node()[1]", "root");
        doTest("//node()[2]", INTEGER);
        doTest("//node()[3]", "ciao");
        doTest("//node()[4]", getTargetChild());
        doTest("//node()[5]", "last");
        doTest("//node()[6]", null);
        doTest("//node()[7]", null);
        doTest("//node()[8]", null);

        /*
         * INVALID FOR XALAN doTest("//.[0]", null); doTest("//.[1]", "root");
         * doTest("//.[2]", INTEGER); doTest("//.[3]", "ciao"); doTest("//.[4]",
         * getTargetChild()); doTest("//.[5]", INTEGER2); doTest("//.[6]",
         * "last"); doTest("//.[7]", null); doTest("//.[8]",
         * NULLORNOTFOUNDEXCEPTION, "Not sure why here throws exception");
         */

        doTest("descendant-or-self::node()[0]",
            null,
            "Hmm... here ? [0] returns root ??");
        doTest("descendant-or-self::node()[1]", getTarget());
        doTest("descendant-or-self::node()[2]", "root");
        doTest("descendant-or-self::node()[3]", INTEGER);
        doTest("descendant-or-self::node()[4]", "ciao");
        doTest("descendant-or-self::node()[5]", getTargetChild());
        doTest("descendant-or-self::node()[6]", "child");
        doTest("descendant-or-self::node()[7]", INTEGER2);
        doTest("descendant-or-self::node()[8]",
            "last",
            "Not sure why here (for bean) throws exception or is null, probably because in the 'scheme' there is the property but no value");
        doTest("descendant-or-self::node()[9]",
            null,
            "Not sure why here (for bean) throws exception, see up");
        doTest("descendant-or-self::node()[10]", null);
        doTest("descendant-or-self::node()[11]", null);

        doTest("descendant::node()[0]",
            null,
            "Hmm... here ? [0] returns root ??");
        // doTest("descendant::node()[1]", getTarget());
        doTest("descendant::node()[1]", "root");
        doTest("descendant::node()[2]", INTEGER);
        doTest("descendant::node()[3]", "ciao");
        doTest("descendant::node()[4]", getTargetChild());
        doTest("descendant::node()[5]", "child");
        doTest("descendant::node()[6]", INTEGER2);
        doTest("descendant::node()[7]",
            "last",
            "Not sure why here (for bean) throws exception or is null, probably because in the 'scheme' there is the property but no value");
        doTest("descendant::node()[8]",
            null,
            "Not sure why here (for bean) throws exception, see up");
        doTest("descendant::node()[9]", null);
        doTest("descendant::node()[10]", null);
        doTest("descendant::node()[11]", null);

        doTest("descendant-or-self::*[0]",
            null,
            "Hmm... here ? [0] returns root ??");
        doTest("descendant-or-self::*[1]", getTarget());
        doTest("descendant-or-self::*[2]", "root");
        doTest("descendant-or-self::*[3]", INTEGER);
        doTest("descendant-or-self::*[4]", "ciao");
        doTest("descendant-or-self::*[5]", getTargetChild());
        doTest("descendant-or-self::*[6]", "child");
        doTest("descendant-or-self::*[7]", INTEGER2);
        doTest("descendant-or-self::*[8]",
            "last",
            "Not sure why here (for bean) throws exception or is null, probably because in the 'scheme' there is the property but no value");
        doTest("descendant-or-self::*[9]",
            null,
            "Not sure why here (for bean) throws exception, see up");
        doTest("descendant-or-self::*[10]", null);
        doTest("descendant-or-self::*[11]", null);

    }

    protected void setUp() throws Exception
    {
        File tempFolder = File.createTempFile("Dummy", ".html").getParentFile();
        File file = new File(tempFolder,
            this.getClass().getSimpleName() + ".html");
        System.out.println("Writing to " + file.getAbsolutePath());
        final java.io.OutputStreamWriter w = new java.io.OutputStreamWriter(new FileOutputStream(file),
            "UTF-8");
        this.out = w;
        final Object target = getTarget();
        final String obj = target instanceof TestBean ? ((TestBean) target).toStringLong()
            : target.toString();
        out.write("<h1>" + this.getClass().getName() + "</h1>");
        out.write("<h2> Java " + System.getProperty("java.version") + "</h2>");
        out.write("<h3> JXPATH 1.4 trunk 20141128</h3>");
        out.write("<h3> JAXP XPATH " + createXPath() + "</h3>");

        out.write("<pre>" + escape(obj) + "</pre>");
        out.write("<pre>" + escape(xml) + "</pre>");

        out.write("<style>.success{background:lightgreen;text-align:center;} .error {background:red;color:white;}</style>");
        out.write("<table border='1' cellspacing='0'>");
        out.write("<tr><th>#</th><th>XPath</th><th>Result</th><th>Test</th><th>XPath DOM result</th><th>Note</th></tr>");
        super.setUp();
    }

    protected void tearDown() throws Exception
    {
        out.write("</table>");
        out.close();

        super.tearDown();
    }

    private void doTest(String xpath, Object expected) throws Exception
    {
        doTest(xpath, expected, null);
    }

    private void doTest(String xpath, Object expected, String note) throws Exception
    {
        Object target = getTarget();
        try
        {
            doRowImpl(xpath, expected == NULLORNOTFOUNDEXCEPTION ? null
                : expected, target, false, null, note);
        }
        catch (JXPathNotFoundException e)
        {
            if (expected == NULLORNOTFOUNDEXCEPTION)
            {
                doRowImpl(xpath,
                    null,
                    target,
                    true,
                    NULLORNOTFOUNDEXCEPTION.toString(),
                    note);

            }
            else
            {
                String noteNode = getNodeResult(xpath);
                writeRow(xpath,
                    "{not-found-jxpathexception}",
                    "NO EXCEPTION EXPECTED but " + expected,
                    "error",
                    note,
                    noteNode);
            }
        }
    }

    private void doRowImpl(String xpath,
        Object expected,
        Object target,
        boolean lenient,
        String labelExpected,
        String note) throws IOException, XPathExpressionException
    {
        final JXPathContext ctx = JXPathContextFactory.newInstance()
            .newContext(null, target);
        ctx.setLenient(lenient);
        final Object result = ctx.selectSingleNode(xpath);
        final String msg;
        final String msgClass;
        if (expected == result || (expected != null && expected.equals(result)))
        {
            msg = "ok";
            msgClass = "success";
        }
        else
        {
            msg = "Expected " + expected;
            msgClass = "error";
        }
        final String noteResult = getNodeResult(xpath);
        writeRow(xpath,
        // Only when compare is ok use the label provided by caller
            ("ok".equals(msg) && labelExpected != null) ? labelExpected
                : result,
            msg,
            msgClass,
            note,
            noteResult);
    }

    private String getNodeResult(String xpath) throws XPathExpressionException
    {
        XPath ev = createXPath();
        // System.out.println("Xalan is "+ev);
        Object resultXPath = ev.evaluate(xpath, doc, XPathConstants.NODE);
        final StringBuffer sb = new StringBuffer();
        Node node = (Node) resultXPath;
        if (node instanceof org.w3c.dom.Element)
        {
            org.w3c.dom.Element el = (org.w3c.dom.Element) node;
            sb.append("<").append(node.getLocalName());
            if (node.getLocalName().equals("testBean"))
                sb.append(" beanName='")
                    .append(el.getAttribute("beanName"))
                    .append('\'');
            sb.append(">")
                .append(getText(node))
                .append("</")
                .append(node.getLocalName())
                .append(">");
        }
        else
        {
            if (resultXPath == null)
                sb.append("--no match--");
            else
                sb.append(resultXPath); // .append(resultXPath.getClass());
        }
        String noteNode = sb.toString();
        return noteNode;
    }

    private String getText(Object resultXPath)
    {
        if (resultXPath instanceof Node) { return ((Node) resultXPath).getTextContent(); }
        return null;
    }

    private void writeRow(String xpath,
        Object result,
        String msg,
        String msgClass,
        String note,
        String noteNode) throws IOException
    {
        index++;
        final String resultString = result == null ? "{null}"
            : result.toString();
        out.write("<tr id='T" + index +"'>");
        out.write("<td>");
        out.write("" + index);
        out.write("</td>");
        out.write("<td>");
        out.write(escape(xpath));
        out.write("</td>");
        out.write("<td>");
        out.write(escape(resultString));
        out.write("</td>");
        out.write("<td class='" + msgClass + "'>");
        out.write(escape(msg));
        out.write("</td>");
        out.write("<td>");
        if (noteNode != null) out.write(escape(noteNode));
        out.write("</td>");
        out.write("<td>");
        if (note != null) out.write(escape(note));
        out.write("</td>");

    }

    public static String escape(String s)
    {
        StringBuilder out = new StringBuilder(Math.max(16, s.length()));
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (c > 127 || c == '"' || c == '<' || c == '>' || c == '&')
            {
                out.append("&#");
                out.append((int) c);
                out.append(';');
            }
            else
            {
                out.append(c);
            }
        }
        return out.toString();
    }

}
/**
 * 
 */
package org.grapeqin.cachecontrol;

import java.util.regex.Matcher;

import junit.framework.TestCase;

import org.junit.Test;

import static java.lang.System.out;

/**
 * @author Ternence
 * @create 2015年1月16日
 */
public class ResourceVersionMojoTest extends TestCase {

    @Test
    public void test() {}

    @Test
    public void testCssPattern() {
        Matcher m = ResourceMojo.CSS_PATTERN
                        .matcher("<link type=\"text/css\" rel=\"stylesheet\"	href=\"/wiki/s/d41d8cd98f00b204e9800998ecf8427e/en_GB-1988229788/4731/0a2d13ba65b62df25186f4b87e6c642af1792689.1/86/_/download/superbatch/css/batch.css\" media=\"all\">");

        assertTrue(m.find());
        assertEquals("/wiki/s/d41d8cd98f00b204e9800998ecf8427e/en_GB-1988229788/4731/0a2d13ba65b62df25186f4b87e6c642af1792689.1/86/_/download/superbatch/css/batch.css",
                        m.group(1));

        m = ResourceMojo.CSS_PATTERN
                        .matcher("<link type=\"text/css\"\r\n rel=\"stylesheet\"	href=\"/wiki/s/d41d8cd98f00b204e9800998ecf8427e/en_GB-1988229788/4731/0a2d13ba65b62df25186f4b87e6c642af1792689.1/86/_/download/superbatch/css/batch.css\" media=\"all\">");
        assertTrue(m.find());
        assertEquals("/wiki/s/d41d8cd98f00b204e9800998ecf8427e/en_GB-1988229788/4731/0a2d13ba65b62df25186f4b87e6c642af1792689.1/86/_/download/superbatch/css/batch.css",
                        m.group(1));

        m = ResourceMojo.CSS_PATTERN
                        .matcher("<link href=\"https://assets-cdn.github.com/assets/github-31ae56d54eb0503aae78baa852c9c421ee81d18414cd78e0b4b536cc46c24858.css\" media=\"all\" rel=\"stylesheet\" type=\"text/css\" />");
        assertTrue(m.find());
        assertEquals("https://assets-cdn.github.com/assets/github-31ae56d54eb0503aae78baa852c9c421ee81d18414cd78e0b4b536cc46c24858.css", m.group(1));
    }

    @Test
    public void testJsPattern() {
        Matcher m = ResourceMojo.JS_PATTERN.matcher("<script src=\"http://www.google-analytics.com/urchin.js\" type=\"text/javascript\"></script>");

        assertTrue(m.find());
        assertEquals("http://www.google-analytics.com/urchin.js", m.group(1));

        m = ResourceMojo.JS_PATTERN.matcher("<script type=\"text/javascript\">_uacct = \"UA-140879-1\";\r\n        urchinTracker();</script>");

        assertFalse(m.find());

    }

    @Test
    public void testJsNewFeaturesPattern(){
        String js = "<script src=\"http://www.google-analytics.com/urchin.js\" type=\"text/javascript\"></script>"
                        +"<script src=\"http://www.google-analytics.com/lschin.js\" type=\"text/javascript\"></script>";
        out.println("original js declare : "+ js);
        Matcher m = ResourceMojo.JS_PATTERN.matcher(js);
        int counter = 0;
        while (m.find()){
            out.println("match js : " + m.group());
            counter++;
        }
        assertTrue(counter == 2);

        js = "<script src=\"http://www.google-analytics.com/urchin.js?v=1.0.3\" type=\"text/javascript\"></script>";
        out.println("original js declare : "+ js);
        m = ResourceMojo.JS_PATTERN.matcher(js);
        assertTrue(m.find());
        out.println("match js : " + m.group());
    }

    @Test
    public void testImagePattern() {
        Matcher m = ResourceMojo.IMAGES_PATTERN.matcher("<img src=\"../../images/maven-logo-2.gif\" alt=\"\" />");

        assertTrue(m.find());
        assertEquals("../../images/maven-logo-2.gif", m.group(1));

        m = ResourceMojo.IMAGES_PATTERN.matcher("<img alt=\"Built by Maven\" src=\"./images/logos/maven-feather.png\"/>");

        assertTrue(m.find());
        assertEquals("./images/logos/maven-feather.png", m.group(1));

        m = ResourceMojo.IMAGES_PATTERN.matcher("htm += \"<img src='<%=resourceUrlPrefix %>\" + item.iconUrl + \"'/>\";");

        if (m.find()) {
            System.out.println(m.group(1));
            m.reset();
        }
        assertFalse(m.find());

    }
}

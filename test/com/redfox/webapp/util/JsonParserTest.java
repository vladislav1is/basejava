package com.redfox.webapp.util;

import com.redfox.webapp.model.AbstractSection;
import com.redfox.webapp.model.Resume;
import com.redfox.webapp.model.TextSection;
import org.junit.Assert;
import org.junit.Test;

import static com.redfox.webapp.TestData.R1;

public class JsonParserTest {

    @Test
    public void read() {
        String json = JsonParser.write(R1, Resume.class);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(R1, resume);
    }

    @Test
    public void write() {
        AbstractSection section1 = new TextSection("Objective1");
        String json = JsonParser.write(section1, AbstractSection.class);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(section1, section2);
    }
}
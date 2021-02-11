package com.redfox.webapp.web;

import com.redfox.webapp.Config;
import com.redfox.webapp.model.*;
import com.redfox.webapp.storage.SqlStorage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AddResumeServlet extends HttpServlet {

    private static SqlStorage STORAGE; // Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        STORAGE = Config.get().getStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Resume resume = new Resume(request.getParameter("fullName"));
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            }
        }
        for (SectionType type : SectionType.values()) {
            String sectionContent = request.getParameter(type.name());
            AbstractSection section;
            if (sectionContent != null && sectionContent.trim().length() != 0) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        section = new TextSection(sectionContent.trim());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        String[] strs = sectionContent.split("\n");
                        ArrayList<String> strings = new ArrayList<>();
                        for (int i = 0; i < strs.length; i++) {
                            String tmpStr = strs[i].trim();
                            if (tmpStr.length() != 0) {
                                strings.add(strs[i]);
                            }
                        }
                        section = new ListTextSection(strings);
                        break;
                    default:
                        throw new IllegalArgumentException("Section type " + type + " is illegal");
                }
                resume.addSection(type, section);
            }
        }
        STORAGE.save(resume);
        response.sendRedirect("resume");
    }
}

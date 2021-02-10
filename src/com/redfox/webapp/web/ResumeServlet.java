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

public class ResumeServlet extends HttpServlet {

    private static SqlStorage STORAGE; // Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        STORAGE = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume = STORAGE.get(uuid);
        resume.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
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
                            String tmpStr = strs[i];
                            if (tmpStr.length() != 0) {
                                strings.add(strs[i].trim());
                            }
                        }
                        section = new ListTextSection(strings);
                        break;
                    default:
                        throw new IllegalArgumentException("Section type " + type + " is illegal");
                }
                resume.addSection(type, section);
            } else {
                resume.getSections().remove(type);
            }
        }
        STORAGE.update(resume);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", STORAGE.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
        }
        Resume resume;
        switch (action) {
            case "delete":
                STORAGE.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = STORAGE.get(uuid);
                break;
            case "add":
                request.getRequestDispatcher("/WEB-INF/jsp/add.jsp").forward(request, response);
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}

package com.redfox.webapp.web;

import com.redfox.webapp.Config;
import com.redfox.webapp.model.ContactType;
import com.redfox.webapp.model.Resume;
import com.redfox.webapp.storage.SqlStorage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
/*
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String uuid = request.getParameter("uuid");
        response.getWriter().write(uuid == null ? printResumes() : printResumeByUuid(uuid));
*/
    }

    private String printResumes() {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<html>\n" +
                        "<head>\n" +
                        "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                        "    <link rel=\"stylesheet\" href=\"css/style.css\">\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<section>\n" +
                        "<table border=\"1\" cellpadding=\"8\" cellspacing=\"0\">\n" +
                        "    <caption>Список всех резюме</caption>\n" +
                        "    <tr>\n" +
                        "        <th>Имя</th>\n" +
                        "        <th>Email</th>\n" +
                        "    </tr>\n");
        for (Resume resume : STORAGE.getAllSorted()) {
            sb.append(
                    "<tr>\n" +
                            "     <td><a href=\"resume?uuid=" + resume.getUuid() + "\">" + resume.getFullName() + "</a></td>\n" +
                            "     <td>" + resume.getContact(ContactType.MAIL) + "</td>\n" +
                            "</tr>\n");
        }
        sb.append("</table>\n" +
                "</section>\n" +
                "</body>\n" +
                "</html>\n");
        return sb.toString();
    }

    private String printResumeByUuid(String uuid) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "<html>\n" +
                        "<head>\n" +
                        "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                        "    <link rel=\"stylesheet\" href=\"css/style.css\">\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<section>\n" +
                        "<table border=\"1\" cellpadding=\"8\" cellspacing=\"0\">\n" +
                        "   <caption>Резюме по uuid</caption>\n" +
                        "   <tr>\n" +
                        "      <th>uuid</th>\n" +
                        "      <th>ФИО</th>\n" +
                        "   </tr>");
        Resume resume = STORAGE.get(uuid);
        sb.append(
                "  <tr>\n" +
                        "    <td>" + resume.getUuid() + "</td>\n" +
                        "    <td>" + resume.getFullName() + "</td>\n" +
                        "  </tr>");
        sb.append("</table>\n" +
                "</section>\n" +
                "</body>\n" +
                "</html>\n");
        return sb.toString();
    }
}

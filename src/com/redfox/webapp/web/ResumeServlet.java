package com.redfox.webapp.web;

import com.redfox.webapp.Config;
import com.redfox.webapp.model.Resume;
import com.redfox.webapp.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {

    private static final SqlStorage STORAGE = Config.get().getStorage();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
//        String name = request.getParameter("name");
//        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + "!");

        String uuid = request.getParameter("uuid");
        response.getWriter().write(uuid == null ? printResumes() : printResumeByUuid(uuid));
    }

    private String printResumes() {
        StringBuilder sb = new StringBuilder();
        sb.append("" +
                "<style>\n" +
                "   table, th, td {\n" +
                "       border: 1px solid black;\n" +
                "       border-collapse: collapse;\n" +
                "   }\n" +
                "   th, td {\n" +
                        "  padding: 5px;\n" +
                "   }\n" +
                "</style>");
        sb.append("<table style=\"width:70%\">");
        sb.append("" +
                " <caption>Resumes</caption>\n" +
                " <tr>\n" +
                "   <th>uuid</th>\n" +
                "   <th>full name</th>\n" +
                " </tr>");
        for (Resume r : STORAGE.getAllSorted()) {
            sb.append("" +
                    "  <tr>\n" +
                    "    <td>" + r.getUuid() + "</td>\n" +
                    "    <td>" + r.getFullName() + "</td>\n" +
                    "  </tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }

    private String printResumeByUuid(String uuid) {
        StringBuilder sb = new StringBuilder();
        sb.append("" +
                "<style>\n" +
                "   table, th, td {\n" +
                "       border: 1px solid black;\n" +
                "       border-collapse: collapse;\n" +
                "   }\n" +
                "   th, td {\n" +
                "  padding: 5px;\n" +
                "   }\n" +
                "</style>");
        sb.append("<table style=\"width:70%\">");
        sb.append("" +
                " <caption>Resume by uuid</caption>\n" +
                " <tr>\n" +
                "   <th>uuid</th>\n" +
                "   <th>full name</th>\n" +
                " </tr>");
        Resume resume = STORAGE.get(uuid);
        sb.append("" +
                "  <tr>\n" +
                "    <td>" + resume.getUuid() + "</td>\n" +
                "    <td>" + resume.getFullName() + "</td>\n" +
                "  </tr>");
        sb.append("</table>");
        return sb.toString();
    }
}

package com.redfox.webapp.web;

import com.redfox.webapp.Config;
import com.redfox.webapp.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
//        String name = request.getParameter("name");
//        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + "!");

        SqlStorage storage = Config.get().getStorage();


//        response.getWriter().write("" +
//                "\t\t\t   <table border=\"1\">\n" +
//                "\t\t\t\t <tr>\n" +
//                "\t\t\t\t   <td>Cell 1</td>\n" +
//                "\t\t\t\t   <td>Cell 2</td>\n" +
//                "\t\t\t\t </tr>\n" +
//                "\t\t\t\t <tr>\n" +
//                "\t\t\t\t   <td>Cell 3</td>\n" +
//                "\t\t\t\t   <td>Cell 4</td>\n" +
//                "\t\t\t\t </tr>\n" +
//                "\t\t\t   </table>"
//        );
        response.getWriter().write("<h1>" + 1 + "<h1>");
        System.out.println("HelloWorld");
        response.getWriter().write("<h1></h1");


        //String uuid = request.getParameter("uuid");
        //response.getWriter().write(uuid == null ? printResumes() : printResumeByUuid(uuid));
    }

    private String printResumes() {
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>resumes</h1>");

        return sb.toString();
    }

    private String printResumeByUuid(String uuid) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>resumeTest</h1>");
        return sb.toString();
    }
}

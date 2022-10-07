package exercise.servlet;

import exercise.Data;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.stream.Collectors;

import static exercise.Data.getCompanies;

public class CompaniesServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        String searchParam = request.getParameter("search");

        PrintWriter pw = response.getWriter();

        List<String> companies = Data.getCompanies();

        if (null != searchParam) {
            companies = companies.stream()
                    .filter(value -> value.contains(searchParam))
                    .collect(Collectors.toList());

        }

        if (companies.isEmpty()) {
            pw.print("Companies not found");
        }

        for (String company : companies) {
            pw.println(company);
        }
    }
}

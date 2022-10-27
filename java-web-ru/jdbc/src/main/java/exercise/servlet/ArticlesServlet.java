package exercise.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

import exercise.TemplateEngineUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;


public class ArticlesServlet extends HttpServlet {

    private String getId(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return null;
        }
        String[] pathParts = pathInfo.split("/");
        return ArrayUtils.get(pathParts, 1, null);
    }

    private String getAction(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return "list";
        }
        String[] pathParts = pathInfo.split("/");
        return ArrayUtils.get(pathParts, 2, getId(request));
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        String action = getAction(request);

        switch (action) {
            case "list":
                showArticles(request, response);
                break;
            default:
                showArticle(request, response);
                break;
        }
    }

    private void showArticles(HttpServletRequest request,
                              HttpServletResponse response)
            throws IOException, ServletException {

        ServletContext context = request.getServletContext();
        Connection connection = (Connection) context.getAttribute("dbConnection");
        // BEGIN
        List<Map<String, String>> articles = new ArrayList<>();

        int page = 0;
        if (null != request.getParameter("page")) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int limit = 10;

        if (page == 0 || page < 0) {
            page = 1;
        }

        int offset = (limit * (page - 1));


        String query = "SELECT id, title FROM articles ORDER BY id ASC LIMIT ? OFFSET ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                articles.add(Map.of(
                                // Так можно получить значение нужного поля в текущей строке
                                "id", rs.getString("id"),
                                "title", rs.getString("title")
                        )
                );
            }

        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        request.setAttribute("articles", articles);
        request.setAttribute("prev", page - 1);
        request.setAttribute("next", page + 1);
        // END
        TemplateEngineUtil.render("articles/index.html", request, response);
    }

    private void showArticle(HttpServletRequest request,
                             HttpServletResponse response)
            throws IOException, ServletException {

        ServletContext context = request.getServletContext();
        Connection connection = (Connection) context.getAttribute("dbConnection");
        // BEGIN
        String id = this.getId(request);

        String query = "SELECT * FROM articles WHERE id = ?";

        if (id == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> article = null;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(id));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                article = Map.of(
                        // Так можно получить значение нужного поля в текущей строке
                        "id", rs.getString("id"),
                        "title", rs.getString("title"),
                        "body", rs.getString("body")
                );
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        request.setAttribute("article", article);

        // END
        TemplateEngineUtil.render("articles/show.html", request, response);
    }

}

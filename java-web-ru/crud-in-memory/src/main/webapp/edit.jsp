<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Edit user</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
            crossorigin="anonymous">
    </head>
    <body>
        <div class="container">
            <a href="/users">Все пользователи</a>
            <div class="container">
                <a href="/users">Все пользователи</a>

                <form action='/users/edit?id=${user.get("id")}' method="post">
                    <div class="form-group">
                        <label for="email">Email address</label>
                        <input type="email" class="form-control" id="email" value="${user.get("email")}" placeholder="Enter email">
                    </div>
                    <div class="form-group">
                        <label for="firstName">first name</label>
                        <input type="text" class="form-control" id="firstName" value="${user.get("firstName")}" placeholder="firstName">
                    </div>
                    <div class="form-group">
                        <label for="lastName">last name</label>
                        <input type="text" class="form-control" id="lastName" value="${user.get("lastName")}" placeholder="lastName">
                    </div>
                    <button type="submit" class="btn btn-danger">Создать</button>
                </form>
            </div>
        </div>
    </body>
</html>

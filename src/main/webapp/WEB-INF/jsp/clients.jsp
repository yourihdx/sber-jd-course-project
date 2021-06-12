<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Клиенты</title>
</head>
<body>
<h1>Список всех клиентов</h1>

<table border="1">
    <tr>
        <td>Полное имя</td>
        <td>Дата рождения</td>
        <td>Номер телефона</td>
        <td>Электронная почта</td>
        <td>Серия/номер паспорта</td>

    </tr>

    <c:forEach var="clients" items="${clientList}">
        <tr>
            <td><c:out value="${clients.fullName}"/></td>
            <td><c:out value="${clients.birthDate}"/></td>
            <td><c:out value="${clients.phoneNumber}"/></td>
            <td><c:out value="${clients.email}"/></td>
            <td><c:out value="${clients.passportSeriesNum}"/></td>
            <td><a href="/clients/${clients.id}">Edit</a></td>
            <td><a href="/delete/${clients.id}">Delete</a></td>
        </tr>
    </c:forEach>

</table>
<br><br>
<%--<a href=addNewClient>Add new field</a>--%>

<h2>Введите данные, чтобы добавить нового клиента</h2>

<form action="/clients" method="POST">

    <table bordercolor="white" border="1" width="40%">
        <tr>
            <td><label>Полное имя</label></td>
            <td><input type="text" name="fullName"></td>
        </tr>
        <br>
        <tr>
            <td><label>Дата рождения</label></td>
            <td><input type="text" name="birthDate"></td>
        </tr>
        <br>
        <td><label>Номер телефона</label></td>
        <td><input type="text" name="phoneNumber"></td>
        <tr>
            <td><label>Электронная почта</label></td>
            <td><input type="text" name="email"></td>
        </tr>
        <br>
        <tr>
            <td><label>Серия/номер паспорта</label></td>
            <td><input type="text" name="passportSeriesNum"></td>
        </tr>
    </table>
    <br>

    <input type="submit" value="Сохранить">

</form>

</body>
</html>
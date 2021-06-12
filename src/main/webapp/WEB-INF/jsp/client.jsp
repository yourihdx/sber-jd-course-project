<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Клиент</title>
</head>

<body>
<h1>Клиент: ${client.fullName}</h1>

<table border="1">
    <tr>
        <td>Полное имя</td>
        <td>Дата рождения</td>
        <td>Номер телефона</td>
        <td>Электронная почта</td>
        <td>Серия/номер паспорта</td>

    </tr>
    <tr>
        <td><c:out value="${client.fullName}"/></td>
        <td><c:out value="${client.birthDate}"/></td>
        <td><c:out value="${client.phoneNumber}"/></td>
        <td><c:out value="${client.email}"/></td>
        <td><c:out value="${client.passportSeriesNum}"/></td>
        <td><a href="/clients">Cancel</a></td>
        <td><a href="/delete/${client.id}">Delete</a></td>

    </tr>


</table>
<br><br>
<%--<a href=addNewClient>Add new field</a>--%>

<h2>Введите данные, чтобы отредактировать клиента</h2>

<form action="/clients/${id}" method="POST">

    <table bordercolor="white" border="1" width="40%">
        <tr>
            <td> <label>Полное имя</label></td>
            <td><input type="text" name="fullName"></td>
        </tr><br>
        <tr>
            <td><label>Дата рождения</label></td>
            <td><input type="text" name="birthDate"></td>
        </tr><br>
        <td> <label>Номер телефона</label></td>
        <td> <input type="text" name="phoneNumber"></td>
        <tr>
            <td>  <label>Электронная почта</label></td>
            <td>  <input type="text" name="email"></td>
        </tr><br>
        <tr>
            <td>  <label>Серия/номер паспорта</label></td>
            <td>  <input type="text" name="passportSeriesNum"></td>
        </tr>
    </table>
    <br>

    <input type="submit" value="Сохранить">

</form>

</body>
</html>
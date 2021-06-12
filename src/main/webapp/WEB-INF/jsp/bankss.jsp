<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Банки</title>
</head>
<body>
<h1>Список всех банков</h1>

<table border="1">
    <tr>
        <td>Название банка</td>
    </tr>

    <c:forEach var="banks" items="${bankList}">
        <tr>
            <td><c:out value="${banks.bankName}"/></td>

            <td><a href="/bankss/${banks.id}">Edit</a></td>
            <td><a href="/deleteBank/${banks.id}">Delete</a></td>
        </tr>
    </c:forEach>

</table>
<br><br>

<h2>Введите данные, чтобы добавить новый банк</h2>

<form action="/bankss" method="POST">

    <table bordercolor="white" border="1" width="40%">

        <tr>
            <td><label>Название банка</label></td>
            <td><input type="text" name="bankName"></td>
        </tr>

    </table><br>

    <input type="submit" value="Сохранить">

</form>

</body>
</html>
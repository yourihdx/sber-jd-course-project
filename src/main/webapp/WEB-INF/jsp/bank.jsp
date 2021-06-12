<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Банк</title>
</head>

<body>
<h1>Банк: ${bank.bankName}</h1>

<table border="1">
    <tr>
        <td>Название банка</td>
    </tr>
    <tr>
        <td><c:out value="${bank.bankName}"/></td>

        <td><a href="/bankss">Cancel</a></td>
        <td><a href="/deleteBank/${bank.id}">Delete</a></td>

    </tr>


</table>
<br><br>
<%--<a href=addNewClient>Add new field</a>--%>

<h2>Введите данные, чтобы отредактировать банк</h2>

<form action="/bankss/${id}" method="POST">
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
<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Кредит</title>
</head>

<body>
<h1>Кредит: ${credit.id}</h1>

<table border="1">
    <tr>
        <td>id банка</td>
        <td>Максимальная сумма</td>
        <td>Максимальный период</td>
        <td>Процентная ставка</td>

    </tr>

    <tr>
        <td><c:out value="${credit.bankId}"/></td>
        <td><c:out value="${credit.maxSum}"/></td>
        <td><c:out value="${credit.maxPeriod}"/></td>
        <td><c:out value="${credit.percentRate}"/></td>
        <td><a href="/credits">Cancel</a></td>
        <td><a href="/deleteCredit/${credit.id}">Delete</a></td>
    </tr>


</table>
<br><br>
<%--<a href=addNewClient>Add new field</a>--%>

<h2>Введите данные, чтобы отредактировать кредитное предложение</h2>

<form action="/credits/${id}" method="POST">
    <table bordercolor="white" border="1" width="40%">
    <tr >
        <td><label>id банка</label></td>
        <td><input type="text" name="bankId"></td>
    </tr><br>
    <tr>
        <td><label>Максимальная сумма</label></td>
        <td><input type="text" name="maxSum"></td>
    </tr><br>
    <tr>
        <td><label>Максимальный период</label></td>
        <td><input type="text" name="maxPeriod"></td>
    </tr><br>
    <tr>
        <td><label>Процентная ставка</label></td>
        <td><input type="text" name="percentRate"></td>
    </tr>
    </table><br>
    <input type="submit" value="Сохранить">

</form>

</body>
</html>
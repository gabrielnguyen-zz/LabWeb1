

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New Account</title>
    </head>
    <body>
        <h1>Registration</h1>
        <form action="DispatchServlet" method="POST">
            <c:set var="errors" value="${requestScope.CREATEERROR}"/>
            Username* <input type="text" name="txtUser" value="${param.txtUser}"  style="margin-left: 30px"/> (6 - 20 chars)  <br/>
            <c:if test="${not empty errors.usernameLengthErr}">
                <font color="red">
                ${errors.usernameLengthErr}
                </font> <br/>
            </c:if>
            <c:if test="${not empty errors.usernameFormatErr}">
                <font color="red">
                ${errors.usernameFormatErr}
                </font> <br/>
            </c:if>
            <c:if test="${not empty errors.usernameIsExisted}">
                <font color="red">
                ${errors.usernameIsExisted}
                </font> <br/>
            </c:if>

            Password* <input type="password" name="txtPass" value="" style="padding-right: 7px; margin-left: 34px"/> (6 - 30 chars) <br/>
            <c:if test="${not empty errors.passwordLengthErr}">
                <font color="red">
                ${errors.passwordLengthErr}
                </font> <br/>
            </c:if>
            Fullname* <input type="text" name="txtFullname" value="${param.txtFullname}" style="margin-left: 33px; padding-right: 0.5px"/> (2 - 10 chars) <br/>
            <c:if test="${not empty errors.fullNameLengthErr}">
                <font color="red">
                ${errors.fullNameLengthErr}
                </font> <br/>
            </c:if>
            Phone* <input type="text" name="txtPhone" value="${param.txtPhone}" style="margin-left: 14px"/> (2 - 30 chars)<br/>
            <c:if test="${not empty errors.phoneFormatErr}">
                <font color="red">
                ${errors.phoneFormatErr}
                </font> <br/>
            </c:if>

            Address* <input type="text" name="txtAddress" value="${param.txtAddress}" style="margin-left: 14px"/> (2 - 30 chars)<br/>
            <c:if test="${not empty errors.addressErr}">
                <font color="red">
                ${errors.addressErr}
                </font> <br/>
            </c:if>
            Role*  <select name="role" id="category">
                <option value="emp">Employee</option>
                <option value="lead">Leader</option>
            </select>

            <input type="submit" value="Create New Account" name="btAction" style="margin: 20px"/>
            <input type="reset" value="Reset" style="margin: 20px"/>
        </form>

    </body>
</html>

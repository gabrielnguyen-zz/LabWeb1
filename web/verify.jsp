<%-- 
    Document   : index
    Created on : May 10, 2021, 5:24:32 PM
    Author     : Gabriel Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script src='https://www.google.com/recaptcha/api.js?hl=vi'></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://apis.google.com/js/platform.js" async defer></script>
<%@ page import = "javax.servlet.RequestDispatcher" %>
<meta name="google-signin-client_id"
     content="1209909230-hl6vmv8u8tu5scfr45gnb5cr1es5km3i.apps.googleusercontent.com">
<html>
    <head>
        <title>Verify</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>Verify Page </h1>
        <h3>Please check your email to get the verification code</h3>
        <form action="DispatchServlet" method="POST">
            Verification Code : <input type="text" name="txtVerify" value="" style="margin-left: 30px" required="true"/> <br>
            <input type="hidden" name="txtUsername" value="${requestScope.USERNAME}">
            <input type="submit" value="Verify" name="btAction" />
            <input type="reset" value="Reset" style="margin-left: 20px"/>
            
        </form>        
        <%
            if (request.getAttribute("VERIFYERROR") != null) {
        %>
        <font color="red">
        <%=request.getAttribute("VERIFYERROR")%>
        </font>
        <% }%>
        
        <br>
        
    </body>
</html>

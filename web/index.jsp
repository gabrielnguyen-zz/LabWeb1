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
        <title>Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1> Login Page </h1>
        <form action="DispatchServlet" method="POST">
            Username <input type="text" name="txtUsername" value="" style="margin-left: 30px"/> <br>
            Password <input type="password" name="txtPassword" value="" style="margin-left: 34px"/> <br>
            <input type="submit" value="Login" name="btAction" />
            <input type="reset" value="Reset" style="margin-left: 20px"/>
            <div class="g-recaptcha"
                 data-sitekey="6LfpmM4aAAAAAFcCSalEL046ssriG4pRRv_q8KsL"></div>
        </form>        
        <%
            if (request.getAttribute("ERROR") != null) {
        %>
        <font color="red">
        <%=request.getAttribute("ERROR")%>
        </font>
        <% }%>
        <br>
        <div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
    <script>
      function onSignIn(googleUser) {
        // Useful data for your client-side scripts:
        var profile = googleUser.getBasicProfile();
        console.log("ID: " + profile.getId()); // Don't send this directly to your server!
        console.log('Full Name: ' + profile.getName());
        console.log('Given Name: ' + profile.getGivenName());
        console.log('Family Name: ' + profile.getFamilyName());
        console.log("Image URL: " + profile.getImageUrl());
        console.log("Email: " + profile.getEmail());
        window.location.href = 'LoginGoogleServlet?email=' + profile.getEmail();
        // The ID token you need to pass to your backend:
        var id_token = googleUser.getAuthResponse().id_token;
        console.log("ID Token: " + id_token);
        
      }
    
    </script>
        <br>
        <a href="createNewAccount.html">No account. Click here to sign up</a>
    </body>
</html>

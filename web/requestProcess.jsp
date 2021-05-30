<%-- 
    Document   : search
    Created on : May 10, 2021, 5:27:19 PM
    Author     : Gabriel Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Search</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    </script>
</head>
<body>
    <form action="logout">
        <c:forEach var="cookieValue" items="${cookie}">
            <c:set var="tmp" value="${cookieValue.key}"/>
            <c:if test="${tmp != 'JSESSIONID'}">
                <c:set var="username" value="${tmp}"/>
            </c:if>
        </c:forEach>

        <font color="blue">
        Welcome, ${ACCOUNTNAME}
        </font>
        <input type="submit" value="Logout" name="btAction" />
    </form>
    <form action="DispatchServlet" method="POST">
        Keyword:  <input type="text" name="txtSearchValue" value="${param.txtSearchValue}" /> 
        <label for="cars">Choose a status:</label>

        <select name="status" id="category">
            <option value="New">New</option>
            <option value="Accpet">Accept</option>
            <option value="Delete">Delete</option>
        </select>
        <input type="submit" value="Search Process" name="btAction" />
    </form>
    <br>

    <c:set var="searchValue" value="${param.txtSearchValue}"/>
    
        <c:set var="result" value="${requestScope.RESULTPROCESS}"/>
        <c:if test="${not empty result}">
            <%
                if (request.getAttribute("PROCESSERROR") != null) {
            %>
            <font color="red">
            <%=request.getAttribute("PROCESSERROR")%>
            </font>
            <% }%>
            <%-- <form action="deleteConfirm"> --%>
            <form action="DispatchServlet">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Name</th>
                            <th>Color</th>
                            <th>Quantity</th>
                            <th>From Date</th>
                            <th>End Date</th>
                            <th>Request Date</th>
                            <th>Updated Date</th>
                            <th>Accept</th>
                            <th>Delete</th>
                            <th>Description</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${result}" varStatus="counter">
                            <tr>
                                <td>${counter.count}</td>
                                <td>
                                    ${dto.name}
                                </td>
                                <td>
                                    ${dto.color}
                                </td>
                                <td>
                                    ${dto.quantity}

                                </td> 
                                <td>
                                    ${dto.from}
                                </td>
                                <td>
                                    ${dto.end}
                                </td>
                                <td>
                                    ${dto.requestDate}
                                </td>
                                <td>
                                    ${dto.updatedDate}
                                </td>
                                <td>
                                    <input type="hidden" value="${dto.id}" name="txtId" />
                                    <input type="submit" value="AcceptRequest" name="btAction" />
                                </td>
                                <td>
                                    <input type="hidden" value="${dto.id}" name="txtId" />
                                    <input type="submit" value="DeleteRequest" name="btAction" />
                                </td>
                                <td>
                                    <input type="text" value="" name="txtDescription"> 
                                </td>
                            </tr>

                        </c:forEach>
                    </tbody>
                </table>
                <%--For displaying Previous link except for the 1st page --%>
                <c:if test="${currentPageProcess != 1}">
                    <td><a href="SearchProcessServlet?page=${currentPageProcess - 1}&txtSearchValue=${param.txtSearchValue}&status=${param.status}">Previous</a></td>
                </c:if>

                <%--For displaying Page numbers. 
                The when condition does not display a link for the current page--%>
                <table border="1" cellpadding="5" cellspacing="5">
                    <tr>
                        <c:forEach begin="1" end="${noOfPagesProcess}" var="i">
                            <c:choose>
                                <c:when test="${currentPageProcess eq i}">
                                    <td>${i}</td>
                                </c:when>
                                <c:otherwise>
                                    <td><a href="SearchProcessServlet?page=${i}&txtSearchValue=${param.txtSearchValue}&status=${param.status}">${i}</a></td>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                    </tr>
                </table>

                <%--For displaying Next link --%>
                <c:if test="${currentPageProcess lt noOfPagesProcess}">
                    <td><a href="SearchProcessServlet?page=${currentPageProcess + 1}&txtSearchValue=${param.txtSearchValue}&status=${param.status}">Next</a></td>
                </c:if>
                    <br>
                <input type="hidden" name="lastSearch" value="${param.txtSearchValue}" />
            </form>

        </c:if>
        <c:if test="${empty result}">
            <h2>No record is macthed!!!!</h2>
        </c:if>
    
    <script src="js/pagination.js"></script>

</body>
</html>

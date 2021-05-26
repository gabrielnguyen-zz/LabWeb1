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
        <title>Booking History</title>
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
        <br>
    <c:set var="result" value="${requestScope.RESULTHISTORY}"/>
    <c:if test="${not empty result}">
        <form action="DispatchServlet">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Name</th>
                        <th>Color</th>
                        <th>Category</th>
                        <th>Quantity</th>
                        <th>Request Date</th>
                        <th>Updated Date</th>
                        <th>From Date</th>
                        <th>End Date</th>
                        <th>Status</th>
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
                                ${dto.category}
                            </td>
                            <td>
                                ${dto.quantity}
                            </td>
                            <td>
                                ${dto.requestDate}
                            </td>
                            <td>
                                ${dto.updatedDate}
                            </td>
                            <td>
                                ${dto.from}
                            </td>
                            <td>
                                ${dto.end}
                            </td>
                            <td>
                                ${dto.status}
                            </td>
                            <td>
                                ${dto.description}
                            </td>
                        </tr>

                    </c:forEach>
                </tbody>
            </table>
            <%--For displaying Previous link except for the 1st page --%>
            <c:if test="${currentPageHistory != 1}">
                <td><a href="HistoryServlet?pageHistory=${currentPageHistory - 1}">Previous</a></td>
            </c:if>

            <%--For displaying Page numbers. 
            The when condition does not display a link for the current page--%>
            <table border="1" cellpadding="5" cellspacing="5">
                <tr>
                    <c:forEach begin="1" end="${noOfPagesHistory}" var="i">
                        <c:choose>
                            <c:when test="${currentPageHistory eq i}">
                                <td>${i}</td>
                            </c:when>
                            <c:otherwise>
                                <td><a href="HistoryServlet?pageHistory=${i}">${i}</a></td>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                </tr>
            </table>

            <%--For displaying Next link --%>
            <c:if test="${currentPageHistory lt noOfPagesHistory}">
                <td><a href="HistoryServlet?pagHistorye=${currentPageHistory + 1}">Next</a></td>
            </c:if>
            <br>
        </form>

    </c:if>
    <c:if test="${empty result}">
        <h2>No record is macthed!!!!</h2>
    </c:if>

    <script src="js/pagination.js"></script>

</body>
</html>

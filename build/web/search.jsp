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
        <label for="cars">Choose a category:</label>

        <select name="category" id="category">
            <option value="1">Văn phòng phẩm</option>
            <option value="2">Dụng cụ văn phòng</option>
        </select>
        <div class="book-date one-third">
            <label for="check-in">From date : </label>
            <input type="text" id="checkin_date" class="form-control" placeholder="YYYY/MM/DD" name="txtFrom" value="${param.txtFrom}">

        </div>

        <div class="book-date one-third">
            <label for="check-out">End date :  </label>
            <input type="text" id="checkout_date" class="form-control" placeholder="YYYY/MM/DD" name="txtEnd" value ="${param.txtEnd}">
        </div>
        <input type="submit" value="Search" name="btAction" />
    </form>
    <br>

    <c:set var="searchValue" value="${param.txtSearchValue}"/>
    
    <c:set var="fromDate" value="${param.txtFrom}"/>
    <c:set var="endDate" value="${param.txtEnd}"/>
   
    
    <c:if test="${not empty fromDate}"> 
        <c:set var="result" value="${requestScope.RESULTSEARCH}"/>
        <c:if test="${not empty result}">
            <font color="blue">
            Selected resources must have a same type and same quantity
            <br>
            If you want to book more than 1 day, please choose days in order!!!
            <br>
            Please modified the quantity of each selected date and resource that you want to book!!!
            <br>
            </font>
            <%
                if (request.getAttribute("BOOKINGERROR") != null) {
            %>
            <font color="red">
            <%=request.getAttribute("BOOKINGERROR")%>
            </font>
            <% }%>
            <%-- <form action="deleteConfirm"> --%>
            <form action="DispatchServlet">
                <c:set var="category" value="${param.category}"/>
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Name</th>
                            <th>Color</th>
                            <th>Quantity</th>
                            <th>From Date</th>
                            <th>End Date</th>
                            <th>Booking</th>

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
                                    <input type="checkbox" name="chkBooking" value="${dto.quantity},${dto.from},${dto.name},${dto.color}" />
                                </td>

                            </tr>

                        </c:forEach>
                    </tbody>
                </table>
                <%--For displaying Previous link except for the 1st page --%>
                <c:if test="${currentPage != 1}">
                    <td><a href="SearchServlet?page=${currentPage - 1}&txtSearchValue=${param.txtSearchValue}&category=${param.category}&txtFrom=${param.txtFrom}&txtEnd=${param.txtEnd}">Previous</a></td>
                </c:if>

                <%--For displaying Page numbers. 
                The when condition does not display a link for the current page--%>
                <table border="1" cellpadding="5" cellspacing="5">
                    <tr>
                        <c:forEach begin="1" end="${noOfPages}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <td>${i}</td>
                                </c:when>
                                <c:otherwise>
                                    <td><a href="SearchServlet?page=${i}&txtSearchValue=${param.txtSearchValue}&category=${param.category}&txtFrom=${param.txtFrom}&txtEnd=${param.txtEnd}">${i}</a></td>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                    </tr>
                </table>

                <%--For displaying Next link --%>
                <c:if test="${currentPage lt noOfPages}">
                    <td><a href="SearchServlet?page=${currentPage + 1}&txtSearchValue=${param.txtSearchValue}&category=${param.category}&txtFrom=${param.txtFrom}&txtEnd=${param.txtEnd}">Next</a></td>
                </c:if>
                    <br>
                <input type="hidden" name="lastSearch" value="${param.txtSearchValue}" />
                <input type="hidden" name="lastFrom" value="${param.txtFrom}" />
                <input type="hidden" name="lastEnd" value="${param.txtEnd}" />
                <input type="hidden" name="latCate" value="${param.category}" />
                <label>Quantity you want to book : </label><br><input type="number" name="txtQuantity" value="">
                <input type="submit" value="Booking" name="btAction" />
            </form>

        </c:if>
        <c:if test="${empty result}">
            <h2>No record is macthed!!!!</h2>
        </c:if>
    </c:if>
    <script src="js/pagination.js"></script>

</body>
</html>

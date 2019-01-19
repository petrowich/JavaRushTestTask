<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <meta charset="UTF-8" />
    <style><%@include file="/table.css"%></style>
    <style><%@include file="/pagination.css"%></style>
    <title>TestTask</title>
</head>
<body>
<header>
    <h1 align="center">JavaRush Test Task</h1>
</header>
<main>
    <div align="center">
        <form action="/parts">
            <p><input name="filter" value="${listParams.get('filter')}"><input type="submit" value="найти"></p>
        </form>
    </div>
    <c:set var="params" value=""/>
    <table width="1024">
        <thead>
        <tr>
            <!--СОРТИРОВКА
            вытаскиваем параметры сортировки, с которыми открыта страница-->
            <c:set var="orderby" value="${listParams.get('orderby')}"/>
            <c:choose>
                <c:when test="${listParams.get('ordertype').toLowerCase().equals('desc')}">
                    <c:set var="ordersign" value="&#9660;"/>
                    <c:set var="paramdesc" value="&ordertype=asc"/>
                </c:when>
                <c:otherwise>
                    <c:set var="ordersign" value="&#9650;"/>
                    <c:set var="paramdesc" value="&ordertype=desc"/>
                </c:otherwise>
            </c:choose>
            <!--собираем остальные параметры, с которыми была открыта страница (фильтр и ещё была страница, но я её тоже решил обнулять)
            чтобы передать их в линки сортировок-->
            <c:set var="params" value=""/>
            <c:forEach items="${listParams}" var="listParams">
                <c:if test="${!(listParams.key.toLowerCase().equals('orderby')) && !(listParams.key.toLowerCase().equals('ordertype')) && !(listParams.key.toLowerCase().equals('page'))}">
                    <c:set var="params" value="${params}&${listParams.key}=${listParams.value}"/>
                </c:if>
            </c:forEach>
            <!-- в линках после параметра orderby дописываем параметр сортировки ordertype и остальные параметры -->
            <th><a href="?orderby=partId${paramdesc}${params}">ид</a><c:if test="${orderby.equals('partId')}">${ordersign}</c:if></th>
            <th><a href="?orderby=partName${paramdesc}${params}">Наименование</a><c:if test="${orderby.equals('partName')}">${ordersign}</c:if></th>
            <th><a href="?orderby=partRequired${paramdesc}${params}">Необходимость</a><c:if test="${orderby.equals('partRequired')}">${ordersign}</c:if></th>
            <th><a href="?orderby=partAmount${paramdesc}${params}">Количество</a><c:if test="${orderby.equals('partAmount')}">${ordersign}</c:if></th>
            <!-- теперь, всякий раз когда мы тыкаем в сортировку, новая страница открывается со старыми параметрами
                 и мы получаем тот же запрос, открытый на той же странице, но по-другому отсортированный
            -->
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tfoot>
            <tr>
                <c:set var="maxPcNumber" value="${maxPcNumber}"/>
                <td></td>
                <td><strong>Можно собрать</strong></td>
                <td align="center"><strong>${maxPcNumber}</strong></td>
                <td><strong>компьютеров<strong></strong></td>
            </tr>
        </tfoot>
        <tbody>
        <c:forEach items="${listParts}" var="part">
            <tr>
                <td width="30">${part.partId}</td>
                <td>${part.partName}</td>
                <td width="50" align="center"><c:if test="${part.partRequired}">&#10004;</c:if></td>
                <td width="50" align="center">${part.partAmount}</td>
                <td width="50"><a href="<c:url value='/part/${part.partId}'/>" title="редактировать">редактировать</a></td>
                <td width="50"><a class="remove" href="<c:url value='/remove/${part.partId}'/>"title="удолить">&#10006;</a></td>
            </tr>
        </c:forEach>
        <tr>
            <td>
            <td colspan="5">
                <c:url var="editAction" value="/part/new"/>
                <form:form method="POST" action="${editAction}" accept-charset="utf-8" modelAttribute="part">
                    <input type="submit" value="добавить"/>
                </form:form>
            </td>
        </tr>
        </tbody>
    </table>
</main>
<footer>
    <!--ПАГИНАЦИЯ
      сначала собираем параметры, с которыми была открыта страница (фильтр, сортировка)
      я не знаю, как это делается в веб разработке, и, скорее всего, сделал какую-то фигню
      но зато пагинация будет работать внешне нормально-->
    <!-- перебираем в цикле меп с параметрами/значениями, если параметр не page, составляем из них строку параметров &параметр=значение%.. -->
    <c:set var="params" value=""/>
    <c:forEach items="${listParams}" var="listParams">
        <c:if test="${!(listParams.key.toLowerCase().equals('page'))}">
            <c:set var="params" value="${params}&${listParams.key}=${listParams.value}"/>
        </c:if>
    </c:forEach>
    <div align="center">
        <div class="pagination">
            <!-- и дописываем строку с параметрами после параметра page  -->
            <c:forEach items="${pageList}" var="page">
                <!-- помечаем открытую страницу сss слассом  -->
                <c:set var="class_active" value=""/>
                <c:if test="${!(page==currentPage)}">
                    <c:set var="class_active" value=' class="active"'/>
                </c:if>
                <a ${class_active} href="?page=${page.toString()}${params}">${page.toString()}</a>
            </c:forEach>
        </div>
    </div>
</footer>
</body>
</html>

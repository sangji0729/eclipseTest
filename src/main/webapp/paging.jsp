<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 만드는 변수마다 모두 scope="request" 적기 -> 변수의 참조 범위를 지정하는 것.
     scope="page"가 기본 -->
<fmt:parseNumber integerOnly="true" var="totalPage"
	value="${totalCount / PAGENUMBER }" scope="request" />
<c:if test="${totalCount % PAGENUMBER ne 0 }">
	<c:set var="totalPage" value="${totalPage + 1 }" scope="request" />
</c:if>
<c:if test="${page % PAGENUMBER ne 0 }">
	<fmt:parseNumber integerOnly="true" var="startPage"
		value="${page / PAGENUMBER }" scope="request" />
	<c:set value="${startPage * PAGENUMBER + 1 }" var="startPage"
		scope="request" />
</c:if>
<c:if test="${page % PAGENUMBER eq 0 }">
	<c:set value="${page - (PAGENUMBER - 1) }" var="startPage"
		scope="request" />
</c:if>

<c:set value="${startPage + 9 }" var="endPage" scope="request" />
<c:if test="${startPage + 9 gt totalPage}">
	<c:set var="endPage" value="${totalPage }" scope="request" />
</c:if>

<button onclick="location.href='./${pageName}?page=1'">To the
	front</button>
<c:if test="${page lt 11 }">
	<button disabled="disabled">Previous</button>
</c:if>
<c:if test="${page gt PAGENUMBER }">
	<button onclick="location.href='./${pageName}?page=${page - 10}'">
		Previous</button>
</c:if>

<c:if test="${page eq 1 }">
	<button disabled="disabled">◀</button>
</c:if>
<c:if test="${page ne 1 }">
	<button onclick="location.href='./${pageName}?page=${page - 1}'">◀</button>
</c:if>

<c:forEach begin="${startPage }" end="${endPage }" var="i">
	<c:if test="${i eq page }">
		<a href="./${pageName}?page=${i }"><button>${i }</button></a>
	</c:if>
	<c:if test="${i ne page }">
		<a href="./${pageName}?page=${i }">${i }</a>
	</c:if>
</c:forEach>
<c:if test="${page eq totalPage }">
	<button disabled="disabled">▶</button>
</c:if>
<c:if test="${page ne totalPage }">
	<button onclick="location.href='./${pageName}?page=${page + 1}'">▶</button>
</c:if>
<c:if test="${page lt totalPage - 9 }">
	<button onclick="location.href='./${pageName}?page=${page + 10}'">Next</button>
</c:if>
<c:if test="${page gt totalPage - 10 }">
	<button disabled="disabled">Next</button>
</c:if>
<button onclick="location.href='./${pageName}?page=${totalPage }'">To
	the end</button>
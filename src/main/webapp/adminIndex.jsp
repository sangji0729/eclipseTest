<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>	    

 <c:if test="{${sessionScope.grade eq null }">
<c:redirect url="./index" />
</c:if>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>WebtoonReview - ������ ������</title>
</head>
<style>
    @import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
</style>
<link rel="stylesheet" href="./css/admin.css">
<body>
<div id="admin">
<nav id="adminSide">
  <div id="adminSideMenuTop">
    <div class="titleLogo">
        <img src="./img/title2.png" alt="title">
    </div>
    <div class="pageTitle">
        <h3>[ ������ ��� ]</h3>
    </div>
    <div id="adminIdPrint">
        ${sessionScope.id} ��
    </div>
    <hr class="styleHR4">
  </div>
      <div id="adminSideMenuContainer">
          <div id="adminSideMenu">
              <ul class="item_AdminSideMenu">
                  <li><a href="./adminMember"><img src="./img/memberControl.png">ȸ�� ����</a></li>
                  <li><a href="./adminPost"><img src="./img/articleControl.png">�Խñ� ����</a></li>
                  <li><a href="./admin"><img src="./img/log.png">�α� ����</a></li>
                  <li><a href="./adminPick"><img src="./img/article2.png">Editor's Pick</a></li>
                  <li><a href="./index"><img src="./img/exit.png">������</a></li>
              </ul>
          </div>
      </div>
</nav>
<div id="adminMain">
    <div id="adminMainContainer">
        <div id="adminMainContent">
            <h1>������ ���</h1>
            <span>���� �޴����� ������ �۾��� �����ϼ���.</span>
        </div>
    </div>
</div>
</div>
</body>
</html>
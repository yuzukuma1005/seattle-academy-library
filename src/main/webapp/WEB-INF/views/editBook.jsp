<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta charset="UTF-8">
<title>書籍の追加｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet"
    type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP"
    rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />"
    rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css"
    rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet"
    type="text/css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="resources/js/thumbnail.js"></script>
</head>
<body class="wrapper">
    <header>
        <div class="left">
            <img class="mark" src="resources/img/logo.png" />
            <div class="logo">Seattle Library</div>
        </div>
        <div class="right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/home"
                    class="menu">Home</a></li>
                <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>
            </ul>
        </div>
    </header>
    <main>
        <form action="<%=request.getContextPath()%>/updateBook"
            method="post" enctype="multipart/form-data"
            id="data_upload_form">
            <h1>書籍の編集</h1>
            <div class="content_body add_book_content">
                <div>
                    <span>書籍の画像</span> <span class="care care1">任意</span>
                    <div class="book_thumnail">
                        <c:if test="${empty bookInfo.thumbnailUrl}">
                            <img class="book_noimg"
                                src="resources/img/noImg.png">
                        </c:if>
                        <c:if test="${!empty bookInfo.thumbnailUrl}">
                            <img class="book_noimg"
                                src="${bookInfo.thumbnailUrl}">
                        </c:if>
                    </div>
                    <input type="file" accept="image/*" name=thumbnail
                        id="thumbnail">
                </div>
                <div class="content_right">
                    <div>
                        <c:if test="${!empty errorList}">
                            <div class="error">
                                <c:forEach var="error"
                                    items="${errorList}">
                                    <p>${error}</p>
                                </c:forEach>
                            </div>
                        </c:if>
                        <span>書籍名</span()><span class="care care2">必須</span>
                            <input type="text" name="title"
                            value="${bookInfo.title}">
                    </div>
                    <div>
                        <span>著者名</span><span class="care care2">必須</span>
                        <input type="text" name="author"
                            value="${bookInfo.author}">
                    </div>
                    <div>
                        <span>出版社</span><span class="care care2">必須</span>
                        <input type="text" name="publisher"
                            value="${bookInfo.publisher}">
                    </div>
                    <div>
                        <span>出版日</span><span class="care care2">必須</span>
                        <input type="text" name="publishDate"
                            value="${bookInfo.publishDate}">
                    </div>
                    <div>
                        <span>分類</span><span class="care care2">必須</span>
                        <input type="text" name="classification"
                            value="${bookInfo.classification}">
                    </div>
                    <div>
                        <span>ISBN</span><span class="care care1">任意</span>
                        <input type="text" name="isbn"
                            value="${bookInfo.isbn}">
                    </div>
                    <div>
                        <span>説明文</span><span class="care care1">任意</span>
                        <c:if test="${!empty bookInfo}">
                            <input type="text" name="description"
                                value="${bookInfo.description}">
                        </c:if>
                        <c:if test="${empty bookInfo}">
                            <input type="text" name="description">
                        </c:if>
                    </div>
                    <input type="hidden" id="bookId" name="bookId"
                        value="${bookInfo.bookId}">
                </div>
            </div>
            <div class="bookBtn_box">
                <button type="submit" id="add-btn"
                    class="btn_updateBook">更新</button>
        </form>
        <form method="post" action="deleteBook" name="delete">
            <input type="hidden" id="bookId" name="bookId"
                value="${bookInfo.bookId}">
            <div class="bookBtn_box">
                <button type="submit" id="add-btn"
                    class="btn_deleteBook">削除</button>
        </form>
        </div>
    </main>
</body>
</html>
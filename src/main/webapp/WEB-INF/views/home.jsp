<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page contentType="text/html; charset=utf8"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>ホーム｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
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
            <link
                href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
                rel="stylesheet">
        </div>
        <div class="c">
            <form action="select" class="search-form-005">
                <label> <input type="text"
                    placeholder="キーワードを入力">
                </label>
                <button type="submit" aria-label="検索"></button>
            </form>
        </div>
    </header>
    <main>
        <h1>Home</h1>
        <a href="<%=request.getContextPath()%>/addBook"
            class="btn_add_book">書籍の追加</a> <a
            href="<%=request.getContextPath()%>/favoriteBook"
            class="btn_fav_book">お気に入り書籍</a>
        <div class="conteiner">
            <form method="get" action="class">
                <label class="selectbox-003"> <select
                    name="category">
                        <option value="選択">--選択--</option>
                        <option value="少年漫画">少年漫画</option>
                        <option value="少女漫画">少女漫画</option>
                        <option value="小説">小説</option>
                        <option value="絵本">絵本</option>
                        <option value="図鑑">図鑑</option>
                        <option value="思い出">思い出</option>
                </select>
                </label>
                <button type="submit" name="submit" class="button-002">表示</button>
            </form>
            <form method="get" action="sortOrders">
                <p>
                    <label class="selectbox-003"> <select
                        name="sortOrder">
                            <option value="sortASC">タイトル(昇順)</option>
                            <option value="sortDESC">タイトル(降順)</option>
                            <option value="sortPlASC">出版日順(昇順)</option>
                            <option value="sortPlDESC">出版日順(降順)</option>
                    </select>
                    </label>
                    <button type="submit" name="submit"
                        class="button-002">並び替え</button>
                </p>
            </form>
        </div>
        <div class="content_body">
            <c:if test="${!empty resultMessage}">
                <div class="error_msg">${resultMessage}</div>
            </c:if>
            <div>
                <div class="booklist">
                    <c:forEach var="bookInfo" items="${bookList}">
                        <div class="books">
                            <form method="get" class="book_thumnail"
                                action="editBook">
                                <a href="javascript:void(0)"
                                    onclick="this.parentNode.submit();">
                                    <c:if
                                        test="${empty bookInfo.thumbnail}">
                                        <img class="book_noimg"
                                            src="resources/img/noImg.png">
                                    </c:if> <c:if
                                        test="${!empty bookInfo.thumbnail}">
                                        <img class="book_noimg"
                                            src="${bookInfo.thumbnail}">
                                    </c:if>
                                </a> <input type="hidden" name="bookId"
                                    value="${bookInfo.bookId}">
                            </form>
                            <ul>
                                <li class="book_title">${bookInfo.title}</li>
                                <li class="book_author">${bookInfo.author}(著)</li>
                                <li class="book_publisher">出版社：${bookInfo.publisher}</li>
                                <li class="book_publish_date">出版日：${bookInfo.publishDate}</li>
                                <li class="book_classification">ジャンル：${bookInfo.classification}</li>
                                <li class="book_evaluation">評価:${bookInfo.evaluation}</li>
                                <c:if
                                    test="${!(bookInfo.favorit.equals('like'))}">
                                    <form methot="get" action="fvbutton"
                                        name="fvbtn">
                                        <button>
                                            <svg viewBox="0 0 24 24"
                                                width="24" height="24"
                                                stroke="currentColor"
                                                stroke-width="2"
                                                fill="none"
                                                stroke-linecap="round"
                                                stroke-linejoin="round"
                                                class="css-i6dzq1">
                                                <polygon
                                                    points="13 2 3 14 12 14 11 22 21 10 12 10 13 2"></polygon></svg>
                                            Add to Favorite
                                        </button>
                                        <input type="hidden"
                                            name="bookId"
                                            value="${bookInfo.bookId}">
                                    </form>
                                </c:if>
                                <c:if
                                    test="${bookInfo.favorit.equals('like')}">
                                    <form methot="get"
                                        action="fvtbutton" name="fvtbtn">
                                        <button>
                                            <svg viewBox="0 0 24 24"
                                                width="24" height="24"
                                                stroke="currentColor"
                                                stroke-width="2"
                                                fill="none"
                                                stroke-linecap="round"
                                                stroke-linejoin="round"
                                                class="css-i6dzq1">
                                                <polygon
                                                    points="13 2 3 14 12 14 11 22 21 10 12 10 13 2"></polygon></svg>
                                            Added to Favorite
                                        </button>
                                        <input type="hidden"
                                            name="bookId"
                                            value="${bookInfo.bookId}">
                                    </form>
                                </c:if>
                            </ul>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </main>
</body>
</html>

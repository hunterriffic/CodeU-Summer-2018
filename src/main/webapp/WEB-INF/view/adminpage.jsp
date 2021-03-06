<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String userCount = request.getAttribute("userCount").toString();
String messageCount = request.getAttribute("messageCount").toString();
String conversationCount = request.getAttribute("conversationCount").toString();
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Admin Page - Charmer Chat</title>
  <link rel="stylesheet" href="/css/main.css">
  <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
</head>
<body>
  <nav>
    <a id="navTitle" href="/">Charmer Chat</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
  </nav>
  <div id="container">
    <div
      style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">

      <h1>Administration</h1>
      <h2>Site Statistics</h2>

      <p>Here are some site stats:</p>
      <ul>
        <li><strong>Users:</strong> <%= userCount %></li>
        <li><strong>Conversations:</strong> <%= conversationCount %> </li>
        <li><strong>Messages:</strong> <%= messageCount %></li>
      </ul>
    </div>
  </div>
</body>
</html>

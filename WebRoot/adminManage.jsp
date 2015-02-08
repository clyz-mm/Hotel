<%@ page contentType="text/html;charset=utf-8"
    import="java.util.*,db.*"
%>
 <html>
  <head>
   <title>管理员管理</title>
   <link href="css/generalstyle.css" type="text/css" rel="stylesheet">
  </head>
 <body>
    <%@ include file="admintop.jsp" %>
    <hr width="100%"></hr><br>
    <%
       Vector<String []> vadmin = DB.getAdminInfo();
       int color=0;//改变每行颜色 
     %>
    <table align="center" width="90%" cellspacing="1" bgcolor="black">
     <tr bgcolor="white">
       <th>管理员ID</th>
       <th>管理员级别</th>
       <th>删除</th>   
     </tr>
      <%
      for(String []s:vadmin)
      {
     %>
      <tr bgcolor=<%= color%2==0?"eeffee":"ffeeee" %>>
        <td align="center"><%= s[0] %></td>
        <% 
        if(s[1].equals("1"))
        {
        %>
        <td align="center">超级管理员</td>
        <%
        }
        else
        {
         %>
        <td align="center">普通管理员</td>
        <% } %>        
        <td align="center">
         <a href=ListServlet?action=deleteAdmin&&adname=<%= s[0] %>>删除</a>
        </td>
       </tr>
     <%
         color++;
       }
      %>
    </table >
    <table align="center" border="0" width="80%">
     <tr>
      <td align="left"><a href=addAdmin.jsp>&lt;&lt;添加管理员</a></td>
      <td align="right"><a href=adResetPwd.jsp>重置管理员密码&gt;&gt;</a></td>
     </tr>
    </table>
 </body>
</html>
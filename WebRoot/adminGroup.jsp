<%@ page contentType="text/html;charset=utf-8"
    import="java.util.*,db.*"%>
 <html>
  <head>
   <title>分组管理</title>
   <link href="css/generalstyle.css" type="text/css" rel="stylesheet">
  </head>
 <body>
    <%@ include file="admintop.jsp" %>
    
    <hr width="100%"></hr><br>
    <table align="center" width="80%">
    <tr>
    <td align="right" >
    	<a href=addGroup.jsp>添加分组>></a>
    </td>
    </tr>
    </table>
    <% Vector<String []> vgroup = DB.getGroup();
       int color=0;//改变每行颜色 
     %>
    <table align="center" width="90%" cellspacing="1" bgcolor="black">
     <tr bgcolor="white">
       <th>名称</th>  <th>图片URL</th>  <th>描述</th>
       <th>规则</th>  <th>修改/删除</th>    
     </tr>     
      <%for(String []s:vgroup){%>
      <tr bgcolor=<%= color%2==0?"eeffee":"ffeeee" %>>
        <td align="center"><%= s[0] %></td>
        <td align="center"><%= s[1] %></td>
        <td align="center"><%= s[2] %></td>
        <td align="center"><%= s[4] %></td> 
        <td align="center">
         <a href=ListServlet?action=editGroup&&gId=<%= s[3] %>>修改/删除</a>
        </td>
       </tr>
     <%color++;}
      %>
    </table><br>
    
 </body>
</html>
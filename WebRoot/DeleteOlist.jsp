<%@ page
 contentType="text/html;charset=utf-8"
 import="java.io.*,javax.servlet.*,db.*,util.*,java.util.*"
 %>
 <%
 String param=request.getParameter("param1").trim();
 String oid=MyConverter.unescape(param);
 System.out.print(oid);
 if(DB_delete.isDelete(oid))
 {
   out.println(MyConverter.escape("订单删除成功！"));
 }
 else
 {
   out.println(MyConverter.escape("订单删除失败！"));
 }
 %>
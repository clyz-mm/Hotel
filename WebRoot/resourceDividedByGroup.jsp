<%@ page
 contentType="text/html;charset=utf-8"
 import="java.io.*,javax.servlet.*,db.*,util.*,java.util.*"
 %>
<%
  String params1=request.getParameter("params1");
  String param1=MyConverter.unescape(params1);
  
  Vector<String[]> v = DB.getResource(param1);
  String msg="";
  for(String[]s:v)
  {
      msg+=s[0]+"|"+s[1]+"|"+s[2]+"|"+s[3]+"|";
  }
  out.println(MyConverter.escape(msg));
  //System.out.println(msg);
%>
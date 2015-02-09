<%@ page
 contentType="text/html;charset=utf-8"
 import="java.io.*,java.util.*,javax.servlet.*,db.*,util.*"
 %>
<%
    String param1=request.getParameter("params1");
    String uname=MyConverter.unescape(param1);
    Vector<String[]> v = DB.getGroup();
    String msg="";
    for(String[]s:v)
    {
       msg+=s[0]+"|"+s[2]+"|";
       //获得分组名和分组描述
    }
    out.println(MyConverter.escape(msg));
    //System.out.println(MyConverter.escape(msg));
%>
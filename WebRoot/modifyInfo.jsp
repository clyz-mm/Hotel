<%@ page
 contentType="text/html;charset=utf-8"
 import="java.io.*,javax.servlet.*,db.*,util.*,java.util.*"
 %>
 <%
   String param1=request.getParameter("param1").trim();//姓名
   String param2=request.getParameter("param2").trim();//密码
   String param3=request.getParameter("param3").trim();//手机号
   String param4=request.getParameter("param4").trim();//真实姓名
   String param5=request.getParameter("param5").trim();//性别
   String param6=request.getParameter("param6").trim();//电子邮箱
   String uname=MyConverter.unescape(param1);
   String mm=MyConverter.unescape(param2);
   String telnum=MyConverter.unescape(param3);
   String realname=MyConverter.unescape(param4);
   String sex=MyConverter.unescape(param5);
   String email=MyConverter.unescape(param6);
   Vector<String> userInfo=new Vector<String>(); 
   String sqla="update user set pwd='"+mm+"',telNum='"+telnum+"',realName='"+
   realname+"',gender='"+sex+"',email='"+email+"' where uname='"+uname+"'";
   if(DB.updatea(sqla)){
	 out.println(MyConverter.escape("更新成功，请重新登录！"));
	}
   else
   {
	   out.println(MyConverter.escape("更新失败！！！！"));
   //System.out.println("更新失败！！！！");
   }
%>
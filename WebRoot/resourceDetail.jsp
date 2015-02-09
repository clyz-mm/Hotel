<%@ page
 contentType="text/html;charset=gbk"
 import="java.io.*,javax.servlet.*,util.*,db.*,java.util.*"
 %>
 <%
   String params1=request.getParameter("params1");
   String param1=MyConverter.unescape(params1);
   //System.out.println(param1);
   //System.out.print(param1.length());
   String s="";
   if(param1.length()==3)
   {
      s=DB.getOrderDet(1);
   }
   else
   {
      s=DB.getOrderDet(2);
   }
   String msg=DB.getDetail(param1);
   out.println(MyConverter.escape("¼ò½é£º"+msg+"|Ô¤¶¨¹æÔò:"+s));  
 %>
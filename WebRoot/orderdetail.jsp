<%@ page
 contentType="text/html;charset=utf-8"
 import="java.io.*,javax.servlet.*,db.*,util.*,java.util.*"
 %>
 <% 
     String param1=request.getParameter("param1").trim();
     String oid=MyConverter.unescape(param1);
     Vector<String[]> v=new Vector<String[]>();
   //根据订单号查询.订单的(房间号,开始结束时间,预定状态,分组名)
     v=Order_DB.getOrderDetail(oid);
     StringBuffer msg=new StringBuffer();
     for(String[] s:v)
   {
 	msg.append(s[0]);
 	msg.append("|");
 		msg.append(s[1]);
 	msg.append("|");
 		msg.append(s[2]);
 	msg.append("|");
 		msg.append(s[3]);
 	msg.append("|");
 		msg.append(s[4]);
 	msg.append("|");
   }
   String s=msg.toString();
   out.println(MyConverter.escape(s));
  // System.out.println(s+",用户数据已经传回客户端！");
  %>
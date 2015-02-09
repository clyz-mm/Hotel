<%@ page
 contentType="text/html;charset=utf-8"
 import="java.io.*,javax.servlet.*,util.*,db.*,java.util.*"
 %>
<%
  System.out.println("setOlist========");
  String param1=request.getParameter("param1").trim();
  String param2=request.getParameter("param2").trim();
  String param3=request.getParameter("param3").trim();
  String param4=request.getParameter("param4").trim();
  String uname=MyConverter.unescape(param1); 
  String roomid=MyConverter.unescape(param2); 
  String starttime=MyConverter.unescape(param3); 
  String endtime=MyConverter.unescape(param4);
  Vector<String[]> v=new Vector<String[]>();
  String[] s=new String[3];
  //若该房间没有被预定
  if(!Order_DB.isOrdered(roomid))
  {
      s[0]=roomid;
      //会议室时间正常预定
    if(s[0].length()==2)
    {
           s[1]=starttime;
           s[2]=endtime;
    }
      //客房开始和结束时间默认值设置
    else
    {
        s[1]=(starttime+" 14:00");
        s[2]=(endtime+" 12:00");
    
    }


    v.add(s);
    int i=0;
    i=Order_DB.addOrder(uname,v);
    if(i==-1)
    {
      out.println(MyConverter.escape("订单提交失败！"));
    }
    else
    {
      String oid="";
      String sql="select oid from oinfo where rgid="+roomid;
      oid=DB.getInfo(sql);
      Vector<String[]> vv=new Vector<String[]>();
    //根据订单号查询.订单的(房间号,开始结束时间,预定状态,分组名)
      vv=Order_DB.getOrderDetail(oid);
      StringBuffer msg=new StringBuffer("");
      for(String[] ss:vv)
      {
        msg.append(ss[0]);
        msg.append("   ");
        msg.append(ss[1]);
        msg.append("   ");
        msg.append(ss[2]);
        msg.append("   ");
        msg.append(ss[3]);
        msg.append("   ");
        msg.append(ss[4]);
        msg.append("   |");
      }
      out.println(MyConverter.escape("预订成功！"));
    }
  }
  else
  {
    out.println(MyConverter.escape("订单还在预订中！"));
  }
  //}
%>

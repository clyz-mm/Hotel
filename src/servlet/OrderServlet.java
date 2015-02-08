/**
 * 
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.DB;
import db.Order_DB;

/**
 * @author Harry
 *
 */
public class OrderServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public OrderServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	public void init(ServletConfig conf) throws ServletException 
	{ //Servlet的inti初始化方法
		super.init(conf);
	}
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException, IOException{//doGet方法
		doPost(req,res);//调用doPost方法
	}
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");	
		
		String action = req.getParameter("action");//得到请求的响应action		
		HttpSession session=req.getSession(true);//得到session对象
		PrintWriter pw=res.getWriter();//得到输出流对象
		String msg="";//声明提示消息		
		String uname = (String)session.getAttribute("uname");//得到登陆用户名
		//资源的几种状态
		String ostatus1 = "预订中";
		String ostatus2 = "预订成功";
		String ostatus3 = "预订失败";
		Vector<String[]> OrderList = //得到订单列表
					(Vector<String[]>)session.getAttribute("OrderList");
		if(OrderList==null)//如果为空则创建一个订单列表对象
		{OrderList = new Vector<String[]>();}
		if(action.equals("ListDetail")){
			String oid = req.getParameter("oid");//得到订单编号
			Vector<String []> ListDetail = Order_DB.getOrderDetail(oid);//执行查询				
			req.setAttribute("ListDetail",ListDetail);
			req.setAttribute("oid",oid);
			req.getRequestDispatcher("detail.jsp").forward(req,res);
		}
		
		else if(action.equals("allOrders")){//按条件查询订单
			if(session.getAttribute("adname")!=null){//管理员是否登陆
				String sql = "";//声明SQL引用
				int conditon = Integer.parseInt(req.getParameter("condition"));
				switch(conditon){
					case 1://1表示所有订单
					sql = "select * from olist";
					break;
					case 2://2表示已经处理的订单
					sql = "select * from olist where ostatus='"+ostatus2+"' or ostatus='"+ostatus3+"'";
					break;
					case 3://3表示未处理的订单
					sql = "select * from olist where ostatus='"+ostatus1+"'";
					break;
				}
				Vector<String []> list = Order_DB.getOrderList(sql);				
				req.setAttribute("list",list);//将订单列表返回				
				req.getRequestDispatcher("adminOrders.jsp").forward(req,res);
			}
			else{
				msg = "请先登陆";//没有登陆的提示消息
				req.setAttribute("msg",msg);
				req.getRequestDispatcher("adinfo.jsp").forward(req,res);
			}
		}
		else if(action.equals("query")){//按编号查询订单
		    Vector<String []> list = null;
		    try{
		    	int oid = Integer.parseInt(req.getParameter("oid"));
				String sql = "select * from olist where oid="+oid;
				list = Order_DB.getOrderList(sql);
		    }
		    catch(NumberFormatException nfe)//输入订单号格式不正确
		    {list = new Vector<String []>();}//返回一个空的向量							
			req.setAttribute("list",list);
			req.getRequestDispatcher("adminOrders.jsp").forward(req,res);
		}
		else if(action.equals("dealOrder")){//处理订单
			String adname = (String)session.getAttribute("adname");
			String reason = req.getParameter("reason");
			String ostatus = req.getParameter("ostatus");
			int oid = Integer.parseInt(req.getParameter("oid"));
			//拼装SQL
			String sqla = "update olist set ostatus='"+ostatus+"',oreason='"+
							reason+"',odeal='"+adname+"'where oid="+oid;
			String sqlb = "update oinfo set ostatus='"+ostatus+"' where oid="+oid;			
			boolean b = DB.update(sqla,sqlb);//执行更新		
			if(b==true){
				msg = "订单处理成功<br><br>"
					+"<a href=OrderServlet?action=allOrders&&condition=1>返回";
			}
			else{msg = "订单处理发生错误，处理失败";}
			req.setAttribute("msg",msg);//返回处理消息
			req.getRequestDispatcher("adinfo.jsp").forward(req,res);
		}
	}
}

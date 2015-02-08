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
	{ //Servlet��inti��ʼ������
		super.init(conf);
	}
	public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException, IOException{//doGet����
		doPost(req,res);//����doPost����
	}
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");	
		
		String action = req.getParameter("action");//�õ��������Ӧaction		
		HttpSession session=req.getSession(true);//�õ�session����
		PrintWriter pw=res.getWriter();//�õ����������
		String msg="";//������ʾ��Ϣ		
		String uname = (String)session.getAttribute("uname");//�õ���½�û���
		//��Դ�ļ���״̬
		String ostatus1 = "Ԥ����";
		String ostatus2 = "Ԥ���ɹ�";
		String ostatus3 = "Ԥ��ʧ��";
		Vector<String[]> OrderList = //�õ������б�
					(Vector<String[]>)session.getAttribute("OrderList");
		if(OrderList==null)//���Ϊ���򴴽�һ�������б����
		{OrderList = new Vector<String[]>();}
		if(action.equals("ListDetail")){
			String oid = req.getParameter("oid");//�õ��������
			Vector<String []> ListDetail = Order_DB.getOrderDetail(oid);//ִ�в�ѯ				
			req.setAttribute("ListDetail",ListDetail);
			req.setAttribute("oid",oid);
			req.getRequestDispatcher("detail.jsp").forward(req,res);
		}
		
		else if(action.equals("allOrders")){//��������ѯ����
			if(session.getAttribute("adname")!=null){//����Ա�Ƿ��½
				String sql = "";//����SQL����
				int conditon = Integer.parseInt(req.getParameter("condition"));
				switch(conditon){
					case 1://1��ʾ���ж���
					sql = "select * from olist";
					break;
					case 2://2��ʾ�Ѿ�����Ķ���
					sql = "select * from olist where ostatus='"+ostatus2+"' or ostatus='"+ostatus3+"'";
					break;
					case 3://3��ʾδ����Ķ���
					sql = "select * from olist where ostatus='"+ostatus1+"'";
					break;
				}
				Vector<String []> list = Order_DB.getOrderList(sql);				
				req.setAttribute("list",list);//�������б���				
				req.getRequestDispatcher("adminOrders.jsp").forward(req,res);
			}
			else{
				msg = "���ȵ�½";//û�е�½����ʾ��Ϣ
				req.setAttribute("msg",msg);
				req.getRequestDispatcher("adinfo.jsp").forward(req,res);
			}
		}
		else if(action.equals("query")){//����Ų�ѯ����
		    Vector<String []> list = null;
		    try{
		    	int oid = Integer.parseInt(req.getParameter("oid"));
				String sql = "select * from olist where oid="+oid;
				list = Order_DB.getOrderList(sql);
		    }
		    catch(NumberFormatException nfe)//���붩���Ÿ�ʽ����ȷ
		    {list = new Vector<String []>();}//����һ���յ�����							
			req.setAttribute("list",list);
			req.getRequestDispatcher("adminOrders.jsp").forward(req,res);
		}
		else if(action.equals("dealOrder")){//������
			String adname = (String)session.getAttribute("adname");
			String reason = req.getParameter("reason");
			String ostatus = req.getParameter("ostatus");
			int oid = Integer.parseInt(req.getParameter("oid"));
			//ƴװSQL
			String sqla = "update olist set ostatus='"+ostatus+"',oreason='"+
							reason+"',odeal='"+adname+"'where oid="+oid;
			String sqlb = "update oinfo set ostatus='"+ostatus+"' where oid="+oid;			
			boolean b = DB.update(sqla,sqlb);//ִ�и���		
			if(b==true){
				msg = "��������ɹ�<br><br>"
					+"<a href=OrderServlet?action=allOrders&&condition=1>����";
			}
			else{msg = "�������������󣬴���ʧ��";}
			req.setAttribute("msg",msg);//���ش�����Ϣ
			req.getRequestDispatcher("adinfo.jsp").forward(req,res);
		}
	}
}

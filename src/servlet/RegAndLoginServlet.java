/**
 * 
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.DB;

/**
 * @author Harry
 *
 */
public class RegAndLoginServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public RegAndLoginServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//得到请求响应的action
		String action=request.getParameter("action");
		//得到Session对象
		HttpSession session=request.getSession(true);
		
		PrintWriter printWriter=response.getWriter();
		//返回的消息内容
		String msg="";
		if (action.equals("adlogin")) {
			session.removeAttribute("adname");
			//获取用户名,密码
			String adname=request.getParameter("adname").trim();
			String pwd=request.getParameter("pwd").trim();
			//拼装从数据库得到登陆管理员密码的SQL语句
			String sqla="select adpwd from adinfo where adname='"+adname+"'";
			String pwdFromDB=DB.getInfo(sqla);
			//密码匹配,登录成功
			if (pwdFromDB!=null && pwd.equals(pwdFromDB)) {
				session.setAttribute("adname",adname);//将登陆管理员保存进session
				msg = "登陆成功。";//提示登陆成功
			}
			else{//登陆失败的提示
				msg = "错误的用户名和密码，请重新登陆<br><br>"+
				 		"<a href=index.jsp>重新登陆";				
			}
			//将信息发送到信息显示页面
			request.setAttribute("msg",msg);
			request.getRequestDispatcher("adinfo.jsp").forward(request,response);
		}
		else if(action.equals("adlogout")){//注销
			session.removeAttribute("adname");
			msg = "退出成功。";//提示注销成功
			request.setAttribute("msg",msg);
			request.getRequestDispatcher("adinfo.jsp").forward(request,response);
		}
	}

	public void init(ServletConfig conf) throws ServletException {
		// Put your code here
		super.init(conf);
	}

}

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
		//�õ�������Ӧ��action
		String action=request.getParameter("action");
		//�õ�Session����
		HttpSession session=request.getSession(true);
		
		PrintWriter printWriter=response.getWriter();
		//���ص���Ϣ����
		String msg="";
		if (action.equals("adlogin")) {
			session.removeAttribute("adname");
			//��ȡ�û���,����
			String adname=request.getParameter("adname").trim();
			String pwd=request.getParameter("pwd").trim();
			//ƴװ�����ݿ�õ���½����Ա�����SQL���
			String sqla="select adpwd from adinfo where adname='"+adname+"'";
			String pwdFromDB=DB.getInfo(sqla);
			//����ƥ��,��¼�ɹ�
			if (pwdFromDB!=null && pwd.equals(pwdFromDB)) {
				session.setAttribute("adname",adname);//����½����Ա�����session
				msg = "��½�ɹ���";//��ʾ��½�ɹ�
			}
			else{//��½ʧ�ܵ���ʾ
				msg = "������û��������룬�����µ�½<br><br>"+
				 		"<a href=index.jsp>���µ�½";				
			}
			//����Ϣ���͵���Ϣ��ʾҳ��
			request.setAttribute("msg",msg);
			request.getRequestDispatcher("adinfo.jsp").forward(request,response);
		}
		else if(action.equals("adlogout")){//ע��
			session.removeAttribute("adname");
			msg = "�˳��ɹ���";//��ʾע���ɹ�
			request.setAttribute("msg",msg);
			request.getRequestDispatcher("adinfo.jsp").forward(request,response);
		}
	}

	public void init(ServletConfig conf) throws ServletException {
		// Put your code here
		super.init(conf);
	}

}

package db;
import java.sql.*;
import javax.sql.*;
import java.util.*;
public class DB_delete{
	private static Connection con=null;
	private static Statement stat=null;
	private static ResultSet rs=null;
	public static boolean isDelete(String oid){
		boolean b = false;
		int count=0;
		try{
			oid = oid;
		    con = DB.getCon();
		    stat = con.createStatement();
			System.out.println(oid);
			String sqla="delete from olist where oid="+oid;
			String sqlb="delete from oinfo where orid="+oid;
		    count+=stat.executeUpdate(sqla);
		    count+=stat.executeUpdate(sqlb);
		    if(count==2){b=true;}
		}
		catch(Exception e){e.printStackTrace();}
		finally{DB.closeCon();}
		return b;
	}
}
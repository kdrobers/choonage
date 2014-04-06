

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HTMLServlet
 */
@WebServlet("/Blah.HTML")
public class HTMLServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    protected Hashtable<String,UserInfo> map = new Hashtable<String,UserInfo>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HTMLServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	@Override
	public void init( ServletConfig config ) throws ServletException {
		super.init( config );
		
		map.put( "foo", new UserInfo("bar","x") );
		map.put( "bar", new UserInfo("bar","x") );
		map.put( "baz", new UserInfo("bar","x") );
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		// Check
		if ( request.getParameter( "usr" ) != null ) {
			map.put(request.getParameter("usr"),new UserInfo((request.getParameter("ip")), request.getParameter("ttl")));
		}
		
		// Result
		writer.write( "{" );
		boolean comma = false;
		for ( Entry<String,UserInfo> entry : map.entrySet() ) {
			writer.write( ( comma ? ',' : "" ) + "\"" + entry.getKey() + "\":" + entry.getValue().toString() );
			comma = true;
		}
		writer.write( "}" );
		writer.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

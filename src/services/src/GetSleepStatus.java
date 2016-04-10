

import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class GetSleepStatus
 */
@WebServlet("/GetSleepStatus")
public class GetSleepStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
    JSONObject info;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSleepStatus() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		info = new JSONObject();
		MongoClientURI uri = new MongoClientURI("mongodb://admin:admin@ds021010.mlab.com:21010/code_troopers");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase db = mongoClient.getDatabase("code_troopers");
		
		FindIterable<Document> iterable = db.getCollection("sleep_status").find(eq("do_update", "true"));		
		
		iterable.forEach(new Block<Document>() {
			
		    @Override
		    public void apply(final Document document) {
		        System.out.println(document);
				buildJSON(document);
		    } 
		});
		mongoClient.close();
	    response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
	    response.setHeader("Access-Control-Max-Age", "3600");
	    response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		
		response.setContentType("application/json");     
		PrintWriter out = response.getWriter();  
		out.print(info);
		out.flush();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void buildJSON(Document document){
		try {
			info.put("status", document.get("status"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

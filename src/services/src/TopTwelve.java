
import static com.mongodb.client.model.Sorts.descending;
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
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class TopTwelve
 */
@WebServlet("/TopTwelve")
public class TopTwelve extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int i = 0;
	JSONObject json = new JSONObject();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopTwelve() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		json = mongopull();
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		response.setContentType("application/json");     
		PrintWriter out = response.getWriter();  
		out.print(json);
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void buildJSON(Document doc)
	{		
		JSONObject json1 = new JSONObject();
		
		try {
			json1.put("x", doc.get("x"));
			json1.put("y", doc.get("y"));
			json1.put("z", doc.get("z"));
			json1.put("datetime", doc.get("datetime"));
			json.put("reading" + Integer.valueOf(i).toString(), json1);
			i++;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public JSONObject mongopull()
	{
		i = 0;
		json = new JSONObject();
		//float x = 0, y = 0, z = 0;
		//long datetime = 0;
		MongoClientURI uri = new MongoClientURI("mongodb://admin:admin@ds021010.mlab.com:21010/code_troopers");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase db = mongoClient.getDatabase("code_troopers");
		FindIterable<Document> iterable = db.getCollection("iot_pi").find().limit(12).sort(descending("datetime"));
//		MongoCursor<Document> iter = iterable.iterator();
//		for(int i =0; i < 12; ++i){
//			iter
//		}
		iterable.forEach(new Block<Document>() {
		   @Override
		   public void apply(final Document document) {
		       buildJSON(document);
		   } 
		});
		mongoClient.close();
		return json;
	}

}

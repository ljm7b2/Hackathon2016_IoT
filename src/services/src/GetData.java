

import java.io.IOException;
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
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
/**
 * Servlet implementation class GetData
 */
@WebServlet("/GetData")
public class GetData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JSONObject jObj;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		jObj = new JSONObject();
		MongoClientURI uri = new MongoClientURI("mongodb://admin:admin@ds021010.mlab.com:21010/code_troopers");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase db = mongoClient.getDatabase("code_troopers");
		
		FindIterable<Document> iterable = db.getCollection("iot_pi").find().sort(descending("datetime"));;
				
		Document doc = iterable.first();
		
		try {
			jObj.put("x", doc.get("x"));
			jObj.put("y", doc.get("y"));
			jObj.put("z", doc.get("z"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		mongoClient.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

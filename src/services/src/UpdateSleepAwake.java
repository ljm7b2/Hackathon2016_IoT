//import org.bson.Document;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientURI;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import static com.mongodb.client.model.Filters.eq;
//import static com.mongodb.client.model.Updates.set;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//
//public class UpdateSleepAwake {
//	
//	static final int SLEEPING = 0;
//	static final int AWAKE = 1;
//	
//	public UpdateSleepAwake(){
//		mainOps();
//	}
//
//	//public static void main(String[] args)
//	public void mainOps()
//	{
//		while(true)
//		 {
//		String jsonString="";
//		
//		try {
//
//			URL url = new URL("http://localhost:8080/CodeTroopersIoT/TopTwelve");
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setRequestMethod("GET");
//			conn.setRequestProperty("Accept", "application/json");
//
//			if (conn.getResponseCode() != 200) {
//				throw new RuntimeException("Failed : HTTP error code : "
//						+ conn.getResponseCode());
//			}
//
//			BufferedReader br = new BufferedReader(new InputStreamReader(
//				(conn.getInputStream())));
//
//			String output;
//			System.out.println("Output from Server .... \n");
//			while ((output = br.readLine()) != null) {
//				System.out.println(output);
//				jsonString = jsonString+output;
//			}
//
//			conn.disconnect();
//
//		  } catch (MalformedURLException e) {
//
//			e.printStackTrace();
//
//		  } catch (IOException e) {
//
//			e.printStackTrace();
//
//		  }
//		JSONObject readings;
//		try {
//			readings = new JSONObject(jsonString);
//			updateBabyStatus(readings);
//		} catch (JSONException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		 //System.out.println(readings);		 
//		 //updateBabyStatus(readings);
//		 	 
//			 
//			 try {
//				Thread.sleep(10000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		 }
//	}
//
//	public static void updateBabyStatus(JSONObject readings)
//	{
//		//To start lets assume that the baby is sleeping.
//		int status = SLEEPING;
//		float threshold = .5f;
//		float decValue=0f;
//		float data[] = new float[] {0f, 0f, 0f, 0f, 0f,0f, 0f, 0f, 0f, 0f,0f, 0f, 0f, 0f, 0f,0f, 0f, 0f, 0f, 0f};
//		
//		
//		for(int i=0;i<readings.length();i++)
//		{
//			//System.out.println(readings.get("reading"+i));
//			JSONObject obj2;
//			try {
//				obj2 = (JSONObject) readings.get("reading"+i);
//
//			//System.out.println(obj2.get("x"));
//			//data[i] = (float) obj2.get("x");
//			data[i] = Float.valueOf(obj2.get("x")+"f");
//			System.out.print(data[i]);
//			System.out.println("at time"+ obj2.get("datetime"));
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		Statistics s = new Statistics(data);
//		decValue = s.getStdDev();
//		System.out.println("Stand Dev : "+decValue);
//		if(decValue > threshold)
//		{
//			status = AWAKE;
//		}
//		
//		if(status == AWAKE)
//			System.out.println("Baby is Awake!");
//		else
//			System.out.println("Baby is Sleeping zzzz");
//		//update the mongo db to Sleeping or awake.
//		
//		writeToMongoDB(status);
//	}
//	
//	public static void writeToMongoDB(int status)
//	{
//		System.out.println("Status : "+status);
//
//		MongoClientURI uri = new MongoClientURI("mongodb://admin:admin@ds021010.mlab.com:21010/code_troopers");
//		MongoClient mongoClient = new MongoClient(uri);
//		MongoDatabase db = mongoClient.getDatabase("code_troopers"); 
//		MongoCollection<Document> collection = db.getCollection("sleep_status");
//		if(status == AWAKE)
//			collection.updateOne(eq("do_update", "true"), set("status", "AWAKE"));
//		else if(status == SLEEPING)
//			collection.updateOne(eq("do_update", "true"), set("status", "SLEEPING"));
//		mongoClient.close();
//	}
//}

package no.hvl.dat110.ac.restservice;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;
import static spark.Spark.post;
import static spark.Spark.delete;

import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class App {
	
	static AccessLog accesslog = null;
	static AccessCode accesscode = null;
	
	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8080);
		}

		// objects for data stored in the service
		
		accesslog = new AccessLog();
		accesscode  = new AccessCode();
		
		after((req, res) -> {
  		  res.type("application/json");
  		});
		
		// for basic testing purposes
		get("/accessdevice/hello", (req, res) -> {
			
		 	Gson gson = new Gson();
		 	
		 	return gson.toJson("IoT Access Control Device");
		});
				
		// TODO: implement the routes required for the access control service
		// as per the HTTP/REST operations describined in the project description
		
		//legger et element (innloggingsforsøk) til i loggen
		post("/accessdevice/log", (req, res) -> {

			Gson gson = new Gson();
			AccessEntry entry = gson.fromJson(req.body(), AccessEntry.class); //bodyen til HTTP POST er en JSON reprensentasjon av AccesnEntry klassen obj
			
			
			int id = accesslog.add(entry.getMessage()); //.add returnerer IDen på meldingen som ble lagt til i loggen
			res.body(gson.toJson(accesslog.get(id))); //
			
			return res;
													
		});
		
		//henter ut hele loggen
		get("/accessdevice/log", (req, res) -> {
			
						
			Gson gson = new Gson();
			return gson.toJson(accesslog.toJson());	
		});
		
	
		//henter ut et gitt element i loggen basert på ID
		get("/accessdevice/log/:id", (req, res) -> {
			
			// får feil på mappingen i Spark ?!
			
			Gson gson = new Gson();
			
			return gson.toJson(accesslog.get(Integer.parseInt(req.params(":id"))));
			
			//LEGGE INN SJEKK OM LOCKED ELLER IKKE?
		});
		
		//Sletter loggen
		
		delete("/accessdevice/log", (req, res) -> {
			accesslog.clear();
			
			//returnere tom logg
			Gson gson = new Gson();
			return gson.toJson(accesslog.toJson());	
		});
		
		//Setter koden basert på verdi fra JSON fra requesten
		put("/accessdevice/code", (req, res) -> {

			Gson gson = new Gson();
			accesscode = gson.fromJson(req.body(), AccessCode.class); //Henter fra bodyen til reqquesten og setter verdien som ny kode
			
			return gson.toJson(accesscode); //returnere samme JSON obj
		});

		//Henter kode
		get("/accessdevice/code", (req, res) -> {

			Gson gson = new Gson();

			return gson.toJson(accesscode);
		});
		
		
		
    }
    
}

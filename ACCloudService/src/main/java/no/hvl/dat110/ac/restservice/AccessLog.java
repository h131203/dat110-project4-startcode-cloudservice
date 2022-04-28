package no.hvl.dat110.ac.restservice;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.gson.Gson;

public class AccessLog {
	
	// atomic integer used to obtain identifiers for each access entry
	private AtomicInteger cid;
	protected ConcurrentHashMap<Integer, AccessEntry> log;
	
	public AccessLog () {
		this.log = new ConcurrentHashMap<Integer,AccessEntry>();
		cid = new AtomicInteger(0);
	}

	// TODO: add an access entry to the log for the provided message and return assigned id
	public int add(String message) {
		
		int id = cid.getAndIncrement(); //henter verdi, og øker
		
		//nytt element som tildeles nyeste id og meldingen
		AccessEntry newEntry = new AccessEntry(id, message);
		
		//legger i loggen
		log.put(id, newEntry); //id er her 
		
		return id;
	}
		
	// TODO: retrieve a specific access entry from the log
	public AccessEntry get(int id) {
		
		AccessEntry retrievedEntry = log.get(id);
		
		return retrievedEntry;
		
	}
	
	// TODO: clear the access entry log
	public void clear() {
		
		log.clear();
		cid.set(0); //siden vi tømmer køen må teller starte på 0 igjen :)
		
	}
	
	// TODO: return JSON representation of the access log
	public String toJson () {
		/*
		 * 
		 * {
			   "id": 1,
			   "message": "locked"
			}
		 */
		
		//MÅ SJEKKES OM FUNKER!
    	
//		Gson gson = new Gson();
//		
//		String json = gson.toJson(this);
//		
//		int numberOfLogEntries = cid.get();
		
//		for (int i = 0; i < numberOfLogEntries; i++) {
//			
//			
//		}
		
		//VURDERE EN ANNEN VARIANT!
//		String json = null;
//		json = "[";
//		int value = cid.get();
//		for (int i = 0; i < value; i++) {
//			json +="{";
//			json += "\"id\": "+i+",";
//			json += "\"message\": \"" + log.get(i).getMessage() + "\"";
//			json +="}";
//			if (i != value - 1) {
//				json +=",";
//			}
//		}
//		json += "]";
//    	
		Gson gson = new Gson();
		String json = gson.toJson(this);
    	return json;

    	
    }
}

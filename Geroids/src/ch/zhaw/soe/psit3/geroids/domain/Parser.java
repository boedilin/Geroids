package ch.zhaw.soe.psit3.geroids.domain;

import org.json.simple.JSONObject;

import ch.zhaw.soe.psit3.geroids.db.Highscore;

public final class Parser {
	
	@SuppressWarnings("unchecked")
	public static String figureToJsonString(Figure figure){
		//Beispiel:
		
		JSONObject obj = new JSONObject();
		
		String name = "kaderli";
	      obj.put("name", name);
	      obj.put("num", new Integer(100));
	      obj.put("balance", new Double(1000.21));
	      obj.put("is_vip", new Boolean(true));

	      System.out.print(obj);
	      
	      //System.out.print(); calls the toString method.
	      // obj.toJSONString(); machts explizit. 
		return obj.toJSONString();
	}
	public static String scoreToJsonString(Playscore score){
		
		
		
		return "";
	}
	public static String highscoreToJsonString(Highscore highscore){
	
	
	
		return "";
	}
}

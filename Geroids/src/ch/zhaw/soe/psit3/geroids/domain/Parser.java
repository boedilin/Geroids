package ch.zhaw.soe.psit3.geroids.domain;

import org.json.simple.JSONObject;

import ch.zhaw.soe.psit3.geroids.db.Highscore;

public final class Parser {
	
	@SuppressWarnings("unchecked")
	public static String figureToJsonString(Figure figure){
		//Beispiel:?
		
		JSONObject obj = new JSONObject();

	      obj.put("name", "foo");
	      obj.put("num", new Integer(100));
	      obj.put("balance", new Double(1000.21));
	      obj.put("is_vip", new Boolean(true));

	      System.out.print(obj);
		
		return obj.toJSONString();
	}
	public static String scoreToJsonString(Playscore score){
		
		
		
		return "";
	}
	public static String highscoreToJsonString(Highscore highscore){
	
	
	
		return "";
	}
}

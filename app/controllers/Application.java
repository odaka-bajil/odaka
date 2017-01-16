package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import models.Schedule;
import models.line.MessageEvent;
import models.line.TextMessage;
import models.line.service.ReplyService;
import play.mvc.Controller;

public class Application extends Controller {

	public static void index() {
		String message = "こんにちは";
		render(message);
	}

	public static void reply() throws JsonParseException, JsonMappingException, IOException {
		// リクエストを取得
		String json = requestToJson();
		System.out.println(json);
		ReplyService service = new ReplyService(json);
		MessageEvent event = service.getMessageEvent();
		TextMessage message = event.events.get(0).message;
		//ここから
		if (message.text != null && message.text.equals("ほげほげ")) {
			message.text = "ほげほげ";
		} else if (message.text != null && message.text.equals("こんにちは")) {
			message.text = "はい、こんにちは";
		} else if (message.text != null && message.text.equals("ばいばい")) {
			message.text = "ばいばい、またね";
		} else if (message.text != null && message.text.equals("はじめまして")) {
			message.text = "はじめまして、よろしく";
		}
		Schedule sch = new Schedule();
		sch.title = message.text;
		sch.save();
		//ここまで
		service.setMessage(message);
		service.run();
//		List<Schedule> schedules = Schedule.findAll();
		renderText("{}");
	}

	public static String requestToJson() {
		InputStream is = request.body;
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}

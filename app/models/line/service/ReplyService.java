package models.line.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.line.LineMessageEvent;
import models.line.Message;
import models.line.MessageEvent;
import play.Logger;

public class ReplyService {

	private final String END_POINT = "https://api.line.me/v2/bot/message/reply";
	private final String ACCESS_TOKEN = "TNiyVqdOEV+Tt4SheNECr1+tQMuraIB/cecrXyJdU4m2/BWsI8q9mjbZVCB07Y6GnFvm4NGa/lOGGI4TZ8kx/ShG/TWdSJC5cM6NnurM6EwYPolW6KCcAQBp3/H/RUFGEkZoBBrCoKDHnhBst7f6TAdB04t89/1O/w1cDnyilFU=";
	private MessageEvent event;
	private Message message;

	public ReplyService(String json) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			event = mapper.readValue(json, MessageEvent.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public MessageEvent getMessageEvent() {
		return this.event;
	}

	public void run() {

		try {
			for (LineMessageEvent e : event.events) {
				URL url = new URL(END_POINT);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/json");
				con.setRequestProperty("Authorization", "Bearer " + ACCESS_TOKEN);

				String content = "{\"replyToken\" : \"" + e.replyToken + "\"," + "\"messages\" : ["
						+ message.toJson() + "]}";
				Logger.info(content);
				// con.connect();
				// 送信
				PrintWriter pw = new PrintWriter(
						new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "utf-8")));
				pw.print(content);// content
				pw.close(); // closeで送信完了
				Logger.info("code" + con.getResponseCode());
				if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
					InputStream is = con.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));

					StringBuffer res = new StringBuffer();
					String s;
					while ((s = reader.readLine()) != null) {
						res.append(s);
					}
					reader.close();
					Logger.info(res.toString());
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

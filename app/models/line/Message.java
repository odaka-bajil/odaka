package models.line;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Message {
	public String toJson() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			TextMessage message = new TextMessage();
			message.text = "すみません。メッセージをうまく理解できませんでした。";
			return mapper.writeValueAsString(this);
		}
	}
}

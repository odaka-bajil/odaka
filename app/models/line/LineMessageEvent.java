package models.line;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class LineMessageEvent extends Message{
	public String replyToken;
	public String type;
	public TextMessage message;
}

package models.line;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TextMessage extends Message {
	public final String type = "text";
	public String text;
}

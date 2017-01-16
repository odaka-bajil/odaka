package models.line;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Kotoha {
	public Long id;
	public String text;
	public String[] tag_list;

	public static  List<Kotoha> getKotohaPhrase (String type, String phrase) throws JsonParseException, JsonMappingException, IOException {
		String encoding = "UTF-8";
		String query = "";
		if (phrase != null && phrase.trim() != "") {
			try {
				query = "?" + type + "=" + URLEncoder.encode(phrase, encoding) + "";
			} catch (UnsupportedEncodingException e) {
				// とりあえず今回は握りつぶす

			}
		}
		String message = null;
		List<Kotoha> phrases = null;
		try {

			String host = "https://kotoha-server.herokuapp.com/api/phrases.json";// ここにドメインとか書いておくとコンストラクタのhostにファイル名を指定することができる
			URL url = new URL(host + query);
			URLConnection uc = url.openConnection();

			InputStream is = uc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));

			StringBuffer sb = new StringBuffer();
			String s;
			while ((s = reader.readLine()) != null) {
				sb.append(s);
			}
			message = sb.toString();
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		if (message != null) {
			System.out.println(message);
			ObjectMapper mapper = new ObjectMapper();
			phrases =   mapper.readValue(message, new TypeReference<List<Kotoha>>() {});
		}
		return phrases;

	}
}

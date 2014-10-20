package io.robusta.classify.oauth.only.java.ggl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class GGLConnection {
	public static final String GGL_APP_ID = "1080623739209-32tc41pi03nq7adc9ra3dts0m6bsg9k7.apps.googleusercontent.com";
	public static final String GGL_APP_SECRET = "j1BA8-tamxmA---KZ2vOZyWC";
	public static final String REDIRECT_URI = "http://localhost:8080/classify/googleOauth.jsp"; //"https://localhost:8080/oauth2callback";

	static String accessToken = "";

	public String getFBAuthUrl() {
		String fbLoginUrl = "";
		try {
			fbLoginUrl = "https://accounts.google.com/o/oauth2/auth?" + "client_id=" + GGLConnection.GGL_APP_ID
					+ "&redirect_uri=" + URLEncoder.encode(GGLConnection.REDIRECT_URI, "UTF-8"); // +
																									// "&scope=email";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fbLoginUrl;
	}

	public String getFBGraphUrl(String code) {
		String fbGraphUrl = "";
		try {
			fbGraphUrl = "https://accounts.google.com/o/oauth2/token?" + "client_id=" + GGLConnection.GGL_APP_ID
					+ "&redirect_uri=" + URLEncoder.encode(GGLConnection.REDIRECT_URI, "UTF-8") + "&client_secret="
					+ GGL_APP_SECRET + "&code=" + code;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fbGraphUrl;
	}

	public String getAccessToken(String code) {
		if ("".equals(accessToken)) {
			URL fbGraphURL;
			try {
				fbGraphURL = new URL(getFBGraphUrl(code));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new RuntimeException("Invalid code received " + e);
			}
			URLConnection fbConnection;
			StringBuffer b = null;
			try {
				fbConnection = fbGraphURL.openConnection();
				BufferedReader in;
				in = new BufferedReader(new InputStreamReader(fbConnection.getInputStream()));
				String inputLine;
				b = new StringBuffer();
				while ((inputLine = in.readLine()) != null)
					b.append(inputLine + "\n");
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Unable to connect with Facebook " + e);
			}

			accessToken = b.toString();
			if (accessToken.startsWith("{")) {
				throw new RuntimeException("ERROR: Access Token Invalid: " + accessToken);
			}
		}
		return accessToken;
	}
}

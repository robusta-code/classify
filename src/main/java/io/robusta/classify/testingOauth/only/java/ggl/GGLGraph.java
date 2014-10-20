package io.robusta.classify.testingOauth.only.java.ggl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class GGLGraph {
	private String accessToken;

	public GGLGraph(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getFBGraph() {
		String graph = null;
		try {

			String g = "https://accounts.google.com/?" + accessToken;
			URL u = new URL(g);
			URLConnection c = u.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
			String inputLine;
			StringBuffer b = new StringBuffer();
			while ((inputLine = in.readLine()) != null)
				b.append(inputLine + "\n");
			in.close();
			graph = b.toString();
			System.out.println(graph);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR in getting FB graph data. " + e);
		}
		return graph;
	}

	public Map getGraphData(String fbGraph) {
		Map fbProfile = new HashMap();
		try {
			JSONObject json = new JSONObject(fbGraph);
			fbProfile.put("id", json.getString("id"));
			fbProfile.put("first_name", json.getString("first_name"));
			if (json.has("email"))
				fbProfile.put("email", json.getString("email"));
			if (json.has("gender"))
				fbProfile.put("gender", json.getString("gender"));
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR in parsing FB graph data. " + e);
		}
		return fbProfile;
	}
}

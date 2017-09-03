package crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.persistence.EntityManager;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import crawler.entity.RedditPost;
import crawler.entity.RedditThread;
import crawler.properties.RedditProperties;
import manager.RedditManager;

public class Requestor {

	private static String getRedditJson(String sUrl, String queryParameters) throws IOException, MalformedURLException {
		sUrl = sUrl + ".json";
		if (queryParameters != "")
			sUrl = sUrl + "?" + queryParameters;
		System.out.println("Making Request to: " + sUrl);
		StringBuilder result = new StringBuilder();
		int count = 0;
		int maxTries = 3;

		URL url = new URL(sUrl);
		while (true) {
			try {
				HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("User-Agent", "Crawler 0.1");
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				rd.close();
				break;
			} catch (IOException e) {
				count++;
				if (count == maxTries)
					throw e;
			}
		}

		return result.toString();

	}
	
	
	private static List<RedditThread> getThreadsByJson(String json) throws Exception {
		List<RedditThread> threadList = new ArrayList<RedditThread>();
		ObjectMapper objectMapper = new ObjectMapper();

		JsonNode node = objectMapper.readValue(json, JsonNode.class);
		JsonNode jacksonThreads = node.get("data").get("children");
		Iterator<JsonNode> threadIterator = jacksonThreads.elements();
		while (threadIterator.hasNext()) {
			JsonNode iteratorThread = (JsonNode) threadIterator.next();
			RedditThread thread = objectMapper.readValue(iteratorThread.get("data").toString(), RedditThread.class);
			threadList.add(thread);
		}

		return threadList;
	}

	public static List<RedditThread> getThreads(String url) throws Exception {
		return getThreadsByJson(getRedditJson(url, ""));
	}

	public static List<RedditPost> getPosts(String url) throws Exception {
		RedditThread thread = new RedditThread();
		List<RedditPost> postList = new ArrayList<RedditPost>();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode node = objectMapper.readValue(getRedditJson(url, ""), JsonNode.class);
		Iterator<JsonNode> postsIterator = node.elements();
		while (postsIterator.hasNext()) {

			JsonNode postsNode = (JsonNode) postsIterator.next();

			// Only the last element has comments in it. The first one contains
			// the thread.
			if (!postsIterator.hasNext()) {
				JsonNode postContentNode = postsNode.get("data").get("children");
				Iterator<JsonNode> postIterator = postContentNode.elements();

				while (postIterator.hasNext()) {
					JsonNode iteratorPost = (JsonNode) postIterator.next();

					RedditPost post = objectMapper.readValue(iteratorPost.get("data").toString(), RedditPost.class);
					if (iteratorPost.get("kind").toString().equals("\"t1\"")) {
						postList.add(post);
					}

					if (post.getChildren() != null)
						for (String childPost : post.getChildren()) {
							postList.addAll(
									getPosts(RedditProperties.HTTPS_REDDIT + thread.getPermalink() + childPost));
						}
				}
			} else {
				List<RedditThread> threadList = getThreadsByJson(postsNode.toString());
				if (threadList.size() > 1)
					throw new Exception("Post list has more than one thread?!");
				thread = threadList.get(0);
			}
		}
		return postList;
	}
	
	
	public static List<RedditPost> getUserPosts(String url, String parameter) throws Exception {
		RedditThread thread = new RedditThread();
		List<RedditPost> postList = new ArrayList<RedditPost>();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode node = objectMapper.readValue(getRedditJson(url, parameter), JsonNode.class);
		Iterator<JsonNode> postsIterator = node.elements();
		Pattern p = Pattern.compile("^[&gt].*", Pattern.MULTILINE);
	    
		while (postsIterator.hasNext()) {

			JsonNode postsNode = (JsonNode) postsIterator.next();
			if (!postsIterator.hasNext()) {
				
				JsonNode postContentNode = postsNode.get("children");
				Iterator<JsonNode> postIterator = postContentNode.elements();

				while (postIterator.hasNext()) {
					JsonNode iteratorPost = (JsonNode) postIterator.next();

					RedditPost post = objectMapper.readValue(iteratorPost.get("data").toString(), RedditPost.class);
					if (iteratorPost.get("kind").toString().equals("\"t1\"")) {
						
						post.setBody(p.matcher(post.getBody()).replaceAll(""));
						postList.add(post);
					}
					if (post.getChildren() != null)
						for (String childPost : post.getChildren()) {
							postList.addAll(
									getPosts(RedditProperties.HTTPS_REDDIT + thread.getPermalink() + childPost));
						}
				}
				JsonNode after = postsNode.get("after");
				if (after.toString() != "null")
					postList.addAll(getUserPosts(url, "after=" + after.toString().substring(1, after.toString().length()-1)));
			}
		}
		System.out.println("#Size: " + postList.size());
		return postList;
	}
	

}

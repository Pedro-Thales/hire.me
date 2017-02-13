package br.com.pedro.bemobi;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.pedro.bemobi.model.Url;

public class Client {
	public static final String REST_SERVICE_URI = "http://localhost:8080/shortener/";

	public static void main(String[] args) {
		
		listAllUrls();
		listTop10Urls();
		getUrlByAlias("nk");
		createUrl("", "https://github.com/bemobi/hire.me");
	}

	/* GET */
	@SuppressWarnings("unchecked")
	public static void listAllUrls() {
		System.out.println("Listando todas as urls !");

		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap<String, Object>> urlsMap = restTemplate.getForObject(REST_SERVICE_URI, List.class);

		if (urlsMap != null) {
			for (LinkedHashMap<String, Object> map : urlsMap) {
				System.out.println("Url : id= " + map.get("id") + " - Alias= " + map.get("alias") + " - Url Longa= "
						+ map.get("urlLarge"));				
			}
		} else {
			System.out.println("Sem urls cadastradas");
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void listTop10Urls() {
		System.out.println("Listando as 10 urls mais visitadas !");

		RestTemplate restTemplate = new RestTemplate();
		List<LinkedHashMap<String, Object>> urlsMap = restTemplate.getForObject(REST_SERVICE_URI + "top10/", List.class);

		if (urlsMap != null) {
			for (LinkedHashMap<String, Object> map : urlsMap) {
				System.out.println("Url : id= " + map.get("id") + " - Alias= " + map.get("alias") + " - Url Longa= "
						+ map.get("urlLarge"));				
			}
		} else {
			System.out.println("Sem urls cadastradas");
		}
	}

	public static void getUrlByAlias(String alias) {
		URLConnection connection;
		try {
			connection = new URL(REST_SERVICE_URI + "u/" + alias).openConnection();
			connection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			connection.connect();
			connection.getContentType();
			System.out.println("URL para redirecionar = " + connection.getURL());
		} catch (IOException e) {
			System.out.println("Link não é uma url");	
		} 

	}

	public static void createUrl(String alias, String urlLarge) {
		System.out.println("Criando url");
		RestTemplate restTemplate = new RestTemplate();
		Url url = new Url(alias, urlLarge);
		try {
			url = restTemplate.postForObject(
					REST_SERVICE_URI + "create/?urlLarge=" + url.getUrlLarge() + "&alias=" + url.getAlias(), null,
					Url.class);
			System.out.println("Url criada: " + REST_SERVICE_URI + "u/" + url.getAlias() + " ---- Gerada da url "
					+ url.getUrlLarge());
		} catch (HttpClientErrorException e) {
			System.out.println("Custom Alias já existe");
		}

	}	

}

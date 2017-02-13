package br.com.pedro.bemobi;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import br.com.pedro.bemobi.model.Url;
import br.com.pedro.bemobi.repositories.UrlRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlShortenerApplicationTests {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON_UTF8.getType(),
			MediaType.APPLICATION_JSON_UTF8.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJson2Http;

	private List<Url> Urls = new ArrayList<>();

	@Autowired
	private UrlRepository urlRepository;

	@Autowired
	private WebApplicationContext applicationContext;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJson2Http = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		assertNotNull("O converter do Json não pode ser nulo");

	}

	@Before
	public void setup() throws Exception {

		this.mockMvc = webAppContextSetup(applicationContext).build();

		this.urlRepository.deleteAllInBatch();

		this.Urls.add(urlRepository.save(new Url("Auzqu6", "https://www.youtube.com/")));
		this.Urls.add(urlRepository.save(new Url("4zUKfs", "http://www.hltv.org/matches/")));

	}
	
	@Test
	public void readAllUrls() throws Exception {
		mockMvc.perform(get("/shortener")).andExpect(status().isOk()).andExpect(content().contentType(contentType));

	}
	
	@Test
	public void readTopUrls() throws Exception {
		mockMvc.perform(get("/shortener/top10/")).andExpect(status().isOk()).andExpect(content().contentType(contentType));

	}

	@Test
	public void readSingleUrl() throws Exception {
		mockMvc.perform(get("/shortener/u/" + Urls.get(0).getAlias())).andExpect(status().is3xxRedirection());
	}
	
	@Test
	public void readNotFound() throws Exception {
		mockMvc.perform(get("/shortener/u/0" )).andExpect(status().isNotFound());
	}

	@Test
	public void createUrl() throws Exception {
		Url url = new Url("XKwpHG", "https://github.com/bemobi/hire.me");
		String UrlJson = json(url);
		this.mockMvc.perform(post("/shortener/create?").param("urlLarge", url.getUrlLarge()).param("alias", url.getAlias()).contentType(contentType).content(UrlJson))
				.andExpect(status().isCreated());		

	}
	
	@Test
	public void aliasExistent() throws Exception {
		Url url = new Url("Auzqu6", "https://github.com/bemobi/hire.me");
		String UrlJson = json(url);
		this.mockMvc.perform(post("/shortener/create?").param("urlLarge", url.getUrlLarge()).param("alias", url.getAlias()).contentType(contentType).content(UrlJson))
				.andExpect(status().isConflict());		

	}

	@SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJson2Http.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}

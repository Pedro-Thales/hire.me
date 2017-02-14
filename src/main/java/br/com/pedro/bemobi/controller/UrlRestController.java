package br.com.pedro.bemobi.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.pedro.bemobi.builder.ViewBuilder;
import br.com.pedro.bemobi.model.Url;
import br.com.pedro.bemobi.repositories.UrlRepository;
import br.com.pedro.bemobi.services.UrlService;

@RestController
@RequestMapping("/shortener")
public class UrlRestController {

	ModelAndView vb = new ViewBuilder();

	@Autowired
	private UrlRepository repository;

	@Autowired
	UrlService service;

	@GetMapping
	public ResponseEntity<Collection<Url>> getUrls() {
		return new ResponseEntity<>((Collection<Url>) repository.findAll(), HttpStatus.OK);
	}

	@GetMapping(value = "/u/{alias}")
	public ModelAndView findUrlWithAlias(@PathVariable String alias) {
		ResponseEntity<Url> response;
		Url url = repository.findByAlias(alias);
		if (url != null) {
			response = new ResponseEntity<>(url, HttpStatus.OK);
			url.setUses(url.getUses() + 1);	
			repository.save(url);
			return new ModelAndView("redirect:" + response.getBody().getUrlLarge());

		} else {
			return ((ViewBuilder) vb).createError002(alias);
		}

	}

	@GetMapping(value = "/top10/")
	public ResponseEntity<Collection<Url>> findUrlTop() {
		return new ResponseEntity<>((Collection<Url>) repository.findTop10ByOrderByUsesDesc(), HttpStatus.OK);

	}

	@PostMapping(value = "/create")
	public ModelAndView createUrl(@RequestParam(required = true) String urlLarge,
			@RequestParam(required = false) String alias) {
		StopWatch timer = new StopWatch();
		timer.start();
		Url url = new Url(alias, urlLarge);
		try {
			url = service.save(urlLarge, alias);
		} catch (DataIntegrityViolationException e) {
			return ((ViewBuilder) vb).createError001(alias);
		}
		timer.stop();
		return ((ViewBuilder) vb).createView(url, timer.getTotalTimeMillis());

	}

}

package br.com.pedro.bemobi.builder;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import br.com.pedro.bemobi.model.Url;

public class ViewBuilder extends ModelAndView {
	
	private final static String BASE_URL= "http://localhost:8080/shortener/u/";	
	

	public ModelAndView createError001(Object alias){
		ModelAndView mav = new ModelAndView();
		mav.setView(new MappingJackson2JsonView());
		mav.addObject("alias", alias);
		mav.addObject("err_code", 001);
		mav.setStatus(HttpStatus.CONFLICT);
		mav.addObject("description", "CUSTOM ALIAS ALREADY EXISTS");
		return mav;
	}
	
	public ModelAndView createError002(Object alias){
		ModelAndView mav = new ModelAndView();
		mav.setView(new MappingJackson2JsonView());
		mav.addObject("alias", alias);
		mav.addObject("err_code", 002);
		mav.setStatus(HttpStatus.NOT_FOUND);
		mav.addObject("description", "SHORTENED URL NOT FOUND");
		return mav;
	}
	
	public ModelAndView createView(Url url, long timer){
		ModelAndView mav = new ModelAndView();
		mav.setView(new MappingJackson2JsonView());
		mav.setStatus(HttpStatus.CREATED);
		mav.addObject("alias", url.getAlias());
		mav.addObject("url", BASE_URL + url.getAlias());
		mav.addObject("urlLarge", url.getUrlLarge());
		mav.addObject("statistics:", timer+ "ms");
		return mav;
	}
	
	public ModelAndView createViews(List<Url> urls){	
		ModelAndView mav = new ModelAndView();
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.setContentType("application/json;charset=UTF-8");
		mav.setView(view);		
		mav.addObject("Top 10 mais usadas", urls);
		return mav;
	}
	

}

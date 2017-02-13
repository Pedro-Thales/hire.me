package br.com.pedro.bemobi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pedro.bemobi.model.Url;
import br.com.pedro.bemobi.repositories.UrlRepository;

@Service
public class UrlService {

	//Sem vogais ajuda no controle mais preciso, porque geralmente o usuario cria aliases com vogais, 
	//sendo assim o algoritmo tem menos chances de gerar aliases que usuarios poderiam utilizar.
	public static final String ALPHABET = "123456789bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ-_";
	public static final int BASE = ALPHABET.length();

	@Autowired
	private UrlRepository repository;
	
	private long id;
	StringBuilder str;

	public void generateAlias(Long cod) {
		str = new StringBuilder();
		while (cod > 0) {
			str.insert(0, ALPHABET.charAt((int) (cod % BASE)));
			cod = cod / BASE;
		}
		if (repository.findByAlias(str.toString()) != null) {
			// O algoritmo nunca vai gerar alias repetidos, porém pode bater com
			// um alias criado por um usuario, nesse caso vai entrar nessa
			// condição.
			id += 1000;
			generateAlias(id);
		}
	}

	public Url save(String urlLarge, String alias) {
		Url url;
		if (alias == null || alias.isEmpty()) {
			url = new Url(urlLarge);
			id = repository.count();
			generateAlias(id);
			url.setAlias(str.toString());
			repository.save(url);

		} else {
			url = new Url(alias, urlLarge);
			repository.save(url);
		}
		return url;
	}

}

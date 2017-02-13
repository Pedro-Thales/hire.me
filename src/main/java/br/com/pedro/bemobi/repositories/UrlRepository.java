package br.com.pedro.bemobi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pedro.bemobi.model.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {
	public Url findByAlias(String alias);	
	public Url findByUrlLarge(String urlLarge);
	public List<Url> findTop10ByOrderByUsesDesc();

}

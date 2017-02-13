package br.com.pedro.bemobi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Url {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(unique=true)
	@NotNull
	private String alias;	
	private String urlLarge;	
	private int uses = 0;

	public Url() {
	}

	public Url(String urlLarge) {
		super();
		this.urlLarge = urlLarge;
	}
	
	public Url(String alias, String urlLarge) {
		super();
		this.alias = alias;
		this.urlLarge = urlLarge;
	}

	public Long getId() {
		return id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getUrlLarge() {
		return urlLarge;
	}

	public void setUrlLarge(String urlLarge) {
		this.urlLarge = urlLarge;
	}

	public int getUses() {
		return uses;
	}

	public void setUses(int uses) {
		this.uses = uses;
	}

}

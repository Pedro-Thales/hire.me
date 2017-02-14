package br.com.pedro.bemobi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import br.com.pedro.bemobi.services.UrlService;

@SpringBootApplication
public class UrlShortenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortenerApplication.class, args);
	}
	
	
	@Bean
    public CommandLineRunner initializeDb(UrlService service){
        return (args) -> {
            //Insert some random urls           
            service.save("https://github.com/Pedro-Thales/hire.me","hire-me");
            service.save("http://www.bemobi.com.br/","bemobi");
            /*for (int i=0; i<1000000;i++){
            	service.save("http://www.bemobi.com.br/", null);
            }*/
        };
	}
}

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
            service.save("http://www.hltv.org/matches/","31");
            service.save("http://www.hltv.org/matches/","32");
            service.save("http://www.hltv.org/matches/","33");
            service.save("http://www.hltv.org/matches/","34");
            service.save("http://www.hltv.org/matches/","35");
            service.save("http://www.hltv.org/matches/","36");
            service.save("http://www.hltv.org/matches/","39");
            service.save("http://www.hltv.org/matches/","37");
            service.save("http://www.hltv.org/matches/","k");
            service.save("http://www.hltv.org/matches/","l");
            service.save("http://www.hltv.org/matches/","m");
            service.save("http://www.hltv.org/matches/","n");
            service.save("http://www.hltv.org/matches/","o");
            service.save("http://www.hltv.org/matches/","p");
            service.save("http://www.hltv.org/matches/","q");
            service.save("http://www.hltv.org/matches/","r");
            service.save("http://www.hltv.org/matches/","s");
            service.save("http://www.hltv.org/matches/", "t");
            service.save("http://www.hltv.org/matches/","u");
            service.save("http://www.hltv.org/matches/","v");
            service.save("http://www.hltv.org/matches/","w");
            service.save("http://www.hltv.org/matches/","x");
            service.save("https://www.youtube.com/","z");
            service.save("https://www.youtube.com/","");
            /*for (int i=0; i<1000000;i++){
            	service.save("https://www.youtube.com/", null);
            }*/
        };
	}
}

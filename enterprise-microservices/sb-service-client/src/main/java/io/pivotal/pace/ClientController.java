package io.pivotal.pace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class ClientController {
	
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/")
    //@HystrixCommand(fallbackMethod = "fallbackPhrase")
	public String phrase() {
		
		URI uri = UriComponentsBuilder.fromUriString("//sb-service/language")
	            .build()
	            .toUri();
		
		String language = restTemplate.getForObject(uri, String.class);
		return "Greeting language is " + language;
	}
	
	public String fallbackPhrase() {
		return "Greeting language is gibberish!!";
	}
}

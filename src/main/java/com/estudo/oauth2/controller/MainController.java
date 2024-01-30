package com.estudo.oauth2.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Logger;



@RestController
public class MainController {
	

	/*
	 * como obter e utilizar os detalhes de um usuário autenticado. 
	 *  Na arquitetura do Spring Security, é o SecurityContext que armazena os detalhes de um usuário autenticado. 
	 */
	
	/**
	 * 
	 * Assim que o processo de autenticação é concluído, o filtro responsável armazena o objeto Authentication no SecurityContext. 
	 * A aplicação pode recuperar os detalhes do usuário a partir daí e utilizá-los quando necessário. O mesmo ocorre com uma autenticação OAuth 2.
	 * 
	 * 
	 * A implementação do objeto Authentication utilizada pelo framework é chamada OAuth2AuthenticationToken neste caso.
	 *  Você pode recuperá-lo diretamente do SecurityContext ou permitir que o Spring Boot o injete para você como um parâmetro no endpoint;
	 * 
	 * 
	 *  trecho a seguir mostra como eu alterei o controlador para receber e imprimir detalhes sobre um usuário no console.
	 *  
	 *  
	 *     O Spring Boot automaticamente injeta o objeto Authentication representando o usuário no parâmetro do método.

           Isso significa que, ao criar um método em um componente Spring (como um controlador), você pode declarar um parâmetro do tipo Authentication 
            e o Spring Boot, automaticamente, fornecerá uma instância desse objeto preenchida com as informações do usuário autenticado durante a execução do método. 
            Essa facilidade simplifica o acesso aos detalhes do usuário sem a necessidade de buscar manualmente no SecurityContext.
	 * 
	 */
	
	
	private Logger logger = Logger.getLogger(MainController.class.getName());
	
	@GetMapping("/")
	public String main(OAuth2AuthenticationToken token) {
		
		
		logger.info(String.valueOf(token.getPrincipal()));
		
	   return "<!DOCTYPE html>\r\n"
	   		+ "<html lang=\"en\">\r\n"
	   		+ "<head>\r\n"
	   		+ "    <meta charset=\"UTF-8\">\r\n"
	   		+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
	   		+ "    <title>Minha Página</title>\r\n"
	   		+ "</head>\r\n"
	   		+ "<body>\r\n"
	   		+ "    <h1>Hello there!</h1>\r\n"
	   		+ "</body>\r\n"
	   		+ "</html>";
		
	}

}

package com.estudo.oauth2.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nimbusds.jose.proc.SecurityContext;

/**
 * 
 * Classe que desabilita a configuração padrão do spring security
 */
@EnableWebSecurity
@Configuration
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {
	

	/*
	 * 
	 * ClientRegistration - antes de usar ClientRegistration
	 * 
	 * A razão pela qual você não pode acessar a página é que você especificou que,
	 * para qualquer solicitação, o usuário precisa se autenticar, mas você não
	 * forneceu nenhuma maneira de autenticação. Precisamos estabelecer que o GitHub
	 * é nosso servidor de autorização. Para este propósito, o Spring Security
	 * define o contrato ClientRegistration. A interface ClientRegistration
	 * representa o cliente na arquitetura OAuth 2. Para o cliente, você precisa
	 * definir todos os detalhes necessários, entre os quais temos:
	 * 
	 * O ID e segredo do cliente O tipo de concessão usado para autenticação A URI
	 * de redirecionamento Os escopos
	 * 
	 * 
	 * 
	 * 
	 */

	/**
	 * implementação da ligação entre o cliente OAuth 2(nossa aplicação) e o
	 * servidor de autorização(Github).
	 * 
	 * 
	 * O Spring Security também oferece uma maneira fácil de criar uma instância de
	 * um construtor, semelhante ao que você já usou para criar instâncias de
	 * UserDetails.
	 * 
	 * 
	 * como construir tal instância representando nossa implementação de cliente com
	 * o construtor fornecido pelo Spring Security. Na listagem seguinte , mostro
	 * como fornecer todos os detalhes, mas para alguns provedores
	 * 
	 * é ainda mais fácil do que isso.
	 * 
	 * 
	 * Abaixo é configurado o ID do client
	 * 
	 * O Client Secret
	 * 
	 * Os Escopos (Autorizações concedidas )
	 * 
	 * Um nome de client e um registro de client
	 * 
	 * URI de Autorização — A URI para a qual o cliente redireciona o usuário para
	 * autenticação.
	 * 
	 * URI do Token — A URI que o cliente chama para obter um token de acesso e um
	 * token de atualização
	 * 
	 * 
	 * URI de Informações do Usuário — A URI que o cliente pode chamar após obter um
	 * token de acesso para obter mais detalhes sobre o usuário.
	 * 
	 * 
	 */

	/**
	 * 
	 * 
	 * ClientRegistration cr = ClientRegistration.withRegistrationId("github")
	 * .clientId("c15136dae0b2d1b3d6e1")
	 * .clientSecret("878b82d3f061786d4a7814b0380fe8ccf6800746") .scope(new String[]
	 * {"read:user"}) .authorizationUri("https://github.com/login/oauth/authorize")
	 * .tokenUri("https://github.com/login/oauth/access_token")
	 * .userInfoUri("https://api.github.com/user") .userNameAttributeName("id")
	 * .clientName("GitHub")
	 * .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
	 * .redirectUriTemplate("baseUrl}/{action}/oauth2/code/{registrationId}")
	 * .build();
	 * 
	 * 
	 */

	/*
	 * 
	 * Using the CommonOAuth2Provider class
	 * 
	 * Espere! Spring Security é ainda mais inteligente que isso. A estrutura define
	 * uma classe chamada CommonOAuth2Provider. esta classe define parcialmente as
	 * instâncias de ClientRegistration para os provedores mais comuns que você pode
	 * usar para autenticação, incluindo: Google Github Facebook Okta
	 * 
	 * Para ter as URIs correspondentes já configuradas, selecione o provedor do
	 * GitHub
	 * 
	 * 
	 * Fornece um ID para o registro do cliente.
	 * 
	 * Define as credenciais do cliente.
	 * 
	 * Constrói a instância do ClientRegistration.
	 * 
	 * 
	 * 
	 */

	/*
	 * Chama este método   clientRepository() mais tarde para obter o objeto ClientRegistration
	 * retornado.
	 * 
	 * 
	 * Aprendemos como representar o cliente OAuth 2 para o Spring Security ao
	 * implementar o contrato ClientRepository. No entanto, é necessário configurar
	 * isso para utilizá-lo na autenticação. Para esse propósito, o Spring Security
	 * utiliza um objeto do tipo ClientRegistrationRepository.
	 * 
	 * 
	 * O ClientRegistrationRepository é uma interface do Spring Security que serve
	 * como um repositório para manter as informações dos registros de clientes
	 * necessárias para autenticação com o servidor de autorização OAuth 2. Ele
	 * armazena as configurações do cliente, como ID do cliente, segredo do cliente,
	 * URI de autorização, URI do token, escopos, etc.
	 * 
	 * 
	 * Ao implementar o ClientRegistrationRepository, você fornece uma maneira para
	 * o Spring Security obter dinamicamente as informações do cliente no momento da
	 * autenticação. Isso é crucial, pois permite que o sistema se adapte a
	 * diferentes provedores de autenticação OAuth 2.
	 * 
	 * 
	 * A interface ClientRegistrationRepository é semelhante à interface
	 * UserDetails- Service,Da mesma forma que um objeto User- DetailsService
	 * encontra UserDetails pelo seu nome de usuário, um objeto Client-
	 * RegistrationRepository encontra ClientRegistration pelo seu ID de registro.
	 * Você pode implementar a interface ClientRegistrationRepository para informar
	 * ao framework onde encontrar as instâncias de ClientRegistration. O Spring
	 * Security nos oferece uma implementação para o ClientRegistrationRepository,
	 * que armazena em memória as instâncias de ClientRegistration:
	 * InMemoryClientRegistration- Repository. Como você pode imaginar, isso
	 * funciona de maneira semelhante ao InMemoryUserDetails- Manager para
	 * instâncias de UserDetails.
	 * 
	 * 
	 * 
	 * 
	 * O filtro de autenticação obtém detalhes sobre os registros de clientes do
	 * servidor de autorização de um ClientRegistrationRepository.
	 * 
	 * 
	 * 
	 * OAuth2LoginAuthenticationFilte -> uses -> ClientRegistrationRepository has
	 * GitHub(ClientRegistration0) . O fluxo pode se adaptar a outros provedores como facebook ,
	 * Linkedin , Twitter
	 * 
	 * 
	 */
	
	
	/*
	 * 
	 * A anotação @Bean no método clientRepository() é utilizada para indicar ao Spring que o método retorna um objeto que deve ser gerenciado pelo contêiner Spring (chamado de bean).
	 *  No caso, o bean retornado é uma instância de ClientRegistrationRepository construída a partir de um ClientRegistration, conforme seu código.

Por outro lado, o método clientRegistration() não possui a anotação @Bean. 
Isso ocorre porque ele é um método privado usado internamente para construir uma instância de ClientRegistration.
 Ele não precisa ser gerenciado pelo contêiner Spring como um bean independente, pois é chamado apenas dentro da classe de configuração e não é injetado em outros componentes.

O uso de @Bean é frequentemente associado a métodos que retornam instâncias de classes que você deseja que o Spring gerencie e injete em outros componentes do seu aplicativo.
 No seu exemplo, o clientRepository() retorna um bean do tipo ClientRegistrationRepository que pode ser injetado em outros componentes da aplicação.
	 */
	
	public ClientRegistrationRepository clientRepository() {
		
		var c = clientRegistration();
		
		return new InMemoryClientRegistrationRepository(c);
	}
	
	
	private ClientRegistration clientRegistration() {

		return CommonOAuth2Provider.GITHUB.getBuilder("github").clientId("c15136dae0b2d1b3d6e1")
				.clientSecret("878b82d3f061786d4a7814b0380fe8ccf6800746").build();

	}

	/*
	 * 
	 * Sobrescrever o método configure(HttpSecurity http) , ams ao inves de usar o
	 * http basic ou formLogin chamar o método oauth2Login
	 * 
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/**
		 * 
		 * http.oauth2Login() configura seu aplicativo para utilizar o fluxo de login
		 * via OAuth 2.
		 * 
		 * oauth2Login() simplesmente adiciona um novo filtro de autenticação à cadeia
		 * de filtros.
		 *
		 * Assim como httpBasic() ou formLogin(), oauth2Login() simplesmente adiciona um
		 * novo filtro de autenticação à cadeia de filtros
		 * 
		 * 
		 * O nome desse filtro é OAuth2LoginAuthenticationFilter
		 * 
		 * 
		 * Este filtro intercepta as solicitações e aplica a lógica necessária para
		 * autenticação via OAuth 2.
		 * 
		 *
		 *
		 */
		
		/*
		 * 
		 * Usa um Customizer para configurar a instância do ClientRegistrationRepository.
		 * 
		 *  
		http.oauth2Login(c->{
			
			c.clientRegistrationRepository(clientRepository());
			
		});
		 * 
		 * 
		 * Ambas as opções de configuração são igualmente válidas
		 */
		 
		 
http.oauth2Login(c->{
			
			c.clientRegistrationRepository(clientRepository());
			
		});

		/*
		 * 
		 * no método configure de uma configuração do Spring Security, está
		 * especificando que o método de autenticação para todas as requisições deve ser
		 * o padrão, ou seja, os usuários precisam estar autenticados para fazer uma
		 * solicitação.
		 * 
		 * 
		 */

		http.authorizeRequests().anyRequest().authenticated();

	}

	

}

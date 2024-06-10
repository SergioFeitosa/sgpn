package br.com.j4business.saga;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class WebSecurityConfig extends WebSecurityConfiguration {

	@Autowired
	DataSource dataSource;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	
	@EnableWebSecurity
	public class SecurityConfig {
	
		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) 
		  throws Exception {
			auth.inMemoryAuthentication().withUser("user")
			  .password(passwordEncoder().encode("password")).roles("USER");
		}
	}

	@Bean
	SecurityFilterChain web(HttpSecurity http) throws Exception {
		http
			.formLogin(form -> form
				.loginPage("/login")
				.usernameParameter("username")
				.passwordParameter("password")
			.permitAll())
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/endpoint").hasAuthority("USER")
				.anyRequest().authenticated());

		return http.build();
	}
	
	
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	http
		.authorizeHttpRequests((authorize) -> authorize
			.requestMatchers("/acao*").hasAuthority("hasAnyRole('ROLE_PLANEJAMENTO','ROLE_ADMIN')")
			.requestMatchers("/agenda*").hasAuthority("hasAnyRole('ROLE_AGENDA','ROLE_ADMIN')")
			.requestMatchers("/atendimento*").hasAuthority("hasAnyRole('ROLE_OCORRENCIA','ROLE_ADMIN')")
			.requestMatchers("/atividade*").hasAuthority("hasAnyRole('ROLE_PROCESSO','ROLE_ADMIN')")
			.requestMatchers("/atributo*").hasAuthority("hasAnyRole('ROLE_ATRIBUTO','ROLE_ADMIN')")
			.requestMatchers("/avaliacao*").hasAuthority("hasAnyRole('ROLE_AVALIACAO','ROLE_ADMIN')")
			.requestMatchers("/bairro*").hasAuthority("hasAnyRole('ROLE_ENDERECO','ROLE_ADMIN')")
			.requestMatchers("/cenario*").hasAuthority("hasAnyRole('ROLE_CENARIO','ROLE_ADMIN')")
			.requestMatchers("/certificacao*").hasAuthority("hasAnyRole('ROLE_CAPACITACAO','ROLE_ADMIN')")
			.requestMatchers("/colaborador*").hasAuthority("hasAnyRole('ROLE_COLABORADOR','ROLE_ADMIN')")
			.requestMatchers("/contrato*").hasAuthority("hasAnyRole('ROLE_CONTRATO','ROLE_ADMIN')")
			.requestMatchers("/curso*").hasAuthority("hasAnyRole('ROLE_CAPACITACAO','ROLE_ADMIN')")
			.requestMatchers("/detalhe*").hasAuthority("hasAnyRole('ROLE_ATRIBUTO','ROLE_ADMIN')")
			.requestMatchers("/elemento*").hasAuthority("hasAnyRole('ROLE_CENARIO','ROLE_ADMIN')")
			.requestMatchers("/email*").hasAuthority("hasAnyRole('ROLE_EMAIL','ROLE_ADMIN')")
			.requestMatchers("/empresa*").hasAuthority("hasAnyRole('ROLE_EMPRESA','ROLE_ADMIN')")
			.requestMatchers("/estado*").hasAuthority("hasAnyRole('ROLE_ENDERECO','ROLE_ADMIN')")
			.requestMatchers("/estruturafisica*").hasAuthority("hasAnyRole('ROLE_ESTRUTURAFISICA','ROLE_ADMIN')")
			.requestMatchers("/evento*").hasAuthority("hasAnyRole('ROLE_EVENTO','ROLE_ADMIN')")
			.requestMatchers("/formacao*").hasAuthority("hasAnyRole('ROLE_CAPACITACAO','ROLE_ADMIN')")
			.requestMatchers("/fornecedor*").hasAuthority("hasAnyRole('ROLE_FORNECEDOR','ROLE_ADMIN')")
			.requestMatchers("/funcao*").hasAuthority("hasAnyRole('ROLE_COLABORADOR','ROLE_ADMIN')")
			.requestMatchers("/habilidade*").hasAuthority("hasAnyRole('ROLE_CAPACITACAO','ROLE_ADMIN')")
			.requestMatchers("/logradouro*").hasAuthority("hasAnyRole('ROLE_ENDERECO','ROLE_ADMIN')")
			.requestMatchers("/municipio*").hasAuthority("hasAnyRole('ROLE_ENDERECO','ROLE_ADMIN')")
			.requestMatchers("/ocorrencia*").hasAuthority("hasAnyRole('ROLE_OCORRENCIA','ROLE_ADMIN')")
			.requestMatchers("/pais*").hasAuthority("hasAnyRole('ROLE_ENDERECO','ROLE_ADMIN')")
			.requestMatchers("/perfil*").hasAuthority("hasAnyRole('ROLE_USUARIO','ROLE_ADMIN')")
			.requestMatchers("/planejamento*").hasAuthority("hasAnyRole('ROLE_PLANEJAMENTO','ROLE_ADMIN')")
			.requestMatchers("/processo*").hasAuthority("hasAnyRole('ROLE_PROCESSO','ROLE_ADMIN')")
			.requestMatchers("/questao*").hasAuthority("hasAnyRole('ROLE_AVALIACAO','ROLE_ADMIN')")
			.requestMatchers("/questionario*").hasAuthority("hasAnyRole('ROLE_AVALIACAO','ROLE_ADMIN')")
			.requestMatchers("/servico*").hasAuthority("hasAnyRole('ROLE_PROCESSO','ROLE_ADMIN')")
			.requestMatchers("/tipo*").hasAuthority("hasAnyRole('ROLE_ENDERECO','ROLE_ADMIN')")
			.requestMatchers("/treinamento*").hasAuthority("hasAnyRole('ROLE_CAPACITACAO','ROLE_ADMIN','ROLE_VIDEO')")
			.requestMatchers("/unidadeorganizacional*").hasAuthority("hasAnyRole('ROLE_ESTRUTURAFISICA','ROLE_ADMIN')")
			.requestMatchers("/usuario*").hasAuthority("hasAnyRole('ROLE_USUARIO','ROLE_ADMIN')")
			.requestMatchers("/video*").hasAuthority("hasAnyRole('ROLE_VIDEO','ROLE_ADMIN')")
			.anyRequest().authenticated()
		);
	return http.build();
}

	/*
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("select username,password, enabled from users where username=?")
			.authoritiesByUsernameQuery("select username, role from user_roles where username=?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
		.antMatchers("/acao*").access("hasAnyRole('ROLE_PLANEJAMENTO','ROLE_ADMIN')")
		.antMatchers("/agenda*").access("hasAnyRole('ROLE_AGENDA','ROLE_ADMIN')")
		.antMatchers("/atendimento*").access("hasAnyRole('ROLE_OCORRENCIA','ROLE_ADMIN')")
		.antMatchers("/atividade*").access("hasAnyRole('ROLE_PROCESSO','ROLE_ADMIN')")
		.antMatchers("/atributo*").access("hasAnyRole('ROLE_ATRIBUTO','ROLE_ADMIN')")
		.antMatchers("/avaliacao*").access("hasAnyRole('ROLE_AVALIACAO','ROLE_ADMIN')")
		.antMatchers("/bairro*").access("hasAnyRole('ROLE_ENDERECO','ROLE_ADMIN')")
		.antMatchers("/cenario*").access("hasAnyRole('ROLE_CENARIO','ROLE_ADMIN')")
		.antMatchers("/certificacao*").access("hasAnyRole('ROLE_CAPACITACAO','ROLE_ADMIN')")
		.antMatchers("/colaborador*").access("hasAnyRole('ROLE_COLABORADOR','ROLE_ADMIN')")
		.antMatchers("/contrato*").access("hasAnyRole('ROLE_CONTRATO','ROLE_ADMIN')")
		.antMatchers("/curso*").access("hasAnyRole('ROLE_CAPACITACAO','ROLE_ADMIN')")
		.antMatchers("/detalhe*").access("hasAnyRole('ROLE_ATRIBUTO','ROLE_ADMIN')")
		.antMatchers("/elemento*").access("hasAnyRole('ROLE_CENARIO','ROLE_ADMIN')")
		.antMatchers("/email*").access("hasAnyRole('ROLE_EMAIL','ROLE_ADMIN')")
		.antMatchers("/empresa*").access("hasAnyRole('ROLE_EMPRESA','ROLE_ADMIN')")
		.antMatchers("/estado*").access("hasAnyRole('ROLE_ENDERECO','ROLE_ADMIN')")
		.antMatchers("/estruturafisica*").access("hasAnyRole('ROLE_ESTRUTURAFISICA','ROLE_ADMIN')")
		.antMatchers("/evento*").access("hasAnyRole('ROLE_EVENTO','ROLE_ADMIN')")
		.antMatchers("/formacao*").access("hasAnyRole('ROLE_CAPACITACAO','ROLE_ADMIN')")
		.antMatchers("/fornecedor*").access("hasAnyRole('ROLE_FORNECEDOR','ROLE_ADMIN')")
		.antMatchers("/funcao*").access("hasAnyRole('ROLE_COLABORADOR','ROLE_ADMIN')")
		.antMatchers("/habilidade*").access("hasAnyRole('ROLE_CAPACITACAO','ROLE_ADMIN')")
		.antMatchers("/logradouro*").access("hasAnyRole('ROLE_ENDERECO','ROLE_ADMIN')")
		.antMatchers("/municipio*").access("hasAnyRole('ROLE_ENDERECO','ROLE_ADMIN')")
		.antMatchers("/ocorrencia*").access("hasAnyRole('ROLE_OCORRENCIA','ROLE_ADMIN')")
		.antMatchers("/pais*").access("hasAnyRole('ROLE_ENDERECO','ROLE_ADMIN')")
		.antMatchers("/perfil*").access("hasAnyRole('ROLE_USUARIO','ROLE_ADMIN')")
		.antMatchers("/planejamento*").access("hasAnyRole('ROLE_PLANEJAMENTO','ROLE_ADMIN')")
		.antMatchers("/processo*").access("hasAnyRole('ROLE_PROCESSO','ROLE_ADMIN')")
		.antMatchers("/questao*").access("hasAnyRole('ROLE_AVALIACAO','ROLE_ADMIN')")
		.antMatchers("/questionario*").access("hasAnyRole('ROLE_AVALIACAO','ROLE_ADMIN')")
		.antMatchers("/servico*").access("hasAnyRole('ROLE_PROCESSO','ROLE_ADMIN')")
		.antMatchers("/tipo*").access("hasAnyRole('ROLE_ENDERECO','ROLE_ADMIN')")
		.antMatchers("/treinamento*").access("hasAnyRole('ROLE_CAPACITACAO','ROLE_ADMIN','ROLE_VIDEO')")
		.antMatchers("/unidadeorganizacional*").access("hasAnyRole('ROLE_ESTRUTURAFISICA','ROLE_ADMIN')")
		.antMatchers("/usuario*").access("hasAnyRole('ROLE_USUARIO','ROLE_ADMIN')")
		.antMatchers("/video*").access("hasAnyRole('ROLE_VIDEO','ROLE_ADMIN')")
		.anyRequest()
		.permitAll()
	.and()
		.formLogin()
		.loginPage("/login")
		.usernameParameter("username")
		.passwordParameter("password")
	.and()
		.logout()
		.logoutSuccessUrl("/login?logout")
	.and()
		.exceptionHandling()
		.accessDeniedPage("/login")
	.and()
		.csrf();
	}

	*/
}

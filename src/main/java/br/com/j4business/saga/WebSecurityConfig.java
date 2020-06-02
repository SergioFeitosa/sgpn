package br.com.j4business.saga;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

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
	
}

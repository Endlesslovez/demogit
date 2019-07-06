package product.spring.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
////
//	@Bean
//	public BCryptPasswordEncoder bCryptPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	@Autowired
//	private DataSource dataSource;
//
//	@Value("${spring.queries.users-query}")
//	private String usersQuery;
//
//	@Value("${spring.queries.roles-query}")
//	private String rolesQuery;
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
//				.dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/login").permitAll().antMatchers("/admincategoy/categorylist/")
//				.hasAnyRole("ADMIN").antMatchers("/adminproduct/adminproduct1/").hasAnyRole("ADMIN")
//
//				.and()
//
//				.csrf().disable().formLogin().loginPage("/login").failureUrl("/login?error=true")
//				.defaultSuccessUrl("/").usernameParameter("username").passwordParameter("password").and().logout()
//				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").and()
//				.exceptionHandling().accessDeniedPage("/access-denied");
//	}
//
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
//	}
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder).withUser("user@gmail.com")
				.password(passwordEncoder.encode("123456")).roles("USER").and().withUser("admin@gmail.com")
				.password(passwordEncoder.encode("123456")).roles("ADMIN");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login").permitAll().antMatchers("/productlistoppo/**").permitAll()
				.antMatchers("/admincategoy/categorylist/").hasAnyRole("USER")
				.antMatchers("/adminproduct/adminproduct1/").hasAnyRole("USER")
				.antMatchers("/useradmin/inforuser/").hasAnyRole("USER")
				.and().formLogin().loginPage("/login").defaultSuccessUrl("/").failureUrl("/login?error=true")
				.permitAll().and().logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true)
				.permitAll().and().csrf().disable();
	}
}

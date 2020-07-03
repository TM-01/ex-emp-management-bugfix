package jp.co.sample.emp_management;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //ログインページを指定。
        //ログインページへのアクセスは全員許可する。
        http.authorizeRequests()
        	.mvcMatchers("/**", "/employee/**").permitAll()
        	.and()
        	.formLogin()
            .loginPage("/")
            .permitAll();

        http.authorizeRequests()
            .anyRequest().authenticated();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
      web
        .debug(false)
        .ignoring()
        .antMatchers("/img/**", "/js/**", "/css/**")
      ;
    }
    
    @Bean
    public PasswordEncoder getBean() {
    	return new BCryptPasswordEncoder();
    }
}
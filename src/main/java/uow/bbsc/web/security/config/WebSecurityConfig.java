package uow.bbsc.web.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import uow.bbsc.web.data.customer.CustomerService;

import static uow.bbsc.web.data.customer.UserRole.*;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomerService customerService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy","script-src 'self'"))
                .contentTypeOptions();
        http
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                    .antMatchers("/item/**").permitAll()
                    .antMatchers("/database/customer/**").permitAll()
                    .antMatchers("/update/**").permitAll()
                    .antMatchers("/database/shop/**").hasAnyAuthority(ADMIN.name(),SHOP.name(), USER.name())
                    .antMatchers("/user/payCart").hasAnyAuthority(ADMIN.name(),SHOP.name(), USER.name())
                    .antMatchers("/database/item/**").hasAnyAuthority(ADMIN.name(),SHOP.name())
                    .antMatchers("/admin/**").hasAuthority(ADMIN.name())
                    .antMatchers("/shop/my_shop**").hasAnyAuthority(ADMIN.name(), SHOP.name())
//                    .antMatchers("/user/**").hasAnyAuthority(ADMIN.name(),SHOP.name(),USER.name())
                .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/loginAction")
                    .defaultSuccessUrl("/redirectPreviousPage", false)
                    .permitAll()
                .and()
                .sessionManagement()
                .invalidSessionUrl("/logout")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll()
                .deleteCookies("JSESSIONID");
        http.headers().frameOptions().sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(customerService);
        return provider;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/error**","/resources/**", "/static/**", "/css/**", "/js/**", "/img/**");

    }

    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200/");
        configuration.addAllowedOrigin("http://localhost:9000/");
        configuration.addAllowedOrigin("http://localhost:9001/");
        configuration.addAllowedOrigin("http://localhost:8080/");
        configuration.addAllowedOrigin("http://localhost:80/");

        configuration.addAllowedMethod("*");

        configuration.addAllowedHeader("*");
        configuration.setMaxAge(3600L);
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration)

        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return new CorsFilter(source);
    }
}

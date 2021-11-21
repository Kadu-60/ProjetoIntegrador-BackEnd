package br.com.rd.ProjetoIntegrador.security;

import br.com.rd.ProjetoIntegrador.service.DetalheClienteServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class JWTConfiguracao extends WebSecurityConfigurerAdapter {

    private final DetalheClienteServiceImpl clienteService;
    private final PasswordEncoder passwordEncoder;


    public JWTConfiguracao(DetalheClienteServiceImpl clienteService, PasswordEncoder passwordEncoder) {
        this.clienteService = clienteService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(clienteService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login", "/cadastroCliente/salvar", "/Card/multi"
                        ,"/formulariocontato").permitAll()
                .antMatchers(HttpMethod.GET, "/home/categorias", "/home/destaques", "/home/novidades"
                        ,"/produtos", "/produtos/{id}",
                        "/produtos/por-categoria/{id}", "/produtos/por-marca/{id}", "/produtos/por-familia/{id}"
                        , "/produtos/por-prato/{id}", "/Marca"
                        ,"/produtos/buscar/{id}","/formulariocontato/", "/preco/{id}", "/Card/multi","/Card/busca/{busca}", "/Card/{id}", "/preco/findAllById_produto/{id}").permitAll()
                .antMatchers(HttpMethod.PUT,"/cadastroCliente/alterarSenha").permitAll()
                .anyRequest().authenticated()
                .and()
                .cors().configurationSource(corsConfigurationSource()).and()
                .addFilter(new JWTAutenticarFilter(authenticationManager()))
                .addFilter(new JWTValidarFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}

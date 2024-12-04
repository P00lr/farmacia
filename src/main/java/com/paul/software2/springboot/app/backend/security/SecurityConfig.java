package com.paul.software2.springboot.app.backend.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.paul.software2.springboot.app.backend.security.filter.JwtAutentificacionFilter;
import com.paul.software2.springboot.app.backend.security.filter.JwtValidacionFilter;

@Configuration
public class SecurityConfig {

   @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests((authz) -> authz
        //****************RUTAS PUBLICAS************************
        .requestMatchers(HttpMethod.POST, "/api/usuarios/registrar").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/usuarios/page/{page}").permitAll()
        
        .requestMatchers(HttpMethod.GET, "/api/clientes/page/{page}").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/empleados/page/{page}").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/laboratorios/page/{page}").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/proveedores/page/{page}").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/medicamentos/page/{page}").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/almacenes/page/{page}").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/compras/page/{page}").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/ventas/page/{page}").permitAll()




        //*************RUTAS PROTEGIDAS**************************
        //USUARIOS
        .requestMatchers(HttpMethod.GET, "/api/usuarios").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.GET, "/api/usuarios/{id}").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.POST, "/api/usuarios").hasRole("ADMIN")
        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/{id}").hasRole("ADMIN")
        
        //CLIENTES
        .requestMatchers(HttpMethod.GET, "/api/clientes").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.GET, "/api/clientes/{id}").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.POST, "/api/clientes").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.DELETE, "/api/clientes/{id}").hasAnyRole("USER", "ADMIN")

        //EMPLEADOS
        .requestMatchers(HttpMethod.GET, "/api/empleados").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.GET, "/api/empleados/{id}").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.POST, "/api/empleados").hasRole("ADMIN")
        .requestMatchers(HttpMethod.DELETE, "/api/empleados/{id}").hasAnyRole("USER", "ADMIN")

        //LABORATORIOS
        .requestMatchers(HttpMethod.GET, "/api/laboratorios").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.GET, "/api/laboratorios/{id}").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.POST, "/api/laboratorios").hasRole("ADMIN")
        .requestMatchers(HttpMethod.DELETE, "/api/laboratorios/{id}").hasAnyRole("USER", "ADMIN")

        //PROVEEDORES
        .requestMatchers(HttpMethod.GET, "/api/proveedores").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.GET, "/api/proveedores/{id}").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.POST, "/api/proveedores").hasRole("ADMIN")
        .requestMatchers(HttpMethod.DELETE, "/api/proveedores/{id}").hasAnyRole("USER", "ADMIN")

        //MEDICAMENTOS
        .requestMatchers(HttpMethod.GET, "/api/medicamentos").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.GET, "/api/medicamentos/{id}").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.POST, "/api/medicamentos").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.DELETE, "/api/medicamentos/{id}").hasAnyRole("USER", "ADMIN")

        //VENTAS
        .requestMatchers(HttpMethod.GET, "/api/ventas").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.GET, "/api/ventas/{id}").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.POST, "/api/ventas").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.DELETE, "/api/ventas/{id}").hasAnyRole("USER", "ADMIN")

        //COMPRAS
        .requestMatchers(HttpMethod.GET, "/api/compras").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.GET, "/api/compras/{id}").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.POST, "/api/compras").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.DELETE, "/api/compras/{id}").hasAnyRole("USER", "ADMIN")

        //ALMACENES
        .requestMatchers(HttpMethod.GET, "/api/almacenes").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.GET, "/api/almacenes/{id}").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.POST, "/api/almacenes").hasRole("ADMIN")
        .requestMatchers(HttpMethod.POST, " /api/almacenes/agregar-medicamento").hasRole("ADMIN")
        .requestMatchers(HttpMethod.DELETE, "/api/almacenes/{id}").hasAnyRole("USER", "ADMIN")

        //DASHBOARD
        .requestMatchers(HttpMethod.GET, "/api/dashboard/total-stock").hasAnyRole("USER", "ADMIN")
        .requestMatchers(HttpMethod.GET, "/stock-por-almacen/{almacenId}").hasAnyRole("USER", "ADMIN")


         //DETALLES DE VENTAS //no es necesario esta por si acaso
         /* .requestMatchers(HttpMethod.GET, "/api/ventas").hasAnyRole("USER", "ADMIN")
         .requestMatchers(HttpMethod.GET, "/api/ventas/{ventaId}/{medicamentoId}").hasAnyRole("USER", "ADMIN")
         .requestMatchers(HttpMethod.POST, "/api/ventas").hasAnyRole("USER", "ADMIN")
         .requestMatchers(HttpMethod.DELETE, "/api/venta/{ventaId}/{medicamentoId}").hasAnyRole("USER", "ADMIN") */

        // Cualquier otra ruta requiere autenticación
        .anyRequest().authenticated())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .addFilter(new JwtAutentificacionFilter(authenticationManager()))
        .addFilter(new JwtValidacionFilter(authenticationManager()))
        .csrf(config -> config.disable())
        .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
}



    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<CorsFilter>(
                new CorsFilter(corsConfigurationSource()));
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }
}
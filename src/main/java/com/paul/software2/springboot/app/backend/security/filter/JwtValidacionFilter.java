package com.paul.software2.springboot.app.backend.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paul.software2.springboot.app.backend.security.SimpleGrantedAuthoritiesJsonCreator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static com.paul.software2.springboot.app.backend.security.TokenJwtConfig.*;

public class JwtValidacionFilter extends BasicAuthenticationFilter {

    //constructor
    public JwtValidacionFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);//le pasamos este argumento a la clase padre
    }

    @Override//buscamos doFi y lo agregamos
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //obtenemos el token o la cabecera, que es enviado desde postman o el cliente
        String header = request.getHeader(HEADER_AUTHORIZATION);//el argumento esta en TokenJwtConfig
    
        //si no cumple nos salimos
        if(header == null || !header.startsWith(PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }

        //le quitamos el prefijo de Bearer lo dejamos en nada solo el token
        String token = header.replace(PREFIX_TOKEN, "");//obtiene solo el token
    
        //para validar usamos los Claims, le pasamos la llave, el try para manejarlos errores puede que el token haya expirado o no sea correcta
        try {
            Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();//con esto ya podemos obtener username, roles
            String username = claims.getSubject(); //obtenemos el username con claims
            //String username2 = (String) claims.get("username");//otra forma de obtener el username

            //esto luego lo tenemos que convertir a Grand no se que
            Object authoritiesClaims = claims.get("authorities"); //el nombre depende de que nombre le hayamos colocado, esta en la clase JwtAuthFilter
           //aqui solo validamos el token y el username, no interesa el password comom mas arriba, aqui se pone null el password
           
           //aqui pasamos los authorities claims "roles". Lo convertimos a byte el primer argumento
           //convertimos un arreglo a un collection "ArraysList" por que esto si es una collection
           Collection<? extends GrantedAuthority> authorities = Arrays.asList(
            new ObjectMapper()
           //lo que hicimos con el mixIn es acoplar el constructor de SimpleGrantedAuthority con SimpleGrantedAuthoritiesJsonCreator
           //por que en uno usamos rol y en otro authorities
           .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthoritiesJsonCreator.class)//copio la clase de SimpleGranted..JsonCreator
           .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));
           
           UsernamePasswordAuthenticationToken tokenDeAutentificacion = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(tokenDeAutentificacion);
            //cadena de filtro
            chain.doFilter(request, response);
        
        } catch (JwtException e) {
            Map<String, String> body = new HashMap<>();
            body.put("error", e.getMessage());
            body.put("message", "el token jwt es invalido");

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());//no esta autorizado, tambien solo podemos ponerle el 401 nada mas
            response.setContentType(CONTENT_TYPE);
        }
    }

}

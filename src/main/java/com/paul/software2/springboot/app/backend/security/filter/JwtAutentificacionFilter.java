package com.paul.software2.springboot.app.backend.security.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paul.software2.springboot.app.backend.entities.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.paul.software2.springboot.app.backend.security.TokenJwtConfig.*;

public class JwtAutentificacionFilter extends UsernamePasswordAuthenticationFilter{
    private AuthenticationManager authenticationManager;

    public JwtAutentificacionFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //recibimos la request y response, en request recibimos el username y password. en el response
    //la idea aqui es descerializar atrapar al json que la contraseña y username y convertir en un objeto de java
    @Override//vamos override y agregamos en el buscado att y aparace. es para intentar realizar la autentificacion
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        Usuario usuario = null;
        String username = null;
        String password = null;
            
        //aqui recibimos el json en request.getImputStream() y el otro parametro al tipo de datos que lo queremos convertir
        //y le agrego el try cath
        //importante lo que los atributos que viene del json sean los mismo que los que declare en esta clase
        //estamos poniendo los datos del json en el usuario
        //luego lo tomamos en username y password con sus get respectivos y esos datos se lo pasamos a usernamePasswordAuthenticationToken
        try {
            usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            username =  usuario.getUsername();
            password = usuario.getPassword();
        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //le pasamos el username y password vacio
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        //le pasamos usernamePasswordAuthenticationToken que tiene el username y password
        //el usernamePasswordAuthenticationToken lo que hace es llamar a JpaDetallesDeUsuarios y hace las respectivas comparaciones
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override//implementamos y escribimos succ y sale
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

            
            User user = (User)authResult.getPrincipal();//obtengo el user de spring security
            String username = user.getUsername(); //obtengo el username y se lo paso al token

            //aqui vamos a tener los roles
            Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();//esto se lo paso a los clainm

            //aqui no tiene que ir datos sensibles
            Claims claims = Jwts.claims()
                .add("authorities",new ObjectMapper().writeValueAsString(roles))//tambien convertimos a una estructura json serializamos
                .add("username", username)//solo de pureba
                .build();

                //para genrar el token
            String token = Jwts.builder()
            .subject(username)
            .claims(claims)
            .signWith(SECRET_KEY)
            .issuedAt(new Date())//fecha de creacion del token 
            .expiration(new Date(System.currentTimeMillis() + 3600000))//el token este tiempo va a valer la hora del sistema mas lo que le agregue
            .compact();

                //aqui devolvemos el token para que se vea en el cliente
            response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token); //los 2 atributos los estoy importanto de TokenJwtConfig
            //tambien lo pasamos como un json
            Map<String, String> body = new HashMap<>();
            body.put("token", token);
            body.put("username", username);
            body.put("message", String.format("Hola %s has iniciado secion con exito!", username));

            //ahora para mostrarlo convertimos el map en json
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setContentType(CONTENT_TYPE);//el argumetno viene de TokenJwtCongig
            response.setStatus(200);
    }

    //esto va a pasar cuando falla la autentificacion
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

                Map<String, String> body = new HashMap<>();
                body.put("message", "error en la autentificacion username o password son incorrectos");
                body.put("error", failed.getMessage());//devuelvo el error generico que le va a dar
                
                //convertimos de java a un json
                response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                response.setStatus(401);//que no esta autorizado
                response.setContentType(CONTENT_TYPE);//mostramos lo que tiene ese valor de contentType
    }
    
    //todo esto lo tenemos que registrar en securityConfig

}

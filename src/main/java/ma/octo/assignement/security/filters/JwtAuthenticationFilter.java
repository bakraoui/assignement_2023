package ma.octo.assignement.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.octo.assignement.dto.operationdto.TransferDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static ma.octo.assignement.security.utils.Constant.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        // read data from request
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // create an authentication token
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken( username, password );
        
        // manage the authentication process : check if user exist in DB...
        return authenticationManager.authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException {

        // if authentication success do this

        // spring security User
        // principle return Object contain the authenticated user details
        User user = (User) authResult.getPrincipal();

        // generate JWT
        Algorithm algorithm = Algorithm.HMAC256(KEY); // algo for signature, take a secret key

        String jwtAccessToken = JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME_ACCESS_TOKEN) ) // 1 jour
                .withIssuer(request.getRequestURI())
                .withClaim("roles",
                        user.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                //.map(authority -> authority.getAuthority())
                                .collect(Collectors.toList()))
                .sign(algorithm);

        // refresh token : to get new token when jwtAccessToken expired
        String jwtRefreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME_REFRESH_TOKEN) )
                .withIssuer(request.getRequestURI())
                .sign(algorithm);

        // response.setHeader("access_token",jwtAccessToken);
        // response.setHeader("refresh_token",jwtRefreshToken);

        Map<String, String> tokens = new HashMap<>();

        tokens.put("access_token", jwtAccessToken);
        tokens.put("refresh_token", jwtRefreshToken);

        response.setContentType("application/json");
        // ObjectMapper convert the map en JSON format
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);


    }
}

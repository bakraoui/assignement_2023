package ma.octo.assignement.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import ma.octo.assignement.security.utils.Constant;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class JwtAuthorisationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        // Bearer jwt
        if (authorization != null && authorization.startsWith(Constant.BEARER)) {

            try {
                //  get token
                String jwtToken = authorization.substring(Constant.BEARER.length());

                // verify, decode token
                Algorithm algorithm = Algorithm.HMAC256(Constant.KEY);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);

                // get data from token
                String username = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

                Collection<SimpleGrantedAuthority> authorities = Arrays.stream(roles)
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

                // authenticate
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                // pass to next filter
                filterChain.doFilter(request, response);

            }catch (Exception e) {
                response.setHeader("error-message", e.getMessage());
                response.setStatus(HttpServletResponse.SC_CONFLICT);

            }


        }else {
            filterChain.doFilter(request, response);
        }
    }
}

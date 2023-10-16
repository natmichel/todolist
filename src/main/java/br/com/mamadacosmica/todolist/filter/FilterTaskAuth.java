package br.com.mamadacosmica.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.mamadacosmica.todolist.user.User;
import br.com.mamadacosmica.todolist.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                String servletPath = request.getServletPath();

                if(servletPath.startsWith("/tasks/")){
                    //Pegar a autenticação
                    String authorization = request.getHeader("Authorization");

                    authorization = authorization.substring("Basic".length()).trim();

                    byte[] decode = Base64.getDecoder().decode(authorization);

                    authorization = new String(decode);

                    String[] credentials = authorization.split(":");

                    String username = credentials[0];
                    String senha = credentials[1];
                    
                    //Validar Usuário
                    User user = this.userRepository.findByUsername(username);
                    if(user == null){
                        response.sendError(401);
                    }else{
                        //Validar Senha
                        BCrypt.Result senhaVerificada = BCrypt.verifyer().verify(senha.toCharArray(), user.getSenha());
                        //Seguir
                        if(senhaVerificada.verified){
                            request.setAttribute("idUser", user.getId());
                            filterChain.doFilter(request, response);
                        }else{
                            response.sendError(401);
                        }
                    }
            }else{
                filterChain.doFilter(request, response);
            }
        }
}
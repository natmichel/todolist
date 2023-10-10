package br.com.mamadacosmica.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface UserRepository extends JpaRepository<User, UUID>{
    User findByUsername(String username);
}

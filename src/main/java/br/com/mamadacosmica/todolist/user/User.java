package br.com.mamadacosmica.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_users")
public class User {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String nome;
    private String username;
    private String senha;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

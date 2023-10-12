package br.com.mamadacosmica.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name= "tb_tasks")
public class Task {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    
    @Column(length = 50)
    private String titulo;
    private String descricao;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    
    @CreationTimestamp
    private LocalDateTime createdAt;

    private UUID idUser;
    private String prioridade;
}

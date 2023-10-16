package br.com.mamadacosmica.todolist.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface TaskRepository extends JpaRepository<Task, UUID>{
    List<Task> findByIdUser(UUID idUser);
}

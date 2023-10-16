package br.com.mamadacosmica.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mamadacosmica.todolist.util.Util;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    //Contrutor do TaskRepository
    @Autowired
    private TaskRepository taskRepository;

    //Método POST
    @PostMapping("/")
    public ResponseEntity create(@RequestBody Task task, HttpServletRequest request){
        task.setIdUser((UUID) request.getAttribute("idUser"));

//        LocalDateTime agora = LocalDateTime.now();
        if(LocalDateTime.now().isAfter(task.getStartAt()) || LocalDateTime.now().isAfter(task.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio/termino deve ser maior que a data atual");
        }
        if(task.getStartAt().isAfter(task.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio deve ser menor que a data de termino");
        }
        Task teste = this.taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.OK).body(teste);
    }
    
    //Método GET
    @GetMapping("/")
    public List<Task> list(HttpServletRequest request){
        List<Task> lista = this.taskRepository.findByIdUser((UUID) request.getAttribute("idUser"));
        return lista;
    }

    //Método update(PUT)
    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody Task task, HttpServletRequest request, @PathVariable UUID id){
        Task task2 = this.taskRepository.findById(id).orElse(null);

        if(task2 == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não encontrada.");
        }


        UUID idUser = (UUID) request.getAttribute("idUser");
       
        //Restringe usarios de alterar tarefas fora de seu id
        if(!task2.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não tem permissão para alterar essa tarefa.");
        }

        Util.copyNonNullProprieties(task, task2);

        return ResponseEntity.ok().body(this.taskRepository.save(task2));
    }
}

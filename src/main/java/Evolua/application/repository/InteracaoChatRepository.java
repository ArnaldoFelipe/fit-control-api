package Evolua.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Evolua.application.entities.InteracaoChat;
import Evolua.application.entities.Usuario;

public interface InteracaoChatRepository extends JpaRepository<InteracaoChat, Long>{
    
    List<InteracaoChat> findByUsuarioOrderByDataCriacaoDesc(Usuario usuario);

}

package com.helpdesk.repositories;

import com.helpdesk.domain.Chamado;
import com.helpdesk.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {
}

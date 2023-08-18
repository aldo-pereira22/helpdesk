package com.helpdesk.repositories;

import com.helpdesk.domain.Pessoa;
import com.helpdesk.domain.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {
}

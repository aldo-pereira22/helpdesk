package com.helpdesk.services;

import com.helpdesk.domain.Pessoa;
import com.helpdesk.domain.Tecnico;
import com.helpdesk.domain.dtos.TecnicoDTO;
import com.helpdesk.repositories.PessoaRepository;
import com.helpdesk.repositories.TecnicoRepository;
import com.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {
    @Autowired
    private TecnicoRepository repository;

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    public Tecnico findById(Integer id){
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow( () -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
    }

    public List<Tecnico> findAll() {
        return repository.findAll();
    }

    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null);
        objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        validaPorCpfeEmail(objDTO);
        Tecnico newObj = new Tecnico(objDTO);
        return repository.save(newObj);
    }

    public Tecnico update(Integer id, TecnicoDTO objDto) {
        objDto.setId(id);
        Tecnico oldObj = findById(id);
        validaPorCpfeEmail(objDto);
        oldObj = new Tecnico(objDto);
        return repository.save(oldObj);
    }

    public void delete(Integer id) {
        Tecnico obj = findById(id);
        if(obj.getChamados().size() > 0 ){
            throw new DataIntegrityViolationException("O Técnico possui Ordens de serviço e não pode ser Excluído!");
        }
        repository.deleteById(id);
    }

    private void validaPorCpfeEmail(TecnicoDTO objDTO) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());

        if(obj.isPresent() && obj.get().getId() != objDTO.getId()){
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
        }

        obj = pessoaRepository.findByEmail(objDTO.getEmail());

        if(obj.isPresent() && obj.get().getId() != objDTO.getId()){
            throw new DataIntegrityViolationException("E-mail já cadadstrado no sistema!");
        }
    }



}

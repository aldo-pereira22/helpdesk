package com.helpdesk.services;

import com.helpdesk.domain.Cliente;
import com.helpdesk.domain.Pessoa;
import com.helpdesk.domain.Tecnico;
import com.helpdesk.domain.dtos.ClienteDTO;
import com.helpdesk.domain.dtos.TecnicoDTO;
import com.helpdesk.repositories.ClienteRepository;
import com.helpdesk.repositories.PessoaRepository;
import com.helpdesk.repositories.TecnicoRepository;
import com.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    public Cliente findById(Integer id){
        Optional<Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow( () -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null);
        validaPorCpfEEmail(objDTO);
        Cliente newObj = new Cliente(objDTO);
        return clienteRepository.save(newObj);
    }

    public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
        objDTO.setId(id);
        Cliente oldObj = findById(id);

        if(!objDTO.getSenha().equals(oldObj.getSenha()))
            objDTO.setSenha(objDTO.getSenha());

        validaPorCpfEEmail(objDTO);
        oldObj = new Cliente(objDTO);
        return clienteRepository.save(oldObj);
    }
    public void delete(Integer id) {
        Cliente obj = findById(id);
        if(obj.getChamados().size() > 0 ){
            throw new DataIntegrityViolationException("O Técnico possui Ordens de serviço e não pode ser Excluído!");
        }
        clienteRepository.deleteById(id);
    }

    private void validaPorCpfEEmail(ClienteDTO objDTO) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }

        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
        }
    }

}

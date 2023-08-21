package com.helpdesk.resources;

import com.helpdesk.domain.Cliente;
import com.helpdesk.domain.Tecnico;
import com.helpdesk.domain.dtos.ClienteDTO;
import com.helpdesk.domain.dtos.TecnicoDTO;
import com.helpdesk.services.ClienteService;
import com.helpdesk.services.TecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResources {

    @Autowired
    ClienteService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id){
        Cliente obj = service.findById(id);
        return ResponseEntity.ok().body(new ClienteDTO(obj));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll(){
        List<Cliente> list = service.findAll();
        List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
        return  ResponseEntity.ok().body(listDTO);
    }
//
//    @PostMapping
//    public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO){
//        Tecnico newObj = service.create(objDTO);
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//                .buildAndExpand(newObj.getId()).toUri();
//        return ResponseEntity.created(uri).build();
//    }
//
//    @PutMapping(value = "/{id}")
//    public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id,@Valid @RequestBody TecnicoDTO objDto){
//        Tecnico obj = service.update(id, objDto);
//        return ResponseEntity.ok().body(new TecnicoDTO(obj));
//    }
//
//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<TecnicoDTO> delete(@PathVariable Integer id){
//        service.delete(id);
//        return ResponseEntity.noContent().build();
//    }
}
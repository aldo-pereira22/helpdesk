import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/models/cliente';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-cliente-delete',
  templateUrl: './cliente-delete.component.html',
  styleUrls: ['./cliente-delete.component.css']
})
export class ClienteDeleteComponent implements OnInit {

  cliente: Cliente = {
    id: '',
    nome: '',
    cpf: '',
    email: '',
    senha: '',
    perfis: [],
    dataCriacao: ''

  }



  constructor(
    private service: ClienteService,
    private toast: ToastrService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.cliente.id = this.route.snapshot.paramMap.get('id');
    this.findById();
    console.log(this.cliente)
  }


  findById() {
    this.service.findById(this.cliente.id).subscribe(resposta => {
      resposta.perfis = []
      this.cliente = resposta
      console.log(resposta.senha)
    })
  }

  delete() {
    this.service.delete(this.cliente.id).subscribe(() => {
      this.toast.success("Cliente Deletado com sucesso!", 'DELETE');
      this.router.navigate(['clientes'])
    }, ex => {
      console.log(ex)
      if (ex.error.errors) {
        ex.error.errors.forEach(element => {
          this.toast.error(element.message)
        });
      } else {
        this.toast.error(ex.error.message)
      }
    })
  }




}

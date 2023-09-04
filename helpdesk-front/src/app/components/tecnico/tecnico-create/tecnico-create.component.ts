import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Tecnico } from 'src/app/models/tecnico';
import { TecnicoService } from 'src/app/services/tecnico.service';

@Component({
  selector: 'app-tecnico-create',
  templateUrl: './tecnico-create.component.html',
  styleUrls: ['./tecnico-create.component.css']
})
export class TecnicoCreateComponent implements OnInit {

  tecnico: Tecnico = {
    id: '',
    nome: '',
    cpf: '',
    email: '',
    senha: '',
    perfis: [],
    dataCriacao: ''

  }
  nome: FormControl = new FormControl(null, Validators.minLength(3));
  cpf: FormControl = new FormControl(null, Validators.required);
  email: FormControl = new FormControl(null, Validators.email);
  senha: FormControl = new FormControl(null, Validators.minLength(3));


  constructor(private service: TecnicoService, private toast: ToastrService) { }

  ngOnInit(): void {
  }


  create() {
    this.service.create(this.tecnico).subscribe(() => {
      this.toast.success("Técnico cadastrado com sucesso!", 'Cadastro');
    }, ex => {
      console.log(ex)
    })
  }

  addPerfil(perfil: any): void {

    if (this.tecnico.perfis.includes(perfil)) {
      this.tecnico.perfis.splice(this.tecnico.perfis.indexOf(perfil), 1)
      console.log(this.tecnico.perfis)
    } else {
      this.tecnico.perfis.push(perfil);
      console.log(this.tecnico.perfis)

    }

  }
  validaCampos(): boolean {
    return this.nome.valid && this.cpf.valid
      &&
      this.email.valid && this.senha.valid;
  }
}

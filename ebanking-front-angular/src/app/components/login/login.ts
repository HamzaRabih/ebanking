import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {AuthService} from '../../services/authServices/auth-service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login implements OnInit{
  errorMessage: any;
  formLogin!: FormGroup;

  constructor(private fb: FormBuilder,
              private authService:AuthService,
              private router:Router) {
  }

  ngOnInit(): void {
    this.formLogin=this.fb.group({
      username:this.fb.control(""),
      password:this.fb.control(""),
    })
  }

  handleLogin() {
    let username=this.formLogin.value.username;
    let password=this.formLogin.value.password;
    this.authService.login(username,password).subscribe({
      next :data =>{
        console.log(data)
        this.authService.loadProfile(data);
        this.router.navigateByUrl("/admin")
      },error :err => {
        this.errorMessage = "Adresse email ou mot de passe invalide"
        console.log(err )
      }
      }
    )
  }
}

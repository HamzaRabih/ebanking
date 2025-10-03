import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {jwtDecode} from 'jwt-decode';
import {catchError, throwError} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiUrl+"auth/";

  isAutenticated:boolean=false;
  roles:any;
  username:any;
  accessToken!:string;

  constructor(private http:HttpClient) {
  }

  public login(username:string,password:string){
    let options= {
      headers:new HttpHeaders().set("Content-Type","application/x-www-form-urlencoded")
    }
    let params=new HttpParams()
      .set("username",username)
      .set("password",password)

    return this.http.post(this.apiUrl + "login", params, options).pipe(
      catchError((err) => {
        console.error('Erreur de connexion:', err);
        return throwError(() =>
          new Error('Échec de la connexion')
        );
      })
    );  }

  loadProfile(data: any) {
    this.accessToken=data['access-token'];
    this.isAutenticated=true
    let decodedJwt:any=jwtDecode(this.accessToken)
    this.username=decodedJwt.sub;
    this.roles=decodedJwt.scope;
  }

  public logout() {
    this.isAutenticated = false;
    this.accessToken = '';
    this.username = undefined;
    this.roles = undefined;
    localStorage.removeItem('accessToken'); // Supprimer le token
    localStorage.removeItem('refreshToken'); // Supprimer le refreshToken
  }

}

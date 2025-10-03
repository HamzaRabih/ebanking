import {HttpEvent, HttpHandler, HttpInterceptor, HttpInterceptorFn, HttpRequest} from '@angular/common/http';
import {Injectable, Injector} from '@angular/core';
import {AuthService} from '../services/authServices/auth-service';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';

@Injectable()
export class appHttpInterceptor  {

  constructor(private authService:AuthService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!request.url.includes("/auth/login")){
      let newRequest=request.clone({
        headers : request.headers.set('Authorization', 'Bearer '+this.authService.accessToken)
      })
      return next.handle(newRequest);
    }else {
      return next.handle(request);
    }
  }
}

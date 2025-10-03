import {
  CanActivate,
  Router,
} from '@angular/router';
import {AuthService} from '../services/authServices/auth-service';
import {Injectable} from '@angular/core';
@Injectable({
  providedIn: 'root'
})
export class authenticationGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    if (this.authService.isAutenticated) {
      return true;
    } else {
      // Redirigez l'utilisateur vers la page de connexion s'il n'est pas authentifié
      this.router.navigate(['login']);
      return false;
    }
  }

}

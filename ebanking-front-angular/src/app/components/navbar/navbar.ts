import {Component, OnInit} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {AuthService} from '../../services/authServices/auth-service';

@Component({
  selector: 'app-navbar',
  imports: [
    RouterLink
  ],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar implements OnInit{


  constructor(
    private router: Router,
    public authService: AuthService,
  ) {
  }

  ngOnInit(): void {
  }

  handleLogout() {
    this.authService.logout()
    this.router.navigateByUrl("/login")
  }
}

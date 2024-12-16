import { Component, inject, OnInit } from '@angular/core';
import { KeycloakService } from '../../../config/keycloak/keycloak.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit{

  isAuthenticated: boolean| undefined = false;
  username: string | undefined = "";
  
  constructor(private keycloakService: KeycloakService){}

  ngOnInit(): void {
    console.log("isAuthenticated: ", this.keycloakService.keycloak.authenticated)
    if (this.keycloakService.keycloak.authenticated) {
      this.username = this.keycloakService.profile?.username;
      this.isAuthenticated = this.keycloakService.keycloak.authenticated;
    }
  }

  login() {
    this.keycloakService.login()
  }

  logout() {
    console.log("isAuthenticated: ", this.keycloakService.keycloak.authenticated)
    this.keycloakService.logout()
  }

}

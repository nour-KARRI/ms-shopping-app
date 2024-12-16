import { Injectable } from '@angular/core';
import Keycloak from 'keycloak-js';
import { UserProfile } from './user.profile';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {
  
  private _keycloak: Keycloak | undefined
  private _profile: UserProfile | undefined

  get keycloak(){
    if (!this._keycloak) {
      this._keycloak = new Keycloak({
        url: 'http://localhost:8181',
        realm: 'spring-ms-security-realm',
        clientId: 'angular-client'
      })
    } 
    return this._keycloak
  }

  get profile(): UserProfile | undefined {
    return this._profile;
  }

  constructor() { }
  
  async init() {
    const authenticated = await this.keycloak?.init({
      onLoad: 'login-required'
    });

    if (authenticated){
      this._profile = (await this.keycloak?.loadUserProfile()) as UserProfile;
      this._profile.token = this.keycloak?.token;
    }
  }

  login(){
    return this.keycloak?.authenticated;
  }

  logout(){
    return this.keycloak?.logout({redirectUri: 'http://localhost:4200'})
  }
  
}

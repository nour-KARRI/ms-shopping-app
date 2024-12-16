import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { OrderService } from '../../services/order.service';
import { Router } from '@angular/router';
import { Product } from '../../model/product';
import { catchError, map, tap } from 'rxjs';
import { KeycloakService } from '../../../config/keycloak/keycloak.service';
import { OrderProduct, UserDetails } from '../../model/order-product';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent implements OnInit{

  isAuthenticated: boolean = false;
  products: Product[] = [];
  userDetails!: UserDetails;
  orderSuccess!: boolean
  orderFailed!: boolean

  constructor(private productService: ProductService,
              private orderService: OrderService,
              private router: Router,
              private keycloakService: KeycloakService){}

  ngOnInit(): void {
    if(this.keycloakService.keycloak.authenticated){
      this.productService.getProducts().pipe(
            map(products => this.products = products)
          ).subscribe();
    }    
  }

  goToCreateProductPage(){
    this.router.navigateByUrl("/add-product");
  }

  onSubmit(product: Product, form: NgForm) {

    this.userDetails = {
      email: this.keycloakService.profile?.email,
      firstName: this.keycloakService.profile?.firstName,
      lastName: this.keycloakService.profile?.lastName
    }

    const order: OrderProduct = {
      skuCode: product.skuCode,
      price: product.price,
      quantity: form.form.get('quantity')?.value,
      userDetails: this.userDetails
    }
    
    this.orderService.orderProduct(order).subscribe(
      ()=>{
          this.orderSuccess = true;
          this.orderFailed = false;
        }, (err: HttpErrorResponse)=> {
          this.orderSuccess = false;
          this.orderFailed = true;
          console.error(err);
        }
     )

   form.reset()
  
  }
}

import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ProductService } from '../../services/product.service';
import { tap } from 'rxjs';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrl: './add-product.component.css'
})
export class AddProductComponent implements OnInit{

  mainForm!: FormGroup

  skuCodeCtrl!: FormControl;
  nameCtrl!: FormControl
  descriptionCtrl!: FormControl
  priceCtrl!: FormControl
  loading: boolean = false
  productCreated: boolean =  false;

  constructor(private formBuilder: FormBuilder, 
              private productService: ProductService){}

  ngOnInit(): void {
    this.formInit();
    this.mainForm = this.formBuilder.group({
      skuCode: this.skuCodeCtrl,
      name: this.nameCtrl,
      description: this.descriptionCtrl,
      price: this.priceCtrl
    })
  }

  formInit(){
    this.skuCodeCtrl = this.formBuilder.control('', Validators.required)
    this.nameCtrl = this.formBuilder.control('', Validators.required)
    this.descriptionCtrl = this.formBuilder.control('', Validators.required)
    this.priceCtrl = this.formBuilder.control('', [Validators.required, Validators.min(0)])
  }

  getFormControlErrorText(ctrl: AbstractControl){
    if (ctrl.hasError('required')) {
      return 'This Field is required'
    }else if (ctrl.hasError('min')) {
      return 'Price must be greater then 0.'
    } else {
      return 'This field has error'
    }
  }


onAddProduct() {
  this.loading = true;
  this.productService.createProduct(this.mainForm.value).pipe(
    tap(saved =>{
      this.loading = false;
      if (saved) {
        this.productCreated = true;
        this.mainForm.reset()
      } else {
        console.error('Product creation failed')
      }
    })
  ).subscribe()
}

}

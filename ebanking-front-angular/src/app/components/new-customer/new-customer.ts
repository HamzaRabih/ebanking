import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {CustomersService} from '../../services/customers-service';
import {Customers} from '../customers/customers';
import {Customer} from '../../models/Customer.model';
import {NgIf} from '@angular/common';
import {Router} from '@angular/router';

@Component({
  selector: 'app-new-customer',
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './new-customer.html',
  styleUrl: './new-customer.css'
})
export class NewCustomer implements OnInit{

  newCustomerformGroup!: FormGroup ;


  constructor(private fb:FormBuilder,private customerService:CustomersService,private router:Router) {
  }

  ngOnInit(): void {
    this.newCustomerformGroup=this.fb.group({
        name:this.fb.control(null,[Validators.required,Validators.minLength(4)]),
        email:this.fb.control(null,[Validators.email,Validators.required]),
      }
    )
  }

  handeleSaveCustomer() {
    let customer:Customer=this.newCustomerformGroup.value;
    this.customerService.saveCustomer(customer).subscribe({
          next: data=>{
            alert("Customer has been successfully saved!!")
            //this.newCustomerformGroup.reset();
            this.router.navigateByUrl("/customers")
          },error :err => {
            console.log(err)
          }
        }
      )
  }
}

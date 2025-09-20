import {Component, OnInit} from '@angular/core';
import {CustomersService} from '../../services/customersServices/customers-service';
import {AsyncPipe, JsonPipe, NgIf} from '@angular/common';
import {catchError, map, Observable, throwError} from 'rxjs';
import {Customer} from '../../models/Customer.model';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
  selector: 'app-customers',
  imports: [
    JsonPipe,
    AsyncPipe,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './customers.html',
  styleUrl: './customers.css',
  standalone: true
})
export class Customers implements OnInit {

  customers$ !: Observable<Array<Customer>>;
  searchFormGroup: FormGroup | undefined;
  errMessage!: String;

  constructor(private customerService: CustomersService,
              private fb: FormBuilder,
              private router:Router) {
  }

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control("")
    })

    this.customers$ = this.customerService.getCustomers().pipe(
      catchError(err => {
        this.errMessage = err.message
        return throwError(err)
      })
    )
  }


  handelSearchCustomers() {
    let kw=this.searchFormGroup?.value.keyword;
    this.customers$ = this.customerService.searchCustomers(kw).pipe(
      catchError(err => {
        this.errMessage = err.message
        return throwError(err)
      })
    )

  }

  handelDeleteCustomer(c: Customer) {
    let conf=confirm("Are you sure ?")
    if(!conf) return;
      this.customerService.deleteCustomer(c.id).subscribe({
        next :(resp)=>{
          this.customers$=this.customers$.pipe(
            map(data=>{
              let index=data.indexOf(c)
              data.slice(index,1)
              return data
            })
          )
        },
        error :err => {
          console.log(err)
        }
      })

  }


  handelCustomerAccounts(c: Customer) {
    this.router.navigateByUrl("/customer-accounts/"+c.id,{state :c})
  }
}

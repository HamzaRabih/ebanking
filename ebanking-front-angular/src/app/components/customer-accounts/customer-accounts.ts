import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Customer} from '../../models/Customer.model';
import {JsonPipe} from '@angular/common';

@Component({
  selector: 'app-customer-accounts',
  imports: [
    JsonPipe
  ],
  templateUrl: './customer-accounts.html',
  standalone: true,
  styleUrl: './customer-accounts.css'
})
export class CustomerAccounts implements OnInit{

  customerId!:string;
  customer: any;
  constructor(private route :ActivatedRoute,private router :Router) {
    this.customer=this.router.getCurrentNavigation()?.extras.state as Customer;
  }

  ngOnInit(): void {
    this.customerId=this.route.snapshot.params['id']
  }
}

import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../environments/environment';
import {Observable} from 'rxjs';
import {Customer} from '../models/Customer.model';

@Injectable({
  providedIn: 'root'
})
export class CustomersService {

  private apiUrl = environment.apiUrl+"customers";

  constructor(private http:HttpClient) {
  }

  getCustomers():Observable<Array<Customer>>{
    return this.http.get<Array<Customer>>(this.apiUrl)
  }

  searchCustomers(keyword: String):Observable<Array<Customer>>{
    return this.http.get<Array<Customer>>(this.apiUrl+"/search?keyword="+keyword)
  }

  saveCustomer(customer: Customer):Observable<Customer>{
    return this.http.post<Customer>(this.apiUrl,customer)
  }

  deleteCustomer(id: number){
    return this.http.delete<Customer>(this.apiUrl+"/"+id)
  }

}

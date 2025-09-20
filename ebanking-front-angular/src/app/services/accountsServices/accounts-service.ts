import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Customer} from '../../models/Customer.model';
import {Account} from '../../models/Account.model';
import {AccountDetails} from '../../models/AccountDetails.model';

@Injectable({
  providedIn: 'root'
})
export class AccountsService {
  private apiUrl = environment.apiUrl+"accounts/";

  constructor(private http:HttpClient) {
  }

  getAccounts():Observable<Array<Account>>{
    return this.http.get<Array<Account>>(this.apiUrl)
  }

  getAccount(accountId: string,page: number,size: number):Observable<AccountDetails>{
    return this.http.get<AccountDetails>(this.apiUrl+accountId+"/pageOperations?page="+page+"&size"+size)
  }

  public debit(accountId:string,amount:number,description:string){
    let data={accountId:accountId,amount:amount,description:description}
    return this.http.post(this.apiUrl+"debit",data)
  }

  public credit(accountId:string,amount:number,description:string){
    let data={accountId:accountId,amount:amount,description:description}
    return  this.http.post(this.apiUrl+"credit",data)
  }

  public transfer(accountSource:string,accountDestination:string, amount:number,description:string){
    let data={accountSource ,accountDestination ,amount,description}
    return this.http.post(this.apiUrl+"transfer",data)
  }





}

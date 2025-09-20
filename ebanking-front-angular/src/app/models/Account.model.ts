import {Customer} from './Customer.model';

export interface Account{
  id:string;
  balance:number;
  createdAt:Date;
  status:string
  customer:Customer;
  type:string;
  interestRate:string;
  overdraft:string;
}

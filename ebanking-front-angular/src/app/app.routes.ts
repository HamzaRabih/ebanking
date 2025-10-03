import {Routes} from '@angular/router';
import {Customers} from './components/customers/customers';
import {Accounts} from './components/accounts/accounts';
import {NewCustomer} from './components/new-customer/new-customer';
import {CustomerAccounts} from './components/customer-accounts/customer-accounts';
import {Login} from './components/login/login';
import {AdminTemplate} from './components/admin-template/admin-template';
import {authenticationGuard} from './guards/authentication-guard';

export const routes: Routes = [
  {path: "",redirectTo:"/login", pathMatch:"full"},
  {path: "login", component: Login},
  {path: "admin", component: AdminTemplate , canActivate :[authenticationGuard] ,
    children :[
      {path: "customers", component: Customers},
      {path: "accounts", component: Accounts},
      {path: "new-customers", component: NewCustomer},
      {path: "customer-accounts/:id", component: CustomerAccounts},
    ]
  },
];

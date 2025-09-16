import {Routes} from '@angular/router';
import {Customers} from './components/customers/customers';
import {Accounts} from './components/accounts/accounts';
import {NewCustomer} from './components/new-customer/new-customer';

export const routes: Routes = [
  {path: "customers", component: Customers},
  {path: "accounts", component: Accounts},
  {path: "new-customers", component: NewCustomer}
];

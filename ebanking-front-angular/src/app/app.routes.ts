import {Routes} from '@angular/router';
import {Customers} from './components/customers/customers';
import {Accounts} from './components/accounts/accounts';
import {NewCustomer} from './components/new-customer/new-customer';
import {CustomerAccounts} from './components/customer-accounts/customer-accounts';

export const routes: Routes = [
  {path: "customers", component: Customers},
  {path: "accounts", component: Accounts},
  {path: "new-customers", component: NewCustomer},
  {path: "customer-accounts/:id", component: CustomerAccounts}
];

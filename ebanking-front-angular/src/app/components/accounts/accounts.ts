import {Component, OnInit} from '@angular/core';
import {AsyncPipe, DatePipe, DecimalPipe, JsonPipe, NgClass, NgForOf, NgIf} from "@angular/common";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {catchError, Observable, throwError} from 'rxjs';
import {Account} from '../../models/Account.model';
import {AccountsService} from '../../services/accountsServices/accounts-service';
import {AccountDetails} from '../../models/AccountDetails.model';

@Component({
  selector: 'app-accounts',
  imports: [
    AsyncPipe,
    FormsModule,
    NgIf,
    ReactiveFormsModule,
    JsonPipe,
    DecimalPipe,
    DatePipe,
    NgForOf,
    NgClass
  ],
  templateUrl: './accounts.html',
  standalone: true,
  styleUrl: './accounts.css'
})
export class Accounts implements OnInit {
  accountFormGroup!: FormGroup;
  operationFormGroup!: FormGroup;
  accounts$!: Observable<Array<Account>>;
  errMessage: string='';
  currentPage: number = 0;
  pageSize: number = 5;
  accountObservable$ !: Observable<AccountDetails>;
  constructor(private accounService: AccountsService, private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.accountFormGroup = this.fb.group(({
      accountId: this.fb.control('')
    }));

    this.accounts$ = this.accounService.getAccounts().pipe(
      catchError(err => {
        this.errMessage = err.message;
        return throwError(err)
      })
    )

    this.operationFormGroup = this.fb.group({
        operationType: this.fb.control(null),
        amount: this.fb.control(0),
        description: this.fb.control(null),
        accountDestination: this.fb.control(null)
      }
    )
  }

  handelSearchAccounts() {
    let accountId: string = this.accountFormGroup.value.accountId;
    this.accountObservable$ = this.accounService.getAccount(accountId, this.currentPage, this.pageSize).pipe(
      catchError(err => {
        this.errMessage= err.message;
        return throwError(err)
      })
    );
  }


  protected readonly Array = Array;

  goToPage(page: number) {
    this.currentPage = page
    this.handelSearchAccounts()
  }


  handelOperation() {
    let accountId: string = this.accountFormGroup.value.accountId;
    let operationType = this.operationFormGroup.value.operationType;
    let amount: number = this.operationFormGroup.value.amount
    let description: string = this.operationFormGroup.value.description
    let accountDestination: string = this.operationFormGroup.value.accountDestination


    if (operationType == 'DEBIT') {
      this.accounService.debit(accountId, amount, description).subscribe({
          next: (data) => {
            alert("Success Debit");
            this.handelSearchAccounts();
            this.operationFormGroup.reset()
          }, error: (err) => {
            console.log(err);
          }
        }
      )
    } else if (operationType == 'CREDIT') {
      this.accounService.credit(accountId, amount, description).subscribe({
          next: (data) => {
            alert("Success Credit");
            this.handelSearchAccounts();
            this.operationFormGroup.reset()
          }, error: (err) => {
            console.log(err);
          }
        }
      )
    } else if (operationType == 'TRANSFER') {
      this.accounService.transfer(accountId,accountDestination, amount, description).subscribe({
          next: (data) => {
            alert("Success Transfer");
            this.handelSearchAccounts();
            this.operationFormGroup.reset()
          }, error: (err) => {
            console.log(err);
          }
        }
      )
    }
  }

}

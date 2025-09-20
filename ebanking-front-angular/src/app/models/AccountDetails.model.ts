import {AccountOperation} from './AccountOperation';

export interface AccountDetails {
  accountId:            string;
  balance:              number;
  currentPage:          number;
  totalePages:          number;
  pageSize:             number;
  accountOperationDTOS: AccountOperation[];
}

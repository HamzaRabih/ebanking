package ma.enset.iibdcc.ebankingbackend.dtos;

import lombok.Data;

import java.util.List;
@Data
public class AccountHistoryDTO {
    String accountId;
    private double balance;
    private int currentPage;
    private int totalePages;
    private int pageSize;
    private List<AccountOperationDTO> accountOperationDTOS;


}

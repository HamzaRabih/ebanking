package ma.enset.iibdcc.ebankingbackend.dtos;


import lombok.Data;
import ma.enset.iibdcc.ebankingbackend.enums.OperationType;

import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id;
    private Date oprationDate;
    private double amount;
    private OperationType type;
    private String description;
}

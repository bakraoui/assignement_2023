package ma.octo.assignement.dto.operationDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class OperationDto  implements Serializable {

    private String nrCompteBeneficiaire;
    private String motif;
    private BigDecimal montant;
    private Date date;

}

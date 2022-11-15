package ma.octo.assignement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class OperationDto {
    private String nrCompteBeneficiaire;
    private String motif;
    private BigDecimal montant;
    private Date date;
}

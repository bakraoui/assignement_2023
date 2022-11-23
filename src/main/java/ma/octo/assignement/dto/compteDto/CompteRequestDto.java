package ma.octo.assignement.dto.compteDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompteRequestDto implements Serializable {

    private String numeroCompte;
    private String rib;
    private BigDecimal solde;
    private String utilisateurUsername;

}
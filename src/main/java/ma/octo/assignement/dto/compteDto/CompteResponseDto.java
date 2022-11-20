package ma.octo.assignement.dto.compteDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteResponseDto {
    private String nrCompte;
    private String rib;
    private BigDecimal solde;
    private String utilisateurUsername;
}

package ma.octo.assignement.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.octo.assignement.domain.Utilisateur;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteDto {

    private String nrCompte;
    private String rib;
    private BigDecimal solde;
    private String utilisateurUsername;


}

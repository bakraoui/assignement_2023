package ma.octo.assignement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepositDto extends OperationDto {

    private String nom_prenom_emetteur;
}

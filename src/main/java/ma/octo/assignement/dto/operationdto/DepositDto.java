package ma.octo.assignement.dto.operationdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DepositDto extends OperationDto {

    private String nomPrenomEmetteur;
    private String rib;

}

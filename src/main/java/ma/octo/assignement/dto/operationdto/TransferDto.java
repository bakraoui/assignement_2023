package ma.octo.assignement.dto.operationdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransferDto extends OperationDto {
  private String nrCompteEmetteur;
}

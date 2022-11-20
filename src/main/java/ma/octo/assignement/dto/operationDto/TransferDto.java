package ma.octo.assignement.dto.operationDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransferDto extends OperationDto {
  private String nrCompteEmetteur;
}

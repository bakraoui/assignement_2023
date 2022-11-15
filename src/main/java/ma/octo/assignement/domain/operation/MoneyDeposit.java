package ma.octo.assignement.domain.operation;

import lombok.*;
import ma.octo.assignement.domain.Compte;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Table(name = "DEP")
@Builder
public class MoneyDeposit extends Operation{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;


}

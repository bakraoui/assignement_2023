package ma.octo.assignement.domain.operation;

import lombok.*;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "DEPOSIT")
public class MoneyDeposit extends Operation{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

}

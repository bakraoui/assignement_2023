package ma.octo.assignement.domain.operation;

import lombok.*;

import javax.persistence.*;


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

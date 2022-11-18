package ma.octo.assignement.domain.operation;

import lombok.*;
import ma.octo.assignement.domain.Compte;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
@Table(name = "TRANSFER")
public class Transfer extends Operation{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  private Compte compteEmetteur;


}

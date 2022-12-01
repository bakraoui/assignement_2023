package ma.octo.assignement.dto.utilisateurdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilisateurRequestDto implements Serializable {

    private String username;
    private String password;
    private String gender;
    private String lastname;
    private String firstname;
    private Date birthdate;

}

package ma.octo.assignement.dto.utilisateurDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilisateurResponseDto {

    private String username;
    private String gender;
    private String lastname;
    private String firstname;
    private Date birthdate;

}

package ma.octo.assignement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDto {

    private String username;
    private String gender;
    private String lastname;
    private String firstname;
    private Date birthdate;
}

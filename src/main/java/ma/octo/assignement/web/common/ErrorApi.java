package ma.octo.assignement.web.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorApi {

    private String message;
    private Date timestampe = new Date();
    private HttpStatus status;
    private String path;
}

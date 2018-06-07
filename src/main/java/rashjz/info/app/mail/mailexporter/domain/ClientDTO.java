package rashjz.info.app.mail.mailexporter.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientDTO {
    private static final long UID = 12L;
    private  String name;
    private  String emailAddress;
    private  String purchasedPackage;
}

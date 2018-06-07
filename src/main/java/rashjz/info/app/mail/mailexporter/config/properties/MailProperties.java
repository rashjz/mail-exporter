package rashjz.info.app.mail.mailexporter.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;


@Data
public class MailProperties {

    private String url;
    private String host;
    private int port;
    private String username;
    private String password;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttls;

    @Value("${spring.mail.properties.mail.transport.protocol}")
    private String protocol;

    @Value("${spring.mail.properties.mail.debug}")
    private boolean debug;
}

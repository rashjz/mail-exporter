package rashjz.info.app.mail.mailexporter.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import rashjz.info.app.mail.mailexporter.domain.ClientDTO;

@Slf4j
public class StudentProcessor implements ItemProcessor<ClientDTO, ClientDTO> {
    JavaMailSender javaMailSender;

    @Autowired
    public StudentProcessor(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public ClientDTO process(ClientDTO item) throws Exception {
        log.info(item.toString() + " processing data ");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(item.toString());
        message.setTo("mail@gmail.com" );
        message.setFrom(item.getEmailAddress() );
        javaMailSender.send(message);

        return item;
    }


}

package rashjz.info.app.mail.mailexporter.processor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import rashjz.info.app.mail.mailexporter.domain.ClientDTO;

import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class StudentProcessorTest {
    @Mock
    JavaMailSender javaMailSender;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private StudentProcessor studentProcessor;

    @Before
    public void init() {
        studentProcessor = new StudentProcessor(javaMailSender);
    }


    @Test
    public void processCheckExpectedValuesTest() throws Exception {
        ClientDTO expectedDTO = studentProcessor.process(mockClientDTO());
        Assert.assertEquals(expectedDTO, mockClientDTO());
    }

    @Test
    public void processVerifyMailCallTest() throws Exception {
        studentProcessor.process(mockClientDTO());
        verify(javaMailSender, atLeast(1)).send(mockSimpleMailMessage());
    }

    @Test
    public void processErrorTest() throws Exception {
        doThrow(new NullPointerException()).when(javaMailSender).send(mockSimpleMailMessage());
        thrown.expect(NullPointerException.class);
        studentProcessor.process(mockClientDTO());
    }

    private static SimpleMailMessage mockSimpleMailMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(mockClientDTO().getName());
        message.setTo("mail@gmail.com");
        message.setFrom(mockClientDTO().getEmailAddress());
        return message;
    }

    private static ClientDTO mockClientDTO() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setEmailAddress("rashad");
        clientDTO.setName("rashad");
        clientDTO.setPurchasedPackage("rashad");
        return clientDTO;
    }
}

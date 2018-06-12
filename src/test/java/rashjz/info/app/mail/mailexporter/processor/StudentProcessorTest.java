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

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class StudentProcessorTest {

    private static final String TEST_DATA = "test_data";
    private static final String EMAIL_TO = "mail@gmail.com";

    @Mock
    private JavaMailSender javaMailSender;

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
        message.setTo(EMAIL_TO);
        message.setFrom(mockClientDTO().getEmailAddress());
        return message;
    }

    private static ClientDTO mockClientDTO() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setEmailAddress(TEST_DATA);
        clientDTO.setName(TEST_DATA);
        clientDTO.setPurchasedPackage(TEST_DATA);
        return clientDTO;
    }
}

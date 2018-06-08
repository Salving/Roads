package salving.roads.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.xml.sax.InputSource;
import salving.roads.domain.ProblemPoint;
import salving.roads.repository.NotesRepository;
import salving.roads.repository.ProblemPointRepository;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class MailService {

    @Autowired
    ProblemPointRepository pointRepository;

    @Autowired
    NotesRepository notesRepository;

    private Configuration cfg;

    public MailService() {
        cfg = new Configuration();

        ClassLoader cl = getClass().getClassLoader();

        cfg.setClassForTemplateLoading(cl.getClass(), "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public void send(String text, String to) {
        String from = "r3com.service@yandex.ru";
        String host = "smtp.yandex.ru";

        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.user", "r3com.service");
        properties.setProperty("mail.smtp.ssl.enable", "true");
        properties.setProperty("mail.smtp.starttls.enable", "false");
        properties.setProperty("mail.smtp.auth", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String username = "r3com.service";
                String password = "Ntr89oP";
                return new PasswordAuthentication(username, password);
            }
        };
        Session session = Session.getDefaultInstance(properties, auth);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("Complaint");

            message.setText(text, "utf-8", "html");

            Transport.send(message);
            System.out.println("Sent message successfully");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

    public String buildMessage(long id) {
        if (pointRepository.existsById(id)) {
            ProblemPoint point = pointRepository.findById(id);

            Map<String, Object> root = new HashMap<>();
            root.put("longitude", String.valueOf(point.getLongitude()));
            root.put("latitude", String.valueOf(point.getLatitude()));
            root.put("notes", notesRepository.findNotesByPointId(id));

            Writer writer = new StringWriter();

            try {
                Template temp = cfg.getTemplate("Mail.ftlh");
                temp.process(root, writer);
            } catch (IOException | TemplateException e) {
                e.printStackTrace();
            }

            return writer.toString();
        } else {
            return "Point not found";
        }
    }
}

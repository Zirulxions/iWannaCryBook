package utility;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
	public void sendBEmail( String email) throws AddressException, MessagingException {
	Properties props = new Properties();

	// host 
	props.setProperty("mail.smtp.host", "smtp.gmail.com");

	// TLS 
	props.setProperty("mail.smtp.starttls.enable", "true");

	// Puerto
	props.setProperty("mail.smtp.port","587");

	//  usuario
	props.setProperty("mail.smtp.user", "26053780r@gmail.com");

	// Si requiere o no usuario y password para conectarse.
	props.setProperty("mail.smtp.auth", "true");

	Session session = Session.getDefaultInstance(props);

	// Para obtener un log de salida más extenso
	session.setDebug(true);
	
	
	
	
	MimeMessage message = new MimeMessage(session);

	// Quien envia 
	message.setFrom(new InternetAddress("26053780r@gmail.com"));

	// A quien va dirigido
	message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

	message.setSubject("iWannaCryBook");
	message.setText("Your account have been banned");
	
	
	
	
	Transport t = session.getTransport("smtp");

	// Aqui usuario y password de gmail
	t.connect("26053780r@gmail.com","password");
	t.sendMessage(message,message.getAllRecipients());
	t.close();
	}
}
	

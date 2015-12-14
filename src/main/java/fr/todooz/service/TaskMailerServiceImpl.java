package fr.todooz.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import fr.todooz.domain.Task;

@Service
public class TaskMailerServiceImpl implements TaskMailerService {
	
	// Recipient's email ID needs to be mentioned.
	private final static String to 			= "bach.phanluong@gmail.com";
	
	// Sender's email ID needs to be mentioned
	private final static String from 		= "bach.phanluong@centraliens-marseille.fr";

	private final static String username 	= "bach.phanluong@centraliens-marseille.fr";// change accordingly
	private final static String password 	= "xxxx"; // change accordingly
	
	// SMTP Server host
	private final static String host 		= "mail.centraliens-marseille.fr";
	
	// SMTP Server port
	private final static String port 		= "587";
	
	@Async
	@Override
	public void sendEmailWithTaskInfo(final Task task, final String changeState) {
	
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		//Secured connection using SSL
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", host);

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			StringBuilder sbSubject = new StringBuilder("Some change in the Todooz' list: A task has been ");
			StringBuilder sbText = new StringBuilder("Hello ")
										.append(username)
										.append(",\n \n")
										.append("The following task has been ");
			
			switch(changeState){
			case "add":
				sbSubject.append("added!");
				sbText.append("added! \n");
				break;
			case "modify":
				sbSubject.append("modifyied!");
				sbText.append("modifyied! \n");
				break;
			case "delete":
				sbSubject.append("deleted!");
				sbText.append("deleted! \n");
				break;
			default:
				break;			
			}
			
			message.setSubject(sbSubject.toString());
			
			if ( task.getId() != null && !"".equals(task.getId())){
				sbText.append("Id : ")
					.append(task.getId())
					.append(", \n");
			}
			
			sbText.append("Modification date : ")
					.append(new Date())
					.append(", \n");
			
			sbText.append("Title : ")
					.append(task.getTitle())
					.append(", \n");
			
			sbText.append("Deadline : ")
					.append(task.getDate())
					.append(", \n");
			
			sbText.append("Tags : ")
					.append(task.getTags())
					.append(", \n");
			
			sbText.append("Content : ")
					.append(task.getText())
					.append(".\n");

			// Now set the actual message
			message.setText(sbText.toString());

			// Send message
			System.out.println("Sending message !");

			Transport.send(message);

			System.out.println("Sent message successfully....");
			
			

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

}

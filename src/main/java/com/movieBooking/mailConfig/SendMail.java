package com.movieBooking.mailConfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class SendMail {
	
	
	 @Autowired
	 private JavaMailSender mailSender;
	

	
	@KafkaListener(topics = "booking", groupId = "booking-group")
    public void listenBooking(String message) {
        
        String[] emailInfo=message.split(":");
        System.out.println("Received Message: " + emailInfo[0]);
   
        String body="Your Booking for "+emailInfo[1]+" at thater "+emailInfo[2]+" is confirmed \n"+"Your seats "+emailInfo[4]+"\nTotal Price "+emailInfo[5];
        
        sendEmail(emailInfo[0],body);   //send mail to this address
    }
	
	@KafkaListener(topics = "cancelbooking", groupId = "booking-group")
    public void listenCancelBooking(String message) {
        
        String[] emailInfo=message.split(":");
   
        String body="Your Booking for "+emailInfo[1]+" at thater "+emailInfo[2]+" is cancelled \n"+"Your seats "+emailInfo[4]+"\nTotal Price "+emailInfo[5];
        
        sendEmail(emailInfo[0],body);   //send mail to this address
    }
	
	
	public void sendEmail(String toEmail,String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("karan.more33345@gmail.com"); // Must match your verified sender email
            message.setTo(toEmail);
            message.setSubject("Confirmation mail");
            message.setText(body);

            mailSender.send(message);

            
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
	

}

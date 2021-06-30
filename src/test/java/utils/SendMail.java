package utils;

import io.github.cdimascio.dotenv.Dotenv;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Arrays;
import java.util.Properties;

public class SendMail {
    static Dotenv dotenv = Dotenv.load();
    static <T> T[] append(T[] arr, T element) {
        final int N = arr.length;
        arr = Arrays.copyOf(arr, N + 1);
        arr[N] = element;
        return arr;
    }

    public static String[] getAddress(String address) throws AddressException {
        String[] addressList = new String[0];
        if (address.isEmpty()) return addressList;
        if (address.indexOf(";") > 0) {
            String[] addresses = address.split(";");
            for (String a : addresses) {
                InternetAddress.parse("addresses");
                addressList = append(addressList, a);
            }
        } else {
            addressList = append(addressList, address);
        }
        return addressList;
    }

    public static void ComposeGmail(String fromMail, String toMail) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(props);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromMail));
            for (String emailRecipient : getAddress(toMail)) {
                Address toAddress = new InternetAddress(emailRecipient);
                message.addRecipient(Message.RecipientType.TO, toAddress);
            }
            message.setSubject("BioSmart Automated Test Result");
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Hello," + "\n" + "Attached to this mail is the report of an automated test on BioSmart Glo Android.");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            String file = System.getProperty("user.dir") + "/DroidReport.html";
            String fileName = "BioSmart-Android.html";
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);
            System.out.println("Sending...");
            message.setContent(multipart);
            Transport.send(message, "seamfix.test.report@gmail.com", dotenv.get("MAIL_PASSWORD"));
            System.out.println("Sent.");
        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
    }
}


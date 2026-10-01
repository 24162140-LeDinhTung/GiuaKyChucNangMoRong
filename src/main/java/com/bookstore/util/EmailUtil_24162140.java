package com.bookstore.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.InputStream;
import java.util.Properties;

/**
 * Utility gửi email OTP qua Gmail SMTP
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public class EmailUtil_24162140 {

    private static final Properties CONFIG = new Properties();

    static {
        try (InputStream is = EmailUtil_24162140.class
                .getClassLoader()
                .getResourceAsStream("email-config.properties")) {
            if (is != null) {
                CONFIG.load(is);
            } else {
                System.err.println("Không tìm thấy email-config.properties");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gửi mail OTP
     * @param toEmail email người nhận
     * @param otp mã OTP 6 số
     */
    public static void sendOtp(String toEmail, String otp) {

        String host     = CONFIG.getProperty("mail.smtp.host");
        String port     = CONFIG.getProperty("mail.smtp.port");
        String username = CONFIG.getProperty("mail.username");
        String password = CONFIG.getProperty("mail.password");
        String fromName = CONFIG.getProperty("mail.from");

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.writetimeout", "10000");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username, fromName));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("BookStore - Mã xác nhận OTP", "UTF-8");

            String html = """
                <div style="font-family:Arial,sans-serif;max-width:500px;margin:auto;
                            padding:24px;border:1px solid #e5e7eb;border-radius:10px;">
                    <h2 style="color:#2563eb;">📚 BookStore</h2>
                    <p>Xin chào,</p>
                    <p>Mã OTP để kích hoạt tài khoản của bạn là:</p>
                    <div style="font-size:32px;font-weight:bold;color:#2563eb;
                                letter-spacing:8px;text-align:center;padding:16px;
                                background:#eff6ff;border-radius:8px;">
                        %s
                    </div>
                    <p style="color:#6b7280;font-size:13px;margin-top:20px;">
                        Mã có hiệu lực trong 5 phút. Không chia sẻ mã này với bất kỳ ai.
                    </p>
                    <hr style="border:none;border-top:1px solid #e5e7eb;margin:20px 0;">
                    <p style="color:#9ca3af;font-size:12px;text-align:center;">
                        Lê Đình Tùng - 24162140 - Đề 01
                    </p>
                </div>
                """.formatted(otp);

            message.setContent(html, "text/html; charset=UTF-8");

            Transport.send(message);
            System.out.println("==> Đã gửi OTP đến: " + toEmail);

        } catch (Exception e) {
            System.err.println("==> Gửi mail thất bại: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
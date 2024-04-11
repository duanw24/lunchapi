package com.wduan.lunchlinebackend.helpers;

import com.wduan.lunchlinebackend.LogController;
import com.wduan.lunchlinebackend.util.Order;
import com.wduan.lunchlinebackend.util.Utils;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;


@Service
public class emailHelper {

    @Getter
    private static emailHelper instance = new emailHelper();

    @SneakyThrows
    public static void main(String[] args) {
       //init();
       instance.sendEmail("warrenduan@gmail.com", "Test", html2);
    }

    @Autowired
    private JavaMailSender mailSender = getJavaMailSender();

    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("wduan@uaschools.org");
        mailSender.setPassword("dkwr iifw ulrq awgm");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @SneakyThrows
    public void sendEmail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("wduan@uaschools.org", "BearBites-NoReply"));
        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.addRecipients(MimeMessage.RecipientType.CC, "wduan@uaschools.org");
        message.addRecipients(MimeMessage.RecipientType.CC, "warrenduan@gmail.com");
        message.setSubject(subject);

        message.setContent(content, "text/html; charset=utf-8");
        mailSender.send(message);
    }

    @SneakyThrows
    public void sendConfirmation(String toEmail, Order order) {
     String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta http-equiv=\"x-ua-compatible\" content=\"ie=edge\">\n" +
                "    <title>Email Confirmation</title>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <style type=\"text/css\">\n" +
                "        /**\n" +
                "         * Google webfonts. Recommended to include the .woff version for cross-client compatibility.\n" +
                "         */\n" +
                "        @media screen {\n" +
                "            @font-face {\n" +
                "                font-family: 'Source Sans Pro';\n" +
                "                font-style: normal;\n" +
                "                font-weight: 400;\n" +
                "                src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff');\n" +
                "            }\n" +
                "            @font-face {\n" +
                "                font-family: 'Source Sans Pro';\n" +
                "                font-style: normal;\n" +
                "                font-weight: 700;\n" +
                "                src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff');\n" +
                "            }\n" +
                "        }\n" +
                "        /**\n" +
                "         * Avoid browser level font resizing.\n" +
                "         * 1. Windows Mobile\n" +
                "         * 2. iOS / OSX\n" +
                "         */\n" +
                "        body,\n" +
                "        table,\n" +
                "        td,\n" +
                "        a {\n" +
                "            -ms-text-size-adjust: 100%; /* 1 */\n" +
                "            -webkit-text-size-adjust: 100%; /* 2 */\n" +
                "        }\n" +
                "        /**\n" +
                "         * Remove extra space added to tables and cells in Outlook.\n" +
                "         */\n" +
                "        table,\n" +
                "        td {\n" +
                "            mso-table-rspace: 0pt;\n" +
                "            mso-table-lspace: 0pt;\n" +
                "        }\n" +
                "        /**\n" +
                "         * Better fluid images in Internet Explorer.\n" +
                "         */\n" +
                "        img {\n" +
                "            -ms-interpolation-mode: bicubic;\n" +
                "        }\n" +
                "        /**\n" +
                "         * Remove blue links for iOS devices.\n" +
                "         */\n" +
                "        a[x-apple-data-detectors] {\n" +
                "            font-family: inherit !important;\n" +
                "            font-size: inherit !important;\n" +
                "            font-weight: inherit !important;\n" +
                "            line-height: inherit !important;\n" +
                "            color: inherit !important;\n" +
                "            text-decoration: none !important;\n" +
                "        }\n" +
                "        /**\n" +
                "         * Fix centering issues in Android 4.4.\n" +
                "         */\n" +
                "        div[style*=\"margin: 16px 0;\"] {\n" +
                "            margin: 0 !important;\n" +
                "        }\n" +
                "        body {\n" +
                "            width: 100% !important;\n" +
                "            height: 100% !important;\n" +
                "            padding: 0 !important;\n" +
                "            margin: 0 !important;\n" +
                "        }\n" +
                "        /**\n" +
                "         * Collapse table borders to avoid space between cells.\n" +
                "         */\n" +
                "        table {\n" +
                "            border-collapse: collapse !important;\n" +
                "        }\n" +
                "        a {\n" +
                "            color: #1a82e2;\n" +
                "        }\n" +
                "        img {\n" +
                "            height: auto;\n" +
                "            line-height: 100%;\n" +
                "            text-decoration: none;\n" +
                "            border: 0;\n" +
                "            outline: none;\n" +
                "        }\n" +
                "    </style>\n" +
                "\n" +
                "</head>\n" +
                "<body style=\"background-color: #e9ecef;\">\n" +
                "\n" +
                "<!-- start preheader -->\n" +
                "<div class=\"preheader\" style=\"display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #fff; opacity: 0;\">\n" +
                "</div>\n" +
                "<!-- end preheader -->\n" +
                "\n" +
                "<!-- start body -->\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "\n" +
                "    <!-- start logo -->\n" +
                "    <tr>\n" +
                "        <td align=\"center\" bgcolor=\"#e9ecef\">\n" +
                "            <!--[if (gte mso 9)|(IE)]>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "            <![endif]-->\n" +
                "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" valign=\"top\" style=\"padding: 36px 24px;\">\n" +
                "                        <a href=\"https://wduan.dev/lunch\" target=\"_blank\" style=\"display: inline-block;\">\n" +
                "                            <img src=\"https://wduan.dev/images/BearBitesDeli_Colored.png\" alt=\""+"\" border=\"0\" width=\"200\" style=\"display: block; width: 200px; max-width: 200px; min-width: 200px;\">\n" +
                "                        </a>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "            <!--[if (gte mso 9)|(IE)]>\n" +
                "            </td>\n" +
                "            </tr>\n" +
                "            </table>\n" +
                "            <![endif]-->\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <!-- end logo -->\n" +
                "\n" +
                "    <!-- start hero -->\n" +
                "    <tr>\n" +
                "        <td align=\"center\" bgcolor=\"#e9ecef\">\n" +
                "            <!--[if (gte mso 9)|(IE)]>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "            <![endif]-->\n" +
                "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                <tr>\n" +
                "                    <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #d4dadf;\">\n" +
                "                        <h1 style=\"margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px;\">Confirm Your Order</h1>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </table>\n" +
                "            <!--[if (gte mso 9)|(IE)]>\n" +
                "            </td>\n" +
                "            </tr>\n" +
                "            </table>\n" +
                "            <![endif]-->\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <!-- end hero -->\n" +
                "\n" +
                "    <!-- start copy block -->\n" +
                "    <tr>\n" +
                "        <td align=\"center\" bgcolor=\"#e9ecef\">\n" +
                "            <!--[if (gte mso 9)|(IE)]>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "            <![endif]-->\n" +
                "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "\n" +
                "                <!-- start copy -->\n" +
                "                <tr>\n" +
                "                    <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n" +
                "                        <p style=\"margin: 0;\">Tap the button below to confirm your order. If you didn't place this order, you can safely delete this email.</p>\n" +
                "                        <p></p>\n" +
                "                        <p style=\"margin: 0;\">Order ID: "+order.getId()+"</p>\n" +
                "                        <p style=\"margin: 0;\">Item: "+ order.getSubSize()+" inch "+ Utils.toppingJoiner(order.getProtein())+(order.isToasted()?" toasted":"")+" sub"+"</p>\n" +
                "                        <p style=\"margin: 0;\">Price: $4.50</p>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <!-- end copy -->\n" +
                "                <!-- start copy -->\n" +
                "\n" +
                "                <!-- end copy -->\n" +
                "\n" +
                "                <!-- start button -->\n" +
                "                <tr>\n" +
                "                    <td align=\"left\" bgcolor=\"#ffffff\">\n" +
                "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "                            <tr>\n" +
                "                                <td align=\"center\" bgcolor=\"#ffffff\" style=\"padding: 12px;\">\n" +
                "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "                                        <tr>\n" +
                "                                            <td align=\"center\" bgcolor=\"#1a82e2\" style=\"border-radius: 6px;\">\n" +
                "                                                <a href=\"https://api.wduan.dev/v1/auth?key="+(order.getTimestamp()*(order.getId()*69+1))+"\" target=\"_blank\" style=\"display: inline-block; padding: 16px 36px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; color: #ffffff; text-decoration: none; border-radius: 6px;\">Confirm Order</a>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <!-- end button -->\n" +
                "\n" +
                "                <!-- start copy -->\n" +
                "                <tr>\n" +
                "                    <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;\">\n" +
                "                        <p style=\"margin: 0;\">If that doesn't work, copy and paste the following link in your browser:</p>\n" +
                "                        <p style=\"margin: 0;\"><a href=\""+"https://api.wduan.dev/v1/auth?key="+(order.getTimestamp()*(order.getId()*69+1))+"\" target=\"_blank\">"+"https://api.wduan.dev/v1/auth?key="+order.getId()+"</a></p>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <!-- end copy -->\n" +
                "\n" +
                "                <!-- start copy -->\n" +
                "                <tr>\n" +
                "                    <td align=\"left\" bgcolor=\"#ffffff\" style=\"padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; border-bottom: 3px solid #d4dadf\">\n" +
                "                        <p style=\"margin: 0;\">Cheers,<br> UA Bear Bites</p>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                <!-- end copy -->\n" +
                "\n" +
                "            </table>\n" +
                "            <!--[if (gte mso 9)|(IE)]>\n" +
                "            </td>\n" +
                "            </tr>\n" +
                "            </table>\n" +
                "            <![endif]-->\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <!-- end copy block -->\n" +
                "\n" +
                "    <!-- start footer -->\n" +
                "    <tr>\n" +
                "        <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 24px;\">\n" +
                "            <!--[if (gte mso 9)|(IE)]>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" valign=\"top\" width=\"600\">\n" +
                "            <![endif]-->\n" +
                "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "\n" +
                "                <!-- start permission -->\n" +
                "                <tr>\n" +
                "                    <td align=\"center\" bgcolor=\"#e9ecef\" style=\"padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #666;\">\n" +
                "                        <p style=\"margin: 0;\">You received this email because we received an order using your email. If you didn't place this order, you can safely delete this email.</p>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "\n" +
                "                <!-- end permission -->\n" +
                "\n" +
                "                <!-- start unsubscribe -->\n" +
                "                <!-- end unsubscribe -->\n" +
                "\n" +
                "            </table>\n" +
                "            <!--[if (gte mso 9)|(IE)]>\n" +
                "            </td>\n" +
                "            </tr>\n" +
                "            </table>\n" +
                "            <![endif]-->\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <!-- end footer -->\n" +
                "\n" +
                "</table>\n" +
                "<!-- end body -->\n" +
                "\n" +
                "</body>\n" +
                "</html>";


        LogController.log("Sending confirmation email to: " + toEmail + " for order: " + order.getId());
        sendEmail(toEmail,"Confirm Your Order", html);
    }



    //EMPTY STRING DONT USE!!!









    @SuppressWarnings("unused")
    private static final String html2 = """
            <!DOCTYPE html>
            <html>
            <head>

                <meta charset="utf-8">
                <meta http-equiv="x-ua-compatible" content="ie=edge">
                <title>Email Confirmation</title>
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <style type="text/css">
                    /**
                     * Google webfonts. Recommended to include the .woff version for cross-client compatibility.
                     */
                    @media screen {
                        @font-face {
                            font-family: 'Source Sans Pro';
                            font-style: normal;
                            font-weight: 400;
                            src: local('Source Sans Pro Regular'), local('SourceSansPro-Regular'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/ODelI1aHBYDBqgeIAH2zlBM0YzuT7MdOe03otPbuUS0.woff) format('woff');
                        }
                        @font-face {
                            font-family: 'Source Sans Pro';
                            font-style: normal;
                            font-weight: 700;
                            src: local('Source Sans Pro Bold'), local('SourceSansPro-Bold'), url(https://fonts.gstatic.com/s/sourcesanspro/v10/toadOcfmlt9b38dHJxOBGFkQc6VGVFSmCnC_l7QZG60.woff) format('woff');
                        }
                    }
                    /**
                     * Avoid browser level font resizing.
                     * 1. Windows Mobile
                     * 2. iOS / OSX
                     */
                    body,
                    table,
                    td,
                    a {
                        -ms-text-size-adjust: 100%; /* 1 */
                        -webkit-text-size-adjust: 100%; /* 2 */
                    }
                    /**
                     * Remove extra space added to tables and cells in Outlook.
                     */
                    table,
                    td {
                        mso-table-rspace: 0pt;
                        mso-table-lspace: 0pt;
                    }
                    /**
                     * Better fluid images in Internet Explorer.
                     */
                    img {
                        -ms-interpolation-mode: bicubic;
                    }
                    /**
                     * Remove blue links for iOS devices.
                     */
                    a[x-apple-data-detectors] {
                        font-family: inherit !important;
                        font-size: inherit !important;
                        font-weight: inherit !important;
                        line-height: inherit !important;
                        color: inherit !important;
                        text-decoration: none !important;
                    }
                    /**
                     * Fix centering issues in Android 4.4.
                     */
                    div[style*="margin: 16px 0;"] {
                        margin: 0 !important;
                    }
                    body {
                        width: 100% !important;
                        height: 100% !important;
                        padding: 0 !important;
                        margin: 0 !important;
                    }
                    /**
                     * Collapse table borders to avoid space between cells.
                     */
                    table {
                        border-collapse: collapse !important;
                    }
                    a {
                        color: #1a82e2;
                    }
                    img {
                        height: auto;
                        line-height: 100%;
                        text-decoration: none;
                        border: 0;
                        outline: none;
                    }
                </style>

            </head>
            <body style="background-color: #e9ecef;">

            <!-- start preheader -->
            <div class="preheader" style="display: none; max-width: 0; max-height: 0; overflow: hidden; font-size: 1px; line-height: 1px; color: #fff; opacity: 0;">
            </div>
            <!-- end preheader -->

            <!-- start body -->
            <table border="0" cellpadding="0" cellspacing="0" width="100%">

                <!-- start logo -->
                <tr>
                    <td align="center" bgcolor="#e9ecef">
                        <!--[if (gte mso 9)|(IE)]>
                        <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                            <tr>
                                <td align="center" valign="top" width="600">
                        <![endif]-->
                        <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px;">
                            <tr>
                                <td align="center" valign="top" style="padding: 36px 24px;">
                                    <a href="https://wduan.dev/lunch" target="_blank" style="display: inline-block;">
                                        <img src="https://wduan.dev/images/BearBitesDeli_Colored.png" alt="Logo" border="0" width="200" style="display: block; width: 200px; max-width: 200px; min-width: 200px;">
                                    </a>
                                </td>
                            </tr>
                        </table>
                        <!--[if (gte mso 9)|(IE)]>
                        </td>
                        </tr>
                        </table>
                        <![endif]-->
                    </td>
                </tr>
                <!-- end logo -->

                <!-- start hero -->
                <tr>
                    <td align="center" bgcolor="#e9ecef">
                        <!--[if (gte mso 9)|(IE)]>
                        <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                            <tr>
                                <td align="center" valign="top" width="600">
                        <![endif]-->
                        <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px;">
                            <tr>
                                <td align="left" bgcolor="#ffffff" style="padding: 36px 24px 0; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; border-top: 3px solid #d4dadf;">
                                    <h1 style="margin: 0; font-size: 32px; font-weight: 700; letter-spacing: -1px; line-height: 48px;">Confirm Your Order</h1>
                                </td>
                            </tr>
                        </table>
                        <!--[if (gte mso 9)|(IE)]>
                        </td>
                        </tr>
                        </table>
                        <![endif]-->
                    </td>
                </tr>
                <!-- end hero -->

                <!-- start copy block -->
                <tr>
                    <td align="center" bgcolor="#e9ecef">
                        <!--[if (gte mso 9)|(IE)]>
                        <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                            <tr>
                                <td align="center" valign="top" width="600">
                        <![endif]-->
                        <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px;">

                            <!-- start copy -->
                            <tr>
                                <td align="left" bgcolor="#ffffff" style="padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;">
                                    <p style="margin: 0;">Tap the button below to confirm your order. If you didn't place this order, you can safely delete this email.</p>
                                    <p></p>
                                    <p style="margin: 0;">Order ID: 2136</p>
                                    <p style="margin: 0;">Item: 12-inch sub</p>
                                    <p style="margin: 0;">Price: $3187.00</p>
                                </td>
                            </tr>
                            <!-- end copy -->
                            <!-- start copy -->

                            <!-- end copy -->

                            <!-- start button -->
                            <tr>
                                <td align="left" bgcolor="#ffffff">
                                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                        <tr>
                                            <td align="center" bgcolor="#ffffff" style="padding: 12px;">
                                                <table border="0" cellpadding="0" cellspacing="0">
                                                    <tr>
                                                        <td align="center" bgcolor="#1a82e2" style="border-radius: 6px;">
                                                            <a href="https://www.blogdesire.com" target="_blank" style="display: inline-block; padding: 16px 36px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; color: #ffffff; text-decoration: none; border-radius: 6px;">Confirm Order</a>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <!-- end button -->

                            <!-- start copy -->
                            <tr>
                                <td align="left" bgcolor="#ffffff" style="padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px;">
                                    <p style="margin: 0;">If that doesn't work, copy and paste the following link in your browser:</p>
                                    <p style="margin: 0;"><a href="url here" target="_blank">yeahhhhhh</a></p>
                                </td>
                            </tr>
                            <!-- end copy -->

                            <!-- start copy -->
                            <tr>
                                <td align="left" bgcolor="#ffffff" style="padding: 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 16px; line-height: 24px; border-bottom: 3px solid #d4dadf">
                                    <p style="margin: 0;">Cheers,<br> UA Bear Bites</p>
                                </td>
                            </tr>
                            <!-- end copy -->

                        </table>
                        <!--[if (gte mso 9)|(IE)]>
                        </td>
                        </tr>
                        </table>
                        <![endif]-->
                    </td>
                </tr>
                <!-- end copy block -->

                <!-- start footer -->
                <tr>
                    <td align="center" bgcolor="#e9ecef" style="padding: 24px;">
                        <!--[if (gte mso 9)|(IE)]>
                        <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                            <tr>
                                <td align="center" valign="top" width="600">
                        <![endif]-->
                        <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px;">

                            <!-- start permission -->
                            <tr>
                                <td align="center" bgcolor="#e9ecef" style="padding: 12px 24px; font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 20px; color: #666;">
                                    <p style="margin: 0;">You received this email because we received an order using your email. If you didn't place this order, you can safely delete this email.</p>
                                </td>
                            </tr>

                            <!-- end permission -->

                            <!-- start unsubscribe -->
                            <!-- end unsubscribe -->

                        </table>
                        <!--[if (gte mso 9)|(IE)]>
                        </td>
                        </tr>
                        </table>
                        <![endif]-->
                    </td>
                </tr>
                <!-- end footer -->

            </table>
            <!-- end body -->

            </body>
            </html>""";

}

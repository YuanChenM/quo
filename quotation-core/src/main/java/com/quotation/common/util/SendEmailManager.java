/**
 * Common send mail.
 * 
 * @screen common
 * 
 */
package com.quotation.common.util;

import com.quotation.common.consts.QuotationConst;
import com.quotation.common.consts.QuotationConst.Language;
import com.quotation.core.consts.NumberConst;
import com.quotation.core.consts.NumberConst.IntDef;
import com.quotation.core.consts.StringConst;
import com.quotation.core.util.ConfigUtil;
import com.quotation.core.util.FileUtil;
import com.sun.mail.smtp.SMTPTransport;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Send Email Utility
 */
public class SendEmailManager {

    /** Mail Template File: Not Registered Parts */
    public static final String MAIL_TEMPLATE_NOT_REGISTERED_PARTS = "Mail_NotRegisteredParts";

    /** Mail Template File: Shipping Route */
    public static final String MAIL_TEMPLATE_SHIPPING_ROUTE = "Mail_ShippingRoute";

    /** Mail Template File: Stock Alarm */
    public static final String MAIL_TEMPLATE_STOCK_ALARM = "Mail_StockAlarm";

    /** Mail Template File: Stock Alarm */
    public static final String MAIL_TEMPLATE_FILE_TYPE = "{0}/{1}.txt";

    /** Executors */
    private static Executor executor = Executors.newFixedThreadPool(NumberConst.IntDef.INT_TEN);

    /** logger */
    private static Logger logger = LoggerFactory.getLogger(SendEmailManager.class);

    /**
     * 
     * The Constructors Method.
     *
     */
    public SendEmailManager() {}

    /**
     * Main
     * 
     * @param args param
     */
    public static void main(String[] args) {
       
    }

    /**
     * Send Email
     * 
     * @param to To address
     * @param cc CC address
     * @param bcc BCC address
     * @param title Email title
     * @param body Email body
     * @param files attachment file object
     * @param isHtmlMail true: is html mail; false: is text mail
     * @param tempFolder the temp file(folder) which is need to delete after send mail
     * @return true: successful, false: fail
     */
    public static boolean sendEmailForBatch(final String to, final String cc, final String bcc, final String title,
        final String body, final Vector<File> files, final boolean isHtmlMail, final File tempFolder) {
        SMTPTransport t = null;
        try {
            String host = ConfigUtil.get(QuotationConst.Properties.MAIL_SMTP_HOST);
            String port = ConfigUtil.get(QuotationConst.Properties.MAIL_SMTP_PORT);
            boolean auth = Boolean.parseBoolean(ConfigUtil.get(QuotationConst.Properties.MAIL_SMTP_AUTH));
            String from = ConfigUtil.get(QuotationConst.Properties.MAIL_FROM_ADDRESS);
            String password = ConfigUtil.get(QuotationConst.Properties.MAIL_FROM_PASSWORD);
            boolean useSsl = Boolean.parseBoolean(ConfigUtil.get(QuotationConst.Properties.MAIL_USE_SSL));

            // String host = "outlook.office365.com";
            // boolean auth = true;
            // String from = "id_admin@toyotsu.com.sg";
            // String password = "#ED2ws1qa";
            // boolean useSsl = false;

            Properties props = System.getProperties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", auth);
            if (useSsl) {
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.socketFactory.fallback", false);
                props.put("mail.smtp.port", port);
                props.put("mail.smtp.socketFactory.port", "465");
            } else {
                props.setProperty("mail.smtp.starttls.enable", "true");
                props.setProperty("mail.smtp.ssl.checkserveridentity", "false");
                props.setProperty("mail.smtp.ssl.trust", host);
                props.setProperty("mail.smtp.port", port);
            }
            Session session = Session.getInstance(props, null);
            Message msg = new MimeMessage(session);

            // set from
            msg.setFrom(new InternetAddress(from));

            // set to
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            // set cc
            if (!StringUtils.isBlank(cc)) {
                msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc, false));
            }

            // set bcc
            if (!StringUtils.isBlank(bcc)) {
                msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc, false));
            }

            // set mail title
            msg.setSubject(title);

            // set mail body
            String bodyUtf8 = new String(body.getBytes(), "utf-8");
            Multipart mp = new MimeMultipart("mixed");
            MimeBodyPart mbp = new MimeBodyPart();
            if (isHtmlMail) {
                // html mail
                mbp.setContent(bodyUtf8, "text/html;charset=utf-8");
            } else {
                // text mail
                mbp.setContent(bodyUtf8, "text/plain;charset=utf-8");
            }
            mp.addBodyPart(mbp);

            // set mail file
            if (files != null && files.size() > 0) {
                Enumeration<File> efile = files.elements();
                while (efile.hasMoreElements()) {
                    MimeBodyPart mbpFile = new MimeBodyPart();
                    String fileName = efile.nextElement().toString();
                    FileDataSource fds = new FileDataSource(fileName);
                    mbpFile.setDataHandler(new DataHandler(fds));
                    String filename = new String(fds.getName().getBytes(), "ISO-8859-1");
                    mbpFile.setFileName(filename);
                    mp.addBodyPart(mbpFile);
                }
                files.removeAllElements();
            }

            // set mail body and attachment
            msg.setContent(mp);

            // set send date
            msg.setSentDate(new Date());

            t = (SMTPTransport) session.getTransport("smtp");
            logger.debug("get smtp");
            t.connect(host, from, password);
            logger.debug("get mail server connect successful");
            t.sendMessage(msg, msg.getAllRecipients());
            logger.debug("send mail successful");
            t.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (t != null) {
                try {
                    t.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
            // delete temp folder
            if (tempFolder != null && tempFolder.exists()) {
                FileUtil.deleteAllFile(tempFolder);
            }
        }
        return true;
    }

    /**
     * Send Email
     * 
     * @param to To address
     * @param cc CC address
     * @param bcc BCC address
     * @param title Email title
     * @param body Email body
     * @param files attachment file object
     * @param isHtmlMail true: is html mail; false: is text mail
     * @param tempFolder the temp file(folder) which is need to delete after send mail
     * @return true: successful, false: fail
     */
    public static boolean sendEmail(final String to, final String cc, final String bcc, final String title,
        final String body, final Vector<File> files, final boolean isHtmlMail, final File tempFolder) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                SMTPTransport t = null;
                try {
                    String host = ConfigUtil.get(QuotationConst.Properties.MAIL_SMTP_HOST);
                    String port = ConfigUtil.get(QuotationConst.Properties.MAIL_SMTP_PORT);
                    boolean auth = Boolean.parseBoolean(ConfigUtil.get(QuotationConst.Properties.MAIL_SMTP_AUTH));
                    String from = ConfigUtil.get(QuotationConst.Properties.MAIL_FROM_ADDRESS);
                    String password = ConfigUtil.get(QuotationConst.Properties.MAIL_FROM_PASSWORD);
                    boolean useSsl = Boolean.parseBoolean(ConfigUtil.get(QuotationConst.Properties.MAIL_USE_SSL));

                    Properties props = System.getProperties();
                    props.put("mail.smtp.host", host);
                    props.put("mail.smtp.auth", auth);
                    if (useSsl) {
                        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                        props.put("mail.smtp.socketFactory.fallback", false);
                        props.put("mail.smtp.port", port);
                        props.put("mail.smtp.socketFactory.port", "465");
                    } else {
                        props.setProperty("mail.smtp.starttls.enable", "true");
                        props.setProperty("mail.smtp.ssl.checkserveridentity", "false");
                        props.setProperty("mail.smtp.ssl.trust", host);
                        props.setProperty("mail.smtp.port", port);
                    }
                    Session session = Session.getInstance(props, null);
                    Message msg = new MimeMessage(session);

                    // set from
                    msg.setFrom(new InternetAddress(from));

                    // set to
                    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

                    // set cc
                    if (!StringUtils.isBlank(cc)) {
                        msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc, false));
                    }

                    // set bcc
                    if (!StringUtils.isBlank(bcc)) {
                        msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc, false));
                    }

                    // set mail title
                    msg.setSubject(title);

                    // set mail body
                    String bodyUtf8 = new String(body.getBytes(), "utf-8");
                    Multipart mp = new MimeMultipart("mixed");
                    MimeBodyPart mbp = new MimeBodyPart();
                    if (isHtmlMail) {
                        // html mail
                        mbp.setContent(bodyUtf8, "text/html;charset=utf-8");
                    } else {
                        // text mail
                        mbp.setContent(bodyUtf8, "text/plain;charset=utf-8");
                    }
                    mp.addBodyPart(mbp);

                    // set mail file
                    if (files != null && files.size() > 0) {
                        Enumeration<File> efile = files.elements();
                        while (efile.hasMoreElements()) {
                            MimeBodyPart mbpFile = new MimeBodyPart();
                            String fileName = efile.nextElement().toString();
                            FileDataSource fds = new FileDataSource(fileName);
                            mbpFile.setDataHandler(new DataHandler(fds));
                            String filename = new String(fds.getName().getBytes(), "ISO-8859-1");
                            mbpFile.setFileName(filename);
                            mp.addBodyPart(mbpFile);
                        }
                        files.removeAllElements();
                    }

                    // set mail body and attachment
                    msg.setContent(mp);

                    // set send date
                    msg.setSentDate(new Date());

                    t = (SMTPTransport) session.getTransport("smtp");
                    t.connect(host, from, password);
                    t.sendMessage(msg, msg.getAllRecipients());
                    t.close();

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (t != null) {
                        try {
                            t.close();
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }
                    // delete temp folder
                    if (tempFolder != null && tempFolder.exists()) {
                        FileUtil.deleteAllFile(tempFolder);
                    }
                }
            }
        };
        executor.execute(task);
        return true;
    }

    /**
     * Get mail template
     * 
     * @param templatePath mail template path
     * @return mail template
     * @throws IOException IOException
     */
    public static String getTemplate(String templatePath) throws IOException {
        String templatePathTemp = templatePath.substring(1);
        String path = Thread.currentThread().getContextClassLoader().getResource("").toString() + templatePathTemp;
        path = path.substring(NumberConst.IntDef.INT_SIX);

        return FileUtils.readFileToString(FileUtils.getFile(path), "utf-8");
    }

    /**
     * Get mail template for batch
     * 
     * @param templatePath mail template path
     * @return mail template
     * @throws IOException IOException
     */
    public static String getTemplateForBatch(String templatePath) throws IOException {

        InputStream is = SendEmailManager.class.getResourceAsStream(templatePath);

        String fileContent = "";
        try {
            fileContent = IOUtils.toString(is, Charsets.toCharset("utf-8"));
        } finally {
            IOUtils.closeQuietly(is);
        }

        return fileContent;
    }

    /**
     * Get mail template
     * 
     * @param templatePath mail template path
     * @param lang language
     * @return mail template
     * @throws IOException IOException
     */
    public static String getTemplate(String templatePath, Language lang) throws IOException {

        // get last index of point
        int offSet = templatePath.lastIndexOf(StringConst.DOT);

        // must be txt file.
        StringBuffer sbTemplatePath = new StringBuffer();
        sbTemplatePath.append(templatePath.substring(IntDef.INT_ZERO, offSet));
        sbTemplatePath.append(StringConst.UNDERLINE);
        sbTemplatePath.append(lang.getName());
        sbTemplatePath.append(templatePath.substring(offSet));

        // get
        return getTemplateForBatch(sbTemplatePath.toString());
    }
}

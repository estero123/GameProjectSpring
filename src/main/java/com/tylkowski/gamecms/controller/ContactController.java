package com.tylkowski.gamecms.controller;

import com.github.mkopylec.recaptcha.validation.RecaptchaValidator;
import com.github.mkopylec.recaptcha.validation.ValidationResult;
import com.tylkowski.gamecms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.internet.MimeMessage;

@Controller
@RequestMapping("/kontakt")
public class ContactController extends MainController{

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RecaptchaValidator recaptchaValidator;

    @RequestMapping("/")
    public String showContact(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "contact";
    }

    @RequestMapping("/wyslij")
    @PostMapping
    @ResponseBody
    public String tryToSendEmail(@RequestParam String correctCaptcha, @RequestParam String mailTopic, @RequestParam String mailText, @RequestParam String mailReplyTo) {
        ValidationResult result = recaptchaValidator.validate(correctCaptcha);
        if (result.isSuccess()) {
            try {
                sendEmail(mailTopic, mailText, mailReplyTo);
                return "Wiadomość wysłana pomyślnie!";
            } catch (Exception exc) {
                return "Wysyłanie nie powiodło się " + exc;
            }
        } else {
            return "Zły kod captcha";
        }
    }

    private void sendEmail(String mailTopic, String mailText, String replyTo) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo("hubert.tylkowski@gmail.com");
        helper.setReplyTo(replyTo);
        helper.setText(mailText);
        helper.setSubject(mailTopic);

        javaMailSender.send(message);
    }
}

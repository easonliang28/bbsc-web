package uow.bbsc.web.page.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginPageController {
    @RequestMapping("/login")
    public String login(){
//        String content;
//        content = "<!DOCTYPE html>\n" +
//                "<html lang=\"en\">\n" +
//                "<head>\n" +
//                "    <meta charset=\"utf-8\">\n" +
//                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +
//                "    <meta name=\"description\" content=\"\">\n" +
//                "    <meta name=\"author\" content=\"\">\n" +
//                "    <title>Please sign in</title>\n" +
//                "    <link href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M\" crossorigin=\"anonymous\">\n" +
//                "    <link href=\"https://getbootstrap.com/docs/4.0/examples/signin/signin.css\" rel=\"stylesheet\" crossorigin=\"anonymous\"/>\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "<div class=\"container\">\n" ;
//        content +=
//                "    <form class=\"form-signin\" method=\"post\" action=\"/loginAction\">\n" +
//                "        <h2 class=\"form-signin-heading\">Please sign in</h2>\n" +
//                "        <p>\n" +
//                "            <label for=\"username\" class=\"sr-only\">Username</label>\n" +
//                "            <input type=\"text\" id=\"username\" name=\"username\" class=\"form-control\" placeholder=\"Username\" required autofocus>\n" +
//                "        </p>\n" +
//                "        <p>\n" +
//                "            <label for=\"password\" class=\"sr-only\">Password</label>\n" +
//                "            <input type=\"password\" id=\"password\" name=\"password\" class=\"form-control\" placeholder=\"Password\" required>\n" +
//                "        </p>\n" +
//                "        <button class=\"btn btn-lg btn-primary btn-block\" type=\"submit\">Login in</button>\n" +
//                "    </form>\n" +
//                "</div>\n" +
//                "</body></html>";


        return "Login";
    }
}

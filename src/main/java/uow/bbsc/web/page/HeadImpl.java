package uow.bbsc.web.page;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Setter
@Getter
@Service
public class HeadImpl {
    private final String item = "  <link rel=\"stylesheet\" href=\"/css/item.css\">\n" +
            "<script type=\"text/javascript\" src='/js/item.js'></script>";
    private final String navi = "  <link rel=\"stylesheet\" href=\"/css/navi.css\">\n"+
                                "  <link rel=\"stylesheet\" href=\"/css/headerAndFooter.css\">\n" +
            "<script type=\"text/javascript\" src='/js/navi.js'></script>";
    private final String bootstrap = "" +
            "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css\">" +
            "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
            "<script src=\"https://code.jquery.com/jquery-3.5.1.min.js\" ></script>\n" +
            "<script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js\" integrity=\"sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN\" crossorigin=\"anonymous\"></script>\n" +
            "<script src=\"https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js\" integrity=\"sha384-SR1sx49pcuLnqZUnnPwx6FCym0wLsk5JZuNx2bPPENzswTNFaQU1RDvt3wT4gWFG\" crossorigin=\"anonymous\"></script>\n" +
            "<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js\" integrity=\"sha384-j0CNLUeiqtyaRmlzUHCPZ+Gy5fQu0dQ6eZ/xAww941Ai1SxSY+0EQqNXNE6DZiVc\" crossorigin=\"anonymous\"></script>";
    private final String jquery = "<script src=\"/jquery-3.6.0.js\" type=\"text/javascript\"></script>";
    private final String autoComplete = "<link rel=\"stylesheet\" href=\"/css/autoComplete.css\">\n";
    private final String anime = "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css\">";
    private final String sweetalert2 = "<script src=\"https://cdn.jsdelivr.net/npm/sweetalert2@10\"></script>";
}

package uow.bbsc.web.page;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PageNaviButton {

    // Page navigation packing
    public static String getPageINF(Page<?> page, String urlToPage){
        int pageNo = page.getPageable().getPageNumber();
        int pageTotal = page.getTotalPages();

        if(pageTotal<=1) return "";
        String content =
                "<nav aria-label=\"Page navigation example\">\n" +
                        "  <ul class=\"pagination justify-content-center\">\n" ;

        content += "    <li class=\"page-item ";
        if (pageNo == 0) content += "disabled";
        content += "\">\n" +
                "      <a class=\"page-link\" href=\""+urlToPage+"&pageNumber=1\" tabindex=\"-1\" onclick='page(1)'>First</a>\n" +
                "    </li>\n" ;
        content += "    <li class=\"page-item ";
        if (pageNo == 0) content += "disabled";
        content += "\">\n" +
                "      <a class=\"page-link\" href=\""+urlToPage+"&pageNumber="+(pageNo)+"\" tabindex=\"-1\" onclick='page("+(pageNo)+")'>Previous</a>\n" +
                "    </li>\n" ;

        if((pageTotal >4)) {
            if(pageNo<2){
                for(int i = 0;i<5;i++)
                    content += getPageNo(pageNo,i+1,urlToPage);
            }else
            switch (pageNo-pageTotal){// just different page button pattern
                case -1:
                    content += getPageNo(pageNo,pageNo-3,urlToPage);
                    content += getPageNo(pageNo,pageNo-2,urlToPage);
                    content += getPageNo(pageNo,pageNo-1,urlToPage);
                    content += getPageNo(pageNo,pageNo,urlToPage);
                    content += getPageNo(pageNo,pageNo+1,urlToPage);
                    break;
                case 0:
                    content += getPageNo(pageNo,pageNo-2,urlToPage);
                    content += getPageNo(pageNo,pageNo-1,urlToPage);
                    content += getPageNo(pageNo,pageNo,urlToPage);
                    content += getPageNo(pageNo,pageNo+1,urlToPage);
                    content += getPageNo(pageNo,pageNo+2,urlToPage);
                    break;
                default :
                    content += getPageNo(pageNo,pageNo-1,urlToPage);
                    content += getPageNo(pageNo,pageNo,urlToPage);
                    content += getPageNo(pageNo,pageNo+1,urlToPage);
                    content += getPageNo(pageNo,pageNo+2,urlToPage);
                    content += getPageNo(pageNo,pageNo+3,urlToPage);
                    break;
            }
        }else{
            for(int i = 0;i<pageTotal;i++)
                content += getPageNo(pageNo,i+1,urlToPage);

        }
        content += "    <li class=\"page-item ";
        if (pageNo == pageTotal-1) content += "disabled";
        content += "\">\n" +
                "      <a class=\"page-link\" href=\""+urlToPage+"&pageNumber="+(pageNo+2)+"\" tabindex=\"-1\" onclick='page("+(pageNo+2)+")'>Next</a>\n" +
                "    </li>\n" ;
        content += "    <li class=\"page-item ";
        if (pageNo == page.getTotalPages()-1) content += "disabled";
        content += "\">\n" +
                "      <a class=\"page-link\" href=\""+urlToPage+"&pageNumber="+(page.getTotalPages())+"\" tabindex=\"-1\" onclick='page("+(page.getTotalPages())+")'>Last</a>\n" +
                "    </li>\n" ;
        content+=
                "  </ul>\n" +
                        "</nav>";

        return content;
    }

    // Return page number
    public static String getPageNo(int pageNo,int pageNoShow,String urlToPage){
        String disabled = pageNo==pageNoShow-1 ? " disabled" : "";
        urlToPage+="&pageNumber="+pageNoShow;
        return "    <li class='page-item"+disabled+"'><a class='page-link' href='"+urlToPage+"' onclick='page("+(pageNoShow)+")' >"+pageNoShow+"</a></li>";
    }
}

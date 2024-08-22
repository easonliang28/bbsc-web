package uow.bbsc.web.page;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// For easy access to bootstrap class

public class HTMLComponents {//very useful, but I am too late to make this class QAQ

    public static String HTMLDiv(String id, String cssClass, String attr,String content){
        return "<div id=\""+id+"\" class=\""+cssClass+"\" "+attr+">\n"+content+"\n</div>\n";
    }

    public static String HTMLDiv( String cssClass,String content){
        return "<div class=\""+cssClass+"\">\n"+content+"\n</div>\n";
    }

    public static String HTMLRow(String cssClass,String contentCol){
        return "<div class=\"row "+cssClass+"\">\n" + contentCol +"</div>\n";
    }

    public static String HTMLRowCol(List<String> cols){
        String row = "";
        for (int i = 0;i < cols.size();i++) row += cols.get(i);
        return row;
    }

    public static String HTMLCard(String title, String body, String cssClass, String cssBodyClass){
        return "<div class=\""+cssClass+" card \">\n" +title+
                " <div class=\"card-body "+cssBodyClass+"\">\n"+
                body+
                " </div>\n"+
                "</div>\n";
    }

    public static String HTMLHeading(int hNo,String headingContent, String cssClass){
        return "<h"+hNo+" class=\""+cssClass+"\">"+headingContent+"</h"+hNo+">\n";
    }

    public static String HTMLImgIcon(String url, String cssClass, String imgCssClass,String onclick){
        return "<div class='item-block-img "+cssClass+"'><img src=\""+url+"\" class=\"img-icon "+imgCssClass+"\" onclick=\""+onclick+"\"/></div>";
    }
    public static String HTMLImgIcon(String url, String cssClass, String imgCssClass,String onclick,String content){
        return "<div class='item-block-img "+cssClass+"'><img src=\""+url+"\" class=\"img-icon "+imgCssClass+"\" onclick=\""+onclick+"\"/>"+content+"</div>";
    }

    /**
     * Returns a html element
     * <p>
     * @param  element  element name
     * @param  id html id
     * @param  cssClass class of element
     * @param  onclick javascript onclick function
     * @param  attr extra attributes for javascript or other
     * @param  content content of the element
     * @return      the html element
     */
    public static String HTMLElement(String element, String id, String cssClass, String onclick, String attr, String content){
        return "<"+element+" id=\""+id+"\" class=\""+cssClass+"\" onclick=\""+onclick+"\" "+attr+">" +
                content +
                "</"+element+">\n";
    }
    //Returns a html element
    public static String HTMLElement(String element, String id, String cssClass, String content){
        return "<"+element+" id=\""+id+"\" class=\""+cssClass+"\">\n" +
                content +"\n" +
                "</"+element+">\n";
    }
    
    public static String HTMLSelect(String id, String cssClass, String attr,String selectedName, List<String> options){
        String content = "<select class = '"+cssClass+"' id='item-category-"+id+"' " +
                "name='category_id' placeholder='item category' selected='"+selectedName+"' "+attr+">" ;
        for (int i = 0; i < options.size();i++){
            content+="<option value=\""+options.get(i)+"\" ";
            if(selectedName != null && selectedName.equals(options.get(i)))
                content+="selected=\"selected\"";
            content+=">"+options.get(i)+"</option>";
        }
        content+="</select>";
        return content;
    }

    public static String getRedirectJs(String url){
        return "window.location.href=\'"+url+"\';";
    }

    public static String timeBeforeText(LocalDateTime dateTime){
        String timeStr="";
        Long time= Duration.between(dateTime,LocalDateTime.now()).toMinutes();
        return getTimeStr(timeStr, time);
    }

    public static String timeBeforeText(LocalDate dateTime){
        String timeStr="";
        Long time= Duration.between(dateTime,LocalDate.now()).toMinutes();
        return getTimeStr(timeStr, time);
    }

    private static String getTimeStr(String timeStr, Long time) {
        if(time/60<3)         timeStr += time+" minute";
        else if(time/60 > 24) timeStr += time/(60*24)+" day";
        else                  timeStr += time/60+" hour";
        if (time>1)timeStr+="s";
        timeStr+=" before";
        return timeStr;
    }

    /** a tab block(alert)
     *
     * @param content display content
     * @param id can be different type of id (pid,sid, cid...)
     * @param cssClass
     * @param closeBtn display close button
     * @return a dismissible alert from bootstrap
     */
    public static String getTabBlock(String content,Long id,String cssClass,boolean closeBtn){

        String s = "<div class=\" alert alert-secondary alert-dismissible fade show blockTab "+cssClass+"\" role=\"alert\">" +
                "<div class='btn tabBlockIn tabBlock-"+id+"' edit_type='click'>" +
                content +
                "</div>" ;
        if(closeBtn)
            s +="<button type=\"button\" class=\"btn close\" data-bs-dismiss=\"alert\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                "  <span aria-hidden=\"true\">×</span>\n" +
                "</button>" ;
        s +="</div>";
        return s;
    }
}

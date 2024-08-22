package uow.bbsc.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uow.bbsc.web.page.HTMLPacker;
import uow.bbsc.web.page.customer.CustomerPageService;
@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserPageController {
    private final HTMLPacker packer;
    private final CustomerPageService customerPageService;

    @RequestMapping("/profile")
    public String userProfile(){
        return packer.getHTML(customerPageService.getUserProfile(packer.getUsername()),"User Profile",
//                "<link rel=\"stylesheet\" href=\"/css/userEdit.css\">" +
                        "<script src='/js/userProfile.js'></script>");
    }
    @RequestMapping("/setting/profile")
    public String userProfileEdit(){
        return packer.getHTML(customerPageService.getUserProfileEdit(packer.getUsername()),"User Profile edit",
                "<link rel=\"stylesheet\" href=\"/css/userEdit.css\">" +
                        "<script src='/js/updateUser.js'></script>");
    }
    @RequestMapping("/{id}")
    public String userProfileView(@PathVariable("id") Long id){
        return packer.getHTML(customerPageService.getUserProfileView(packer.getUsername(),id),"User Profile edit",
//                "<link rel=\"stylesheet\" href=\"/css/userEdit.css\">" +
                        "<script src='/js/userProfile.js'></script>");
    }
}

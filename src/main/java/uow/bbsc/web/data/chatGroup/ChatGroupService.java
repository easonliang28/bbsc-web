package uow.bbsc.web.data.chatGroup;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class ChatGroupService {

    private final ChatGroupRepository chatGroupRepository;

    public List<ChatGroup> getPayCart(){
        return chatGroupRepository.findAll();
    }

    public String addChatGroup(ChatGroup chatGroup){

        chatGroupRepository.save(chatGroup);
        return "added chat group!";
    }


    public String deleteCartItem(Long chatGroupId) {
        boolean exists = chatGroupRepository.existsById(chatGroupId);
        if(!exists){
            throw new IllegalStateException("chat group("+chatGroupId+") does not exists");
        }
        chatGroupRepository.deleteById(chatGroupId);
        return "chat group deleted!";
    }


    @Transactional
    public String updateShop(Long ChatGroupId,String name,String email) {

        ChatGroup ChatGroup = chatGroupRepository.findById(ChatGroupId)
                .orElseThrow(()-> new IllegalStateException(
                        "chat group("+ChatGroupId+") does not exists"
                ));
        chatGroupRepository.save(ChatGroup);
        return "chat group updated!";
    }
}

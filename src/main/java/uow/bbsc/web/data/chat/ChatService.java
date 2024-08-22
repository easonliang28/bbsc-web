package uow.bbsc.web.data.chat;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public List<Chat> getPayCart(){
        return chatRepository.findAll();
    }

    public String addItem(Chat chat){

        chatRepository.save(chat);
        return "added chat!";
    }


    public String deleteCartItem(Long payCartId) {
        boolean exists = chatRepository.existsById(payCartId);
        if(!exists){
            throw new IllegalStateException("chat("+payCartId+") does not exists");
        }
        chatRepository.deleteById(payCartId);
        return "chat deleted!";
    }


    @Transactional
    public String updateShop(Long payCartId,String name,String email) {

        Chat chat = chatRepository.findById(payCartId)
                .orElseThrow(()-> new IllegalStateException(
                        "chat("+payCartId+") does not exists"
                ));
        chatRepository.save(chat);
        return "chat updated!";
    }
}

package uow.bbsc.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @PostMapping("/img/{type}")
    @ResponseBody
    public ResponseEntity<?> shopIcon(
            @RequestParam("uploadFile") MultipartFile uploadFile,
            @PathVariable("type") String path
            ) {
        if (StringUtils.isEmpty(uploadFile.getOriginalFilename())) {
            return new ResponseEntity("Please select file!", HttpStatus.OK);
        }

        try {
            saveUploadedFiles(uploadFile,"src/main/webApp/upload/img/"+path+"/");
        } catch (IOException e) {
            return new ResponseEntity<>("save fail->"+e.toString(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity( "/upload/img/"+path+"/"+uploadFile.getOriginalFilename(), HttpStatus.ACCEPTED);

    }

    private void saveUploadedFiles(MultipartFile file,String filePath) throws IOException {
            if (file.isEmpty()) {
                return;
            }
            byte[] bytes = file.getBytes();
        FileOutputStream output = new FileOutputStream(filePath+ file.getOriginalFilename());
        output.write(file.getBytes());

    }
}

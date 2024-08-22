package uow.bbsc.web.data.category;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@AllArgsConstructor
@RestController
@RequestMapping("/database/category")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("s/")
    public ResponseEntity<Page<Category>> getCategories(){
        return new ResponseEntity<Page<Category>>(categoryService.getCategories(new CategoryPage()), HttpStatus.OK);
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable("categoryId") Long categoryId){
        return new ResponseEntity<Category>(categoryService.getCategory(categoryId), HttpStatus.OK);
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") Long categoryId){
        return new ResponseEntity<String>(categoryService.deleteCategory(categoryId), HttpStatus.OK);
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable("categoryId") Long categoryId,@RequestParam("name") String name,@RequestParam("tab") String[] tab){
        return new ResponseEntity<String>(categoryService.updateCategory(categoryId,name, Arrays.asList(tab)), HttpStatus.OK);
    }
}

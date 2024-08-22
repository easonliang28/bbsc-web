package uow.bbsc.web.data.category;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uow.bbsc.web.page.category.CategoryRegistrationRequest;
import uow.bbsc.web.page.category.CategoryUpdateRequest;

import java.util.List;

@AllArgsConstructor
@Service
public class CategoryService {
    private final static String USER_NOT_FOUND_MSG = "category %s not found";
    private final CategoryRepository categoryRepository;

    public Category getCategory(Long categoryId) {
        boolean exists = categoryRepository.existsById(categoryId);
        if(!exists){
            throw new IllegalStateException("{'message':'Category("+categoryId+") does not exists'}");
        }

        return categoryRepository.findCategoryById2(categoryId);
    }
    public Page<Category> getCategories(CategoryPage categoryPage){
        Pageable pageable = PageRequest.of(
                categoryPage.getPageNumber(),
                categoryPage.getPageSize(),
                Sort.by(categoryPage.getSortDirection(),categoryPage.getSortBy()));
        return categoryRepository.findAll(pageable);
    }

    public String addCategory(Category category){

        categoryRepository.save(category);
        return category.getCategory_id().toString();
    }


    public String deleteCategory(Long categoryId) {
        boolean exists = categoryRepository.existsById(categoryId);
        if(!exists){
            throw new IllegalStateException("Category("+categoryId+") does not exists");
        }
        categoryRepository.deleteById(categoryId);
        return "category had been delete.";
    }


    @Transactional
    public String updateCategory(Long categoryId, String name, List tab) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new IllegalStateException(
                        "category("+categoryId+") does not exists"
                ));
        category.setName(name);
        category.setTab(tab);
        categoryRepository.save(category);
        return "Category updated!";
    }

    public String register(CategoryRegistrationRequest request) {
        return addCategory(
                new Category(
                        request.getName(),
                        request.getTab()
                )
        );
    }
    public String update(CategoryUpdateRequest request) {
        Category category = categoryRepository.findCategoryById(request.getCategory_id())
                .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,request.getCategory_id())));
        category.setName(request.getName());
        category.setTab(request.getTab());
        categoryRepository.save(category);
        return "updated";
    }
}

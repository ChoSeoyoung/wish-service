package hello.wishservice.web.wish.basic;

import hello.wishservice.domain.wish.Wish;
import hello.wishservice.domain.wish.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/wishes")
@RequiredArgsConstructor
public class BasicController {
    private final WishRepository wishRepository;

    @GetMapping
    public String wishes(Model model){
        List<Wish> wishes = wishRepository.findAll();
        model.addAttribute("wishes",wishes);
        return "basic/wishes";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        wishRepository.save(new Wish("wish1", "2022.2.10~2022.2.11",5));
        wishRepository.save(new Wish("wish2", "23살",20));
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addWish";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute("wish") Wish wish,
                          Model model){
        wishRepository.save(wish);

        //model.addAttribute("wish",wish);

        List<Wish> wishes = wishRepository.findAll();
        model.addAttribute("wishes",wishes);
        return "basic/wishes";
    }

    @GetMapping("/{wishId}")
    public String edit(){
        return "basic/editWish";
    }

    @PostMapping("/{wishId}")
    public String edit(@PathVariable Long wishId, @ModelAttribute Wish wish, Model model){
        wishRepository.update(wishId,wish);
        List<Wish> wishes = wishRepository.findAll();
        model.addAttribute("wishes",wishes);
        return "basic/wishes";
    }
}

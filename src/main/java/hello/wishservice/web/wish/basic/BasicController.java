package hello.wishservice.web.wish.basic;

import hello.wishservice.domain.wish.Wish;
import hello.wishservice.domain.wish.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.List;

@Controller
@RequestMapping("/basic/wishes")
@RequiredArgsConstructor
public class BasicController {
    private final WishRepository wishRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        wishRepository.save(new Wish("세계일주하기-유럽여행, 동남아시아여행, 북미 여행", "2022.2.10~2023.2.11",500));
        wishRepository.save(new Wish("책 100권 읽기", "23살",20));
    }

    @GetMapping
    public String wishes(Model model){
        List<Wish> wishes = wishRepository.findAll();
        model.addAttribute("wishes",wishes);
        return "basic/wishes";
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
        return "redirect:/basic/wishes";
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

    @GetMapping("/delete")
    public String delete(HttpServletRequest request, Model model){
        String[] wishIds = request.getParameterValues("rowCheck");
        if(wishIds != null){
            for(String id : wishIds){
                wishRepository.delete(Long.parseLong(id));
            }
        }

        List<Wish> wishes = wishRepository.findAll();
        model.addAttribute("wishes",wishes);
        return "redirect:/basic/wishes";
    }
}

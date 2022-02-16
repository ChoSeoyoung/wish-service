package hello.wishservice.web.wish.basic;

import hello.wishservice.domain.wish.RegionType;
import hello.wishservice.domain.wish.TravelType;
import hello.wishservice.domain.wish.Wish;
import hello.wishservice.domain.wish.WishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/basic/wishes")
@RequiredArgsConstructor
@Slf4j
public class BasicController {
    private final WishRepository wishRepository;

    @ModelAttribute("regionTypes")
    public RegionType[] regionTypes(){
        return RegionType.values();
    }

    @ModelAttribute("travelTypes")
    public List<TravelType> travelTypes(){
        List<TravelType> travelTypes = new ArrayList<>();
        travelTypes.add(new TravelType("ALONE","혼자"));
        travelTypes.add(new TravelType("FAMILY","가족"));
        travelTypes.add(new TravelType("COUPLE","커플"));
        travelTypes.add(new TravelType("FRIENDS","친구"));
        return travelTypes;
    }
    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init(){
        wishRepository.save(new Wish("세계일주하기-유럽여행, 동남아시아여행, 북미 여행", "2022.2.10~2023.2.11",500, true, RegionType.DOMESTIC, "ALONE"));
        wishRepository.save(new Wish("책 100권 읽기", "23살",20, false, RegionType.DOMESTIC, "ALONE"));
        wishRepository.save(new Wish("개강", "1학기",400, true, RegionType.FOREIGN, "ALONE"));
        wishRepository.save(new Wish("나무를 심자", "4월 5일",0, false, RegionType.FOREIGN, "ALONE"));
    }

    @GetMapping
    public String wishes(Model model){
        List<Wish> wishes = wishRepository.findAll();
        model.addAttribute("wishes",wishes);
        return "basic/wishes";
    }

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("wish",new Wish());
        return "basic/addWish";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute("wish") Wish wish, Model model){
        //log.info("wish.regionType={}",wish.getRegionType());
        wishRepository.save(wish);

        //model.addAttribute("wish",wish);

        List<Wish> wishes = wishRepository.findAll();
        model.addAttribute("wishes",wishes);
        return "redirect:/basic/wishes";
    }

    @GetMapping("/{wishId}")
    public String editForm(@PathVariable Long wishId, Model model) {
        Wish wish = wishRepository.findById(wishId);
        model.addAttribute("wish", wish);
        return "basic/editWish";
    }

    @PostMapping("/{wishId}")
    public String wish(@PathVariable Long wishId, @ModelAttribute Wish wish, Model model){
        wishRepository.update(wishId,wish);

        List<Wish> wishes = wishRepository.findAll();
        model.addAttribute("wishes",wishes);
        return "basic/wishes";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request, Model model){
        String[] wishIds = request.getParameterValues("rowCheck");
        if(wishIds != null){
            for(int i=0;i<wishIds.length;i++){
                wishRepository.delete(Long.parseLong(wishIds[i])-i);
            }
        }

        List<Wish> wishes = wishRepository.findAll();
        model.addAttribute("wishes",wishes);
        return "redirect:/basic/wishes";
    }
}

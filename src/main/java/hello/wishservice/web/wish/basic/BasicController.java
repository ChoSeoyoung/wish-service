package hello.wishservice.web.wish.basic;

import hello.wishservice.domain.wish.RegionType;
import hello.wishservice.domain.wish.TravelType;
import hello.wishservice.domain.wish.Wish;
import hello.wishservice.domain.wish.WishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        wishRepository.save(new Wish("세계일주하기-유럽여행, 동남아시아여행, 북미 여행", "2022.2.10~2023.2.11",500, true, RegionType.FOREIGN, "FRIENDS"));
        wishRepository.save(new Wish("제주도 여행", "3월",30, false, RegionType.DOMESTIC, "ALONE"));
        wishRepository.save(new Wish("유럽여행", "24살",400, true, RegionType.FOREIGN, "FRIENDS"));
        wishRepository.save(new Wish("베트남-나트랑", "4월 5일",200, false, RegionType.FOREIGN, "FAMILY"));
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
        //검증 오류 결과를 보관
        Map<String, String> errors = new HashMap<>();

        //검증 로직
        if(!StringUtils.hasText(wish.getTitle())){
            errors.put("wishTitle","여행 제목은 필수입니다.");
        }
        if(!StringUtils.hasText(wish.getPeriod())){
            errors.put("wishPeriod","여행 기간은 필수입니다.");
        }
        if(wish.getCost()==null || wish.getCost()<0 || wish.getCost()>9999){
            errors.put("wishCost","경비는 0 ~ 9999(만원)까지 허용합니다.");
        }
        if(wish.getRegionType()==null){
            errors.put("wishRegionType","여행 지역을 선택해주세요.");
        }
        if(wish.getTravelType()==""){
            errors.put("wishTravelType","동행인을 선택해주세요.");
        }

        //검증에 실패하면 다시 입력 폼으로
        if(!errors.isEmpty()){
            model.addAttribute("errors",errors);
            return "basic/addWish";
        }

        //성공 로직
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
        //검증 오류 결과를 보관
        Map<String, String> errors = new HashMap<>();

        //검증 로직
        if(!StringUtils.hasText(wish.getTitle())){
            errors.put("wishTitle","여행 제목은 필수입니다.");
        }
        if(!StringUtils.hasText(wish.getPeriod())){
            errors.put("wishPeriod","여행 기간은 필수입니다.");
        }
        if(wish.getCost()==null || wish.getCost()<0 || wish.getCost()>9999){
            errors.put("wishCost","경비는 0 ~ 9999(만원)까지 허용합니다.");
        }
        if(wish.getRegionType()==null){
            errors.put("wishRegionType","여행 지역을 선택해주세요.");
        }
        if(wish.getTravelType()==""){
            errors.put("wishTravelType","동행인을 선택해주세요.");
        }

        //검증에 실패하면 다시 입력 폼으로
        if(!errors.isEmpty()){
            model.addAttribute("errors",errors);
            return "basic/editWish";
        }

        //성공 로직
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

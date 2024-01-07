package gbsw.cb.RichKorea.controller;

import gbsw.cb.RichKorea.entity.Pop;
import gbsw.cb.RichKorea.service.RichKoreaService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class RichKoreaController {

    @Autowired
    private RichKoreaService service;


    @GetMapping("/main")
    public String home(
            Model model,
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        List<Pop> popList = service.RankList();
        model.addAttribute("rankList", popList);

        service.newPopCookie("a", response);

        String check = "0";
        Cookie[] list = request.getCookies();
        if (list != null) {
            for (Pop pop : popList) {
                for(Cookie cookie : list) {
                    if (cookie.getName().equals(pop.getArea_a())){
                        check = "1";
                    }
                }
                if (check.equals("0")) {
                    service.newPopCookie(pop.getArea_a(), response);
                }
                check = "0";
            }
        }else{
            for (Pop pop : popList) {
                service.newPopCookie(pop.getArea_a(), response);
            }
        }

        return "main";
    }
    // score
    @GetMapping("/score/{area}")
    public String score(
            HttpServletResponse response,
            HttpServletRequest request,
            @PathVariable String area,
            Model model
    ) {
        String CookieCheck = "0";
        Pop pop = service.getPopAreaInformation(area);

        Cookie[] list = request.getCookies();
        if (list != null) {
            for (Cookie cookie : list) {
                if (cookie.getName().equals(area)) {
                    model.addAttribute("areaName", pop.getArea());
                    model.addAttribute("area", pop.getArea_a());
                    model.addAttribute("score", cookie.getValue());
                    CookieCheck = "1";
                }
            }
            if (CookieCheck.equals("0")){
                service.newPopCookie(area, response);
                model.addAttribute("area", area);
                model.addAttribute("score", "새로고침을 해주세요.");
                CookieCheck = "0";
            }
        }else {
            service.newPopCookie(area, response);
            model.addAttribute("area", area);
            model.addAttribute("score", "새로고침을 해주세요.");
        }
        return "score";
    }

    //pop calculate
    @GetMapping("/plus/{area}")
    public String pop(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable String area
    )  {
        Cookie[] list = request.getCookies();
        for(Cookie cookie : list) {
            if(cookie.getName().equals(area)) {
                service.plusPopCookie(cookie, response);
            }
        }
        service.plusPopData(area);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
//        return "pop";
    }

}

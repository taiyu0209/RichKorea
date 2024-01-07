package gbsw.cb.RichKorea.service;

import gbsw.cb.RichKorea.entity.Pop;
import gbsw.cb.RichKorea.repository.PopRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RichKoreaService {

    private final PopRepository popRepository;

    public RichKoreaService(PopRepository popRepository) {
        this.popRepository = popRepository;
    }

    public void plusPopCookie(Cookie cookie, HttpServletResponse response) {
        Integer popCookieValue = null;
        popCookieValue = Integer.parseInt(cookie.getValue());
        cookie.setValue(null);
        cookie.setValue(String.valueOf(popCookieValue + 1));
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    //쿠키생성
    public void newPopCookie(String area,HttpServletResponse response) {
        Cookie popCookie = new Cookie(area,null);
        popCookie.setValue("0");
        popCookie.setPath("/");
        popCookie.setMaxAge(60 * 60 * 24);
        popCookie.setHttpOnly(true);
        response.addCookie(popCookie);
    }


    public List<Pop> RankList() {

        return popRepository.findAllByOrderByHitDesc();
    }

    public void plusPopData(String area) {
        Pop pop = popRepository.findById(area).get();
//        System.out.println(pop);
        Integer count = null;
        count = pop.getHit()+1;
//        System.out.println(count);
        pop.setHit(count);
    }

    public Pop getPopAreaInformation(String area) {
        return popRepository.findById(area).get();
    }
}

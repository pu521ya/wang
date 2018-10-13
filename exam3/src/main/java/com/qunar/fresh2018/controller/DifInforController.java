package com.qunar.fresh2018.controller;
import com.qunar.fresh2018.model.DifInfor;
import com.qunar.fresh2018.service.DifInforService;
import com.qunar.fresh2018.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@Controller
public class DifInforController {
    @Resource
    private DifInforService diffService;
    @Resource
    private UserService userService;


    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login.do")
    public String login(String name, String password,HttpServletResponse httpServletResponse){
        ModelAndView modelAndView = new ModelAndView();
        if (userService.checkUser(name,password)){
            Cookie cookie = new Cookie("username",name);
            cookie.setMaxAge(1*60*60);
            httpServletResponse.addCookie(cookie);
        }else {

            return "login";
        }
        return "redirect:/index";
    }
    @RequestMapping("/logout")
    public String logout(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        Cookie killCookie = new Cookie("username",null);
        killCookie.setMaxAge(0);
        killCookie.setPath("/");
        response.addCookie(killCookie);
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(defaultValue = "0")int page, HttpServletRequest httpServletRequest) {
        ModelAndView modelAndView = new ModelAndView("upload");
        List<DifInfor> diffList = diffService.selectDiffForPage(page);

        modelAndView.addObject("diffs", diffList);
        modelAndView.addObject("page", page);
        int countNum = Math.min(diffService.gettDiffCount(),5);
        modelAndView.addObject("diffcount",countNum);
        if (httpServletRequest != null) {
            Cookie[] cookies = httpServletRequest.getCookies();
            if (cookies !=null){
                for (Cookie cookie:cookies){
                    if ("username".equals(cookie.getName())) {
                        modelAndView.addObject("username", cookie.getValue());
                    }
                }
            }

        }
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestParam("source") MultipartFile source,@RequestParam("target") MultipartFile target) {
        diffService.createDiffAndInsert(source, target);
        return "redirect:/index";
    }

    @RequestMapping("/delete")
    public String delete(Integer id,HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies !=null){
            for (Cookie cookie:cookies){
                if ("username".equals(cookie.getName()) && cookie.getValue()!=null) {
                    diffService.deleteDiff(id);
                }
            }
        }
        return "redirect:/index";
    }
}
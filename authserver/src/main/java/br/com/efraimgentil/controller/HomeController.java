package br.com.efraimgentil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 28/06/16.
 */
@Controller
public class HomeController {


  @RequestMapping(value = { "/" , "" } , method = RequestMethod.GET)
  public String home(Model model , Authentication authentication){
    model.addAttribute("auth" , authentication) ;
    return "home";
  }

}

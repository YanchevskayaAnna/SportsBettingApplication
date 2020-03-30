package sportsbetting.controller;

import sportsbetting.model.user.Currency;
import sportsbetting.model.user.Player;
import sportsbetting.service.IUserService;
import sportsbetting.service.IWagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("userinfo")
@Slf4j
public class UserController {

    private IUserService userService;
    private AuthenticationManager authenticationManager;
    private IWagerService wagerService;

    @Autowired
    public UserController(IUserService userService, IWagerService wagerService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.wagerService = wagerService;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    public ModelAndView userInfo(@PathVariable String userName) {
        ModelAndView modelAndView = new ModelAndView();
        Player player = (Player) userService.findUserByUserName(userName);
        modelAndView.addObject("player", player);
        modelAndView.addObject("id", userService.findUserByUserName(userName).getId());
        modelAndView.addObject("allCurrencies", Currency.values());
        modelAndView.addObject("wagers", wagerService.findAllByPlayer(player));
        modelAndView.setViewName("userinfo");
        return modelAndView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ModelAndView editUser(@PathVariable long id, @Valid Player player, BindingResult bindingResult, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("player", player);
        modelAndView.addObject("allCurrencies", Currency.values());
        modelAndView.setViewName("userinfo");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        } else {
            player.setId(id);
            String password = player.getPassword();
            userService.updateUser(player);
            authWithAuthManager(request, player.getUserName(), password);
            modelAndView.addObject("successMessage", "User has been updated successfully");
        }
        return modelAndView;
    }

    public void authWithAuthManager(HttpServletRequest request, String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

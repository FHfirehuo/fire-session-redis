package session.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

	@ResponseBody
	@RequestMapping("index")
	public String index(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("a");
		req.getSession().setAttribute("name", "fire");
		return "aa:aa";
	}
}

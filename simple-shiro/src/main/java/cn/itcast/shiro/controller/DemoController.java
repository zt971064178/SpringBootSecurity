package cn.itcast.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/shiro/")
public class DemoController {

	@RequestMapping("index")
	@ResponseBody
	public String index() {
		return "Hello Shiro !!!" ;
	}
	
	
	@RequestMapping("demo")
	@ResponseBody
	public String indexDo() {
		return "Hello Shiro Security !!!" ;
	}
	
}

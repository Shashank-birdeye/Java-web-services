package WebServices.helloWorld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
	
	@Autowired
	MessageSource messageSource;

//	@RequestMapping(method = RequestMethod.GET , path = "/hello-world" )
	@GetMapping("/hello-world")
	public String helloWorld()  {
		return "Hello World";
	}
	@GetMapping("/hello-world/kavish")
	public String helloWorldKavish()  {
		return "Hello Kavish";
	}
	
	
	@GetMapping("/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("hii");
	}
	
	@GetMapping("/hello-world-{name}")
	public HelloWorldBean pathVariable(@PathVariable String name) {
		
		return new HelloWorldBean(String.format("Hello World my friend, %s", name ));
	}
	
	
	@GetMapping("/hello-world-internationalisation")
	public String helloWorldInter(
			@RequestHeader(name= "Accept-Language", required = false) Locale localea)  {
		
//		return messageSource.getMessage("good.morning.message", null, localea);
		return messageSource.getMessage
		("good.morning.message", null, "Default Message", localea);
	}
	
}

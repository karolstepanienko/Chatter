package frontend.main;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

	@RequestMapping("/")
	public String index() {
		return "Hello world.";
	}

  @RequestMapping("/folder")
  public String folder() {
    return "Hello world!";
  }

}

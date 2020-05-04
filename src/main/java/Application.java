import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import be.iccbxl.poo.ui.IUi;
import be.iccbxl.poo.ui.UiConsole;

public class Application {

	private static IUi ui = new UiConsole();
	
	public Application() {
		
	}
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
		
		ui = (IUi) ctx.getBean("uiConsole");
		
		ui.run();

	}

}

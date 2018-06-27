# Component Recaptach Give you to create a Server validation for Google Recaptacha
Spring Boot application with less configuration 

> In this tutorial you will know clear config on captacha server configuration

### Sample Demo Screen

<img width="1440" alt="screen shot 2018-06-28 at 00 04 03" src="https://user-images.githubusercontent.com/26504978/42002758-ad691804-7a68-11e8-94fe-81c20c9441df.png">

### Project Structure

### Let’s Setup Environment

1. Maven 3 and any latest version
2. JDK 1.6 / JDK 1.7 / JDK 1.8 / JDK 1.9
3. Eclipse Kepler / Eclipse Juno / Eclipse Neon


### 2. Extract that project and import in eclipse.

> File > Import > Existing Maven Projects > Click ``Next`` > Select project root directory > Click ``Finish``


### 3. Add Extra dependencies in ``pom.xml`` file

```XML
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-test</artifactId>
		<scope>test</scope>
	</dependency>
	
	<dependency>
		<groupId>javax.servlet</groupId>
		<artifactId>jstl</artifactId>
	</dependency>
	
	<!-- Need this to compile JSP -->
	<dependency>
		<groupId>org.apache.tomcat.embed</groupId>
		<artifactId>tomcat-embed-jasper</artifactId>
		<scope>provided</scope>
	</dependency>
</dependencies>
```


### 4. Add project configuration properties in ``src/main/resources/application.properties`` file

```
# jsp view binding configuration 
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp

# project context and port configuration
server.port=8080
server.context-path=/check/recaptacha

google.recaptcha.secret=6LfUFGEUAAAAAGVs22WCmH0jGoY__Dll4j5DErym
google.recaptcha.url=https://www.google.com/recaptcha/api/siteverify

proxy.host=10.246.1.220
proxy.port=8080
remote.ip=
```


### 5. Add Controller Class in ``src/main/java/package/CaptachaController.java`` file

```JAVA
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CaptachaController {

	@RequestMapping(value = "/", method = RequestMethod.GET )
	public String hello(Model model){
		model.addAttribute("title", "Reusable Component - Recaptacha Server Validation");
		return "recaptacha";
	}
}
```


### 6. Add JSP view in ``webapp/WEB-INF/jsp/recaptacha.jsp`` file

```JSP
JSP code
```
# That's It...you are ready to Run


> Right click to project > Run As > Spring Boot App

go to http://localhost:8080/check/recaptacha/


## Contact

Venkateshprabhu.B - tagvenkat@gmail.com


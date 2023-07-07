package pl.sknikod.kodemy


import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class JavaProBackendApplicationTests extends Specification {

	def "should load context"() {
		expect:
		true
	}
}
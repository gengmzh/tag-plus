package io.github.jsound.tagplus;

/**
 * 
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * the base test
 * 
 * @author gengmaozhang01
 * @since 下午7:17:22
 */
public abstract class BaseTest {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	protected void debug(String msg, Object... args) {
		log.debug(msg, args);
	}

	public static void main(String[] args) {
		BaseTest test = new BaseTest() {
		};

		test.debug("test log: {}", "slf4j with logback");
	}

}

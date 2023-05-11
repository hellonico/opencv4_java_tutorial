///usr/bin/env jbang "$0" "$@" ; exit $?

//REPOS mavencentral,acme=https://clojars.org/repo/
//DEPS ch.qos.reload4j:reload4j:1.2.19
//DEPS origami:origami:4.7.0-5

import static java.lang.System.out;
import origami.*;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

import java.util.Arrays;

class classpath_example {

	static final Logger logger = Logger.getLogger(classpath_example.class);

	public static void main(String[] args) {
		BasicConfigurator.configure(); 
		logger.info("Welcome to jbang");

		Arrays.asList(args).forEach(arg -> logger.warn("arg: " + arg));
		logger.info("Hello from Java!");
        Origami.init();
		new origami.Camera().device("0").run();
	}
}


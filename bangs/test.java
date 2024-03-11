///usr/bin/env jbang "$0" "$@" ; exit $?

//REPOS mavencentral,acme=https://clojars.org/repo/
//DEPS origami:origami:4.7.0-6

import origami.*;

class classpath_example {

	public static void main(String[] args) {
		// works on mac
        Origami.init();
		new origami.Camera().run();
	}
}


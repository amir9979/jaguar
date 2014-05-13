package br.usp.each.saeg.jaguar.runner;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

import br.usp.each.saeg.jaguar.Jaguar;
import br.usp.each.saeg.jaguar.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.heuristic.impl.TarantulaHeuristic;
import br.usp.each.saeg.jaguar.infra.FileUtils;
import br.usp.each.saeg.jaguar.jacoco.JacocoTCPClient;

/**
 * JUnit Runner for fault localization.
 * <p>
 * 
 * This runner will search recursively for JUnits tests in all classes within
 * the current directory. Then will run the tests, collecting coverage
 * information using a jacocoagent running as tcpserver. Finally, will print a
 * list of tests requirements order by suspiciousness.
 * <p>
 * 
 * It is mandatory to run using the following JM arguments:
 * -javaagent:{jacoco_agent_path}/jacocoagent.jar=output=tcpserver
 * 
 * @author Henrique Ribeiro
 * 
 */
public class JaguarSuite extends Suite {

	private JacocoTCPClient tcpClient;
	private Jaguar jaguar;
	private Heuristic heuristic;
	private File targetDir;

	/**
	 * Constructor.
	 * 
	 * @param clazz
	 *            * the class calling the runner
	 * @throws InitializationError
	 * @throws ClassNotFoundException
	 */
	public JaguarSuite(final Class<?> clazz) throws InitializationError, ClassNotFoundException {
		super(clazz, FileUtils.findTestClasses(clazz));
		heuristic = getHeuristic(clazz);
		targetDir = FileUtils.findClassDir(clazz).getParentFile();
	}

	/**
	 * Return the heuristic based on the enum passed on the annotation
	 * {@link JaguarRunnerHeuristic}. If no heuristic is found,
	 * {@link TarantulaHeuristic} is used.
	 * 
	 * @param klass
	 *            class annotated
	 * @return the heuristic
	 * @throws InitializationError
	 */
	private static Heuristic getHeuristic(Class<?> klass) {
		JaguarRunnerHeuristic annotation = klass.getAnnotation(JaguarRunnerHeuristic.class);
		try {
		    return annotation.value().newInstance();
		} catch (Exception e) {
		    return new TarantulaHeuristic();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.junit.runners.Suite#run(org.junit.runner.notification.RunNotifier)
	 */
	@Override
	public void run(final RunNotifier notifier) {
		initializeBeforeTests();

		notifier.addListener(new JaguarRunListener(jaguar, tcpClient));

		super.run(notifier);

		tearDown();
		jaguar.generateRank();
		jaguar.generateXML();
	}

	private void initializeBeforeTests() {
		jaguar = new Jaguar(heuristic, targetDir);
		try {
			tcpClient = new JacocoTCPClient();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void tearDown() {
		try {
			tcpClient.closeSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

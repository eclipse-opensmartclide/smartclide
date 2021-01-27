package de.atb.context;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;

/**
 * TestOntologyExtraction
 *
 * @author scholze
 * @version $LastChangedRevision: 577 $
 *
 */
public class TestOntologyExtraction {

	@Test
	public void shouldCopySomething() throws IOException {
		URL source = ClassLoader.getSystemClassLoader().getResource("LICENSE.txt");
		Assert.assertNotNull(source);
		File dest = new File(FileUtils.getUserDirectoryPath() + File.separator + "license-test.txt");
		dest.delete();
		FileUtils.copyURLToFile(source, dest);
		Assert.assertTrue(dest.exists());
	}

/*	@Test
	public void shouldReachInternet() throws IOException {
		InetAddress addr = InetAddress.getByName("www.atb-bremen.de");
		Assert.assertTrue(addr.isReachable(2500));
		URL url = new URL("http", addr.getHostName(), "/");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		Assert.assertNotNull(connection);
	}
*/
/*	@Test
	@Ignore
	public void shouldNotReachInternet() throws IOException {
		System.setProperty("http.proxyHost", "123.123.123.123");
		System.setProperty("http.proxyPort", "80");
		System.setProperty("http.proxySet", "true");
		// Proxy proxy = new Proxy(Proxy.Type.HTTP, new
		// InetSocketAddress("example.org", 8080));
		InetAddress addr = InetAddress.getByName("www.atb-bremen.de");
		Assert.assertTrue(addr.isReachable(2500));

		URL url = new URL("http", addr.getHostName(), "/");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(500);
		connection.connect();
		Assert.assertTrue(connection.usingProxy());
	}
*/
}

package main;

/**
 * Detection of operating system (taken from mykong website)
 * 
 * @author mykong
 * @see https://www.mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/
 */
public class OS {
	private static String OS = System.getProperty("os.name").toLowerCase();

	/**
	 * Is this a windows system?
	 * 
	 * @return
	 */
	public static boolean isWindows() throws Throwable {
		return (OS.indexOf("win") >= 0);
	}

	/**
	 * Is this an apple mac system?
	 * 
	 * @return
	 */
	public static boolean isMac() throws Throwable {
		return (OS.indexOf("mac") >= 0);
	}

	/**
	 * Is this a unix/linux system?
	 * 
	 * @return
	 */
	public static boolean isUnix() throws Throwable {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	}

	/**
	 * Is this a solaris system?
	 * 
	 * @return
	 */
	public static boolean isSolaris() throws Throwable {
		return (OS.indexOf("sunos") >= 0);
	}
}
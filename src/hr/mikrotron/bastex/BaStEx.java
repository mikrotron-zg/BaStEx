package hr.mikrotron.bastex;

/**BankStatementExtractor command line version used for text extraction testing
 * @author prexy
 * @version 0.1
 */
public class BaStEx {

	public static final boolean DEBUG=false; //needs to be false for production code !!!
	public static final double EPS=0.0001; //double comparison precision	
	public static String OWNER; //bank account owner
	
	public static void main(String[] args) {

		if (checkProperties()) new BatchExtract(System.getProperty("bspath"));

	}
	
	private static boolean checkProperties(){
		if (System.getProperty("bspath")==null || System.getProperty("company")==null){
			System.out.println("Properties not set, run with -Dbspath=[path] -Dcompany=[company name] where"
					+ " [path] and [company name] matches your data.");
			return false;
		}else{
			OWNER=System.getProperty("company");
			return true;
		}
		
	}
	
	
}

/**
 * 
 */
package wsc;

import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.ApexClass;
import com.sforce.ws.ConnectorConfig;

/**
 * @author pambure
 *
 */
public class ApexClassDemo {

	public static EnterpriseConnection connection;
	
	public static void main(String[] args) {
		ConnectorConfig config = new ConnectorConfig();
		config.setUsername(CommonUtil.USERNAME);
		config.setPassword(CommonUtil.PASSWORD+CommonUtil.SECURITY_TOKEN);
		
		try {
			connection = Connector.newConnection(config);
			System.out.println("**********CONNECTION ESTABLISHED*************");
			String classBody = "public class Messages {\n"
					   + "public string SayHello() {\n"
					   + " return 'Hello';\n" + "}\n"
					   + "}";
			ApexClass apexClass = new ApexClass();
			apexClass.setBody(classBody);
			SaveResult[] saveResults = connection.create(new ApexClass[] {apexClass});
			for(SaveResult result:saveResults){
				if(result.isSuccess()){
					System.out.println("Created Apex class.");
				}else{
					System.out.println("Errors were encountered while creating Apex Class");
					for (com.sforce.soap.enterprise.Error e : result.getErrors()) {
						System.out.println("Error message: " + e.getMessage());
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}

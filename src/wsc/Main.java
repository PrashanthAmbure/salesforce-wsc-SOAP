/**
 * 
 */
package wsc;

import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.DeleteResult;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.Error;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

/**
 * @author pambure
 *
 */
public class Main {

	public static EnterpriseConnection connection;

	public static void main(String[] args) {
		ConnectorConfig config = new ConnectorConfig();
		config.setUsername(CommonUtil.USERNAME);
		config.setPassword(CommonUtil.PASSWORD+CommonUtil.SECURITY_TOKEN);
		
		try {
			connection = Connector.newConnection(config);
			System.out.println("**********CONNECTION ESTABLISHED*************");
			
			queryAccount();
			createAccount();
			updateAccount();
			deleteAccount();
			
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	}

	public static Account queryAccount(){
		System.out.println("#### Fetching ACCOUNTS object data. #####");
		Account account=null;
		try {
			QueryResult queryResult = connection.query("select Id, Name from Account where Name='CITI'");
			if(queryResult.getSize() > 0){
				System.out.println("=======Displaying Fetched Data===========");
				for(int i=0;i<queryResult.getRecords().length;i++){
					account = (Account)queryResult.getRecords()[i];
					System.out.println(account.getName());
				}
			}else{
				System.out.println("=======Could not fetch data=======");
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		return account;
	}
	
	public static void createAccount(){
		Account account = new Account();
		account.setName("CITI");
		Account[] records = new Account[1];
		records[0] = account;
		try {
			SaveResult[] saveResults = connection.create(records);
			
			for (int i=0; i< saveResults.length; i++) {
				if (saveResults[i].isSuccess()) {
					System.out.println(i+". Successfully created record - Id: " + saveResults[i].getId());
				} else {
					Error[] errors = saveResults[i].getErrors();
			          for (int j=0; j< errors.length; j++) {
			            System.out.println("ERROR creating record: " + errors[j].getMessage());
			          }
				}
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateAccount(){
		Account account = queryAccount();
		System.out.println("=======Updating Account:>>>>>"+account.getName());
		account.setPhone("90001888885");
		Account[] records = new Account[1];
		records[0] = account;
		try {
			SaveResult[] saveResults = connection.update(records);
			
			for (int i=0; i< saveResults.length; i++) {
				if (saveResults[i].isSuccess()) {
					System.out.println(i+". Successfully updated record - Id: " + saveResults[i].getId());
				} else {
					Error[] errors = saveResults[i].getErrors();
			          for (int j=0; j< errors.length; j++) {
			            System.out.println("ERROR updating record: " + errors[j].getMessage());
			          }
				}
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteAccount(){
		Account account = queryAccount();
		System.out.println("=======Deleting Account:>>>>>"+account.getName());
		String[] records = new String[1];
		records[0] = account.getId();
		try {
			DeleteResult[] saveResults = connection.delete(records);
			
			for (int i=0; i< saveResults.length; i++) {
				if (saveResults[i].isSuccess()) {
					System.out.println(i+". Successfully deleted record - Id: " + saveResults[i].getId());
				} else {
					Error[] errors = saveResults[i].getErrors();
			          for (int j=0; j< errors.length; j++) {
			            System.out.println("ERROR deleting record: " + errors[j].getMessage());
			          }
				}
			}
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	}
}

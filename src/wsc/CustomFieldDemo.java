/**
 * 
 */
package wsc;

import com.sforce.soap.metadata.CustomField;
import com.sforce.soap.metadata.FieldType;
import com.sforce.soap.metadata.Metadata;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.metadata.SaveResult;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

/**
 * @author pambure
 *
 */
public class CustomFieldDemo {

	public static PartnerConnection partnerConnection;
	public static LoginResult partnerLoginResult;
	public static MetadataConnection metadataConnection;
	
	public static void main(String[] args) {
		ConnectorConfig config = new ConnectorConfig();
		config.setUsername(CommonUtil.USERNAME);
		config.setPassword(CommonUtil.PASSWORD+CommonUtil.SECURITY_TOKEN);
		
		try {
			config.setAuthEndpoint(CommonUtil.URL);
			config.setServiceEndpoint(CommonUtil.URL);
			partnerConnection = new PartnerConnection(config);
			partnerLoginResult = partnerConnection.login(CommonUtil.USERNAME, CommonUtil.PASSWORD+CommonUtil.SECURITY_TOKEN);
		} catch (ConnectionException e) {
			System.out.println("Partner Connection failed!");
			e.printStackTrace();
		}

		config.setServiceEndpoint(partnerLoginResult.getMetadataServerUrl());
		config.setSessionId(partnerLoginResult.getSessionId());
		try {
			metadataConnection = new MetadataConnection(config);
			CustomField customField = new CustomField();
			customField.setFullName("Account" + ".STeLA__c");
	        customField.setLabel("STeLA");
	        customField.setDescription("STeLA Desc");
	        customField.setType(FieldType.Text);
	        customField.setExternalId(true);
	        customField.setLength(18);
	        
			SaveResult []saveResults = metadataConnection.createMetadata(new Metadata[] {customField});
			for(SaveResult result:saveResults){
				if(result.isSuccess()){
					System.out.println("Created customField__c field in object Account.");
				}else{
					System.out.println("Errors were encountered while creating "+ result.getFullName());
					for (com.sforce.soap.metadata.Error e : result.getErrors()) {
						System.out.println("Error message: " + e.getMessage());
						System.out.println("Status code: " + e.getStatusCode());
					}
				}
			}
		} catch (ConnectionException e) {
			System.out.println("Metadata Connection failed!");
			e.printStackTrace();
		}
	}

}

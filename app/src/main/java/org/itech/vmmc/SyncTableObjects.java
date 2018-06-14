package org.itech.vmmc;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import static org.itech.vmmc.DatabaseNames.*;

/**
 * Created by rossumg on 3/14/2018.
 */

public class SyncTableObjects {

    public JSONObject personTableInfo = new JSONObject();
    public JSONObject userTableInfo = new JSONObject();
    public JSONObject userTypeTableInfo = new JSONObject();
    public JSONObject userToAclTableInfo = new JSONObject();
    public JSONObject aclTableInfo = new JSONObject();
    public JSONObject clientTableInfo = new JSONObject();
    public JSONObject facilitatorTableInfo = new JSONObject();
    public JSONObject locationTableInfo = new JSONObject();
    public JSONObject addressTableInfo = new JSONObject();
    public JSONObject regionTableInfo = new JSONObject();
    public JSONObject constituencyTableInfo = new JSONObject();
    public JSONObject bookingTableInfo = new JSONObject();
    public JSONObject interactionTableInfo = new JSONObject();
    public JSONObject geolocationTableInfo = new JSONObject();
    public JSONObject facilitatorTypeTableInfo = new JSONObject();
    public JSONObject interactionTypeTableInfo = new JSONObject();
    public JSONObject statusTypeTableInfo = new JSONObject();
    public JSONObject institutionTableInfo = new JSONObject();
    public JSONObject groupActivityTableInfo = new JSONObject();
    public JSONObject groupTypeTableInfo = new JSONObject();
    public JSONObject followupTableInfo = new JSONObject();
    public JSONObject procedureTypeTableInfo = new JSONObject();
    public JSONObject syncAuditTableInfo = new JSONObject();

    public SyncTableObjects(){
        makeTableJSONObjects();
    }

    private void makeTableJSONObjects() {
        try {

            JSONArray syncAuditTableFields = new JSONArray("['"
                    + SYNC_AUDIT_ID + "','"
                    + SYNC_AUDIT_TIMESTAMP + "','"
                    + SYNC_AUDIT_LATITUDE + "','"
                    + SYNC_AUDIT_LONGITUDE + "','"
                    + SYNC_AUDIT_DEVICE_ID + "','"
                    + SYNC_AUDIT_USERNAME + "','"
                    + SYNC_AUDIT_PASSWORD + "','"
                    + SYNC_AUDIT_PROGRESS + "','"
                    + SYNC_AUDIT_STATUS
                    + "']"
            );

            JSONArray personTableFields = new JSONArray("['"
                    + PERSON_ID + "','"
                    + PERSON_TIMESTAMP + "','"
                    + PERSON_FIRST_NAME + "','"
                    + PERSON_LAST_NAME + "','"
                    + PERSON_NATIONAL_ID + "','"
                    + PERSON_ADDRESS + "','"
                    + PERSON_PHONE + "','"
                    + PERSON_DOB + "','"
                    + PERSON_GENDER + "','"
                    + PERSON_LATITUDE + "','"
                    + PERSON_LONGITUDE + "','"
                    + PERSON_IS_DELETED
                    + "']"
            );

            JSONArray userTableFields = new JSONArray("['"
                    + USER_ID + "','"
                    + USER_TIMESTAMP + "','"
                    + USER_USERNAME + "','"
                    + USER_PASSWORD + "','"
                    + USER_EMAIL + "','"
                    + USER_FIRST_NAME + "','"
                    + USER_LAST_NAME + "','"
                    + USER_NATIONAL_ID + "','"
                    + USER_PHONE + "','"
                    + USER_REGION_ID + "','"
                    + USER_USER_TYPE_ID + "','"
                    + USER_LOCATION_ID + "','"
                    + USER_MODIFIED_BY + "','"
                    + USER_CREATED_BY + "','"
                    + USER_IS_BLOCKED + "','"
                    + USER_TIMESTAMP_UPDATED + "','"
                    + USER_TIMESTAMP_CREATED + "','"
                    + USER_TIMESTAMP_LAST_LOGIN
                    + "']");

            JSONArray userTypeTableFields = new JSONArray("['"
                    + USER_TYPE_ID + "','"
                    + USER_TYPE_NAME
                    + "']");

            JSONArray userToAclTableFields = new JSONArray("['"
                    + USER_TO_ACL_ID + "','"
                    + USER_TO_ACL_TIMESTAMP_CREATED + "','"
                    + USER_TO_ACL_ACL_ID + "','"
                    + USER_TO_ACL_USER_ID + "','"
                    + USER_TO_ACL_CREATED_BY
                    + "']");

            JSONArray aclTableFields = new JSONArray("['"
                    + ACL_ID + "','"
                    + ACL_ACL
                    + "']");

            JSONArray clientTableFields = new JSONArray("['"
                    + CLIENT_ID + "','"
                    + CLIENT_TIMESTAMP + "','"
                    + CLIENT_FIRST_NAME + "','"
                    + CLIENT_LAST_NAME + "','"
                    + CLIENT_NATIONAL_ID + "','"
                    + CLIENT_PHONE + "','"
                    + CLIENT_STATUS_ID + "','"
                    + CLIENT_LOC_ID + "','"
                    + CLIENT_LATITUDE + "','"
                    + CLIENT_LONGITUDE + "','"
                    + CLIENT_INSTITUTION_ID + "','"
                    + CLIENT_GROUP_ACTIVITY_NAME + "','"
                    + CLIENT_GROUP_ACTIVITY_DATE + "','"
                    + CLIENT_FAC_FIRST_NAME + "','"
                    + CLIENT_FAC_LAST_NAME + "','"
                    + CLIENT_FAC_NATIONAL_ID + "','"
                    + CLIENT_FAC_PHONE + "','"
                    + CLIENT_ADDRESS_ID + "','"
                    + CLIENT_DOB + "','"
                    + CLIENT_GENDER
                    + "']");

            JSONArray facilitatorTableFields = new JSONArray("['"
                    + FACILITATOR_ID + "','"
                    + FACILITATOR_TIMESTAMP + "','"
                    + FACILITATOR_FIRST_NAME + "','"
                    + FACILITATOR_LAST_NAME + "','"
                    + FACILITATOR_NATIONAL_ID + "','"
                    + FACILITATOR_PHONE + "','"
                    + FACILITATOR_FACILITATOR_TYPE_ID + "','"
                    + FACILITATOR_NOTE + "','"
                    + FACILITATOR_LOCATION_ID + "','"
                    + FACILITATOR_LATITUDE + "','"
                    + FACILITATOR_LONGITUDE + "','"
                    + FACILITATOR_INSTITUTION_ID + "','"
                    + FACILITATOR_ADDRESS_ID + "','"
                    + FACILITATOR_DOB + "','"
                    + FACILITATOR_GENDER
                    + "']");

            JSONArray locationTableFields = new JSONArray("['"
                    + LOCATION_ID + "','"
                    + LOCATION_NAME + "','"
                    + LOCATION_REGION_ID
                    + "']");

            JSONArray addressTableFields = new JSONArray("['"
                    + ADDRESS_ID + "','"
                    + ADDRESS_NAME + "','"
                    + ADDRESS_REGION_ID
                    + "']");

            JSONArray regionTableFields = new JSONArray("['"
                    + REGION_ID + "','"
                    + REGION_NAME
                    + "']");

            JSONArray constituencyTableFields = new JSONArray("['"
                    + CONSTITUENCY_ID + "','"
                    + CONSTITUENCY_NAME + "','"
                    + CONSTITUENCY_REGION_ID
                    + "']");

            JSONArray bookingTableFields = new JSONArray("['"
                    + BOOKING_ID + "','"
                    + BOOKING_TIMESTAMP + "','"
                    + BOOKING_FIRST_NAME + "','"
                    + BOOKING_LAST_NAME + "','"
                    + BOOKING_NATIONAL_ID + "','"
                    + BOOKING_PHONE + "','"
                    + BOOKING_LOCATION_ID + "','"
                    + BOOKING_LATITUDE + "','"
                    + BOOKING_LONGITUDE + "','"
                    + BOOKING_PROJECTED_DATE + "','"
                    + BOOKING_ACTUAL_DATE + "','"
                    + BOOKING_CONSENT + "','"
                    + BOOKING_PROCEDURE_TYPE_ID + "','"
                    + BOOKING_FOLLOWUP_ID + "','"
                    + BOOKING_FOLLOWUP_DATE + "','"
                    + BOOKING_ALT_CONTACT
                    + "']");

            JSONArray interactionTableFields = new JSONArray("['"
                    + INTERACTION_ID + "','"
                    + INTERACTION_TIMESTAMP + "','"
                    + INTERACTION_FAC_FIRST_NAME + "','"
                    + INTERACTION_FAC_LAST_NAME + "','"
                    + INTERACTION_FAC_NATIONAL_ID + "','"
                    + INTERACTION_FAC_PHONE + "','"
                    + INTERACTION_PERSON_FIRST_NAME + "','"
                    + INTERACTION_PERSON_LAST_NAME + "','"
                    + INTERACTION_PERSON_NATIONAL_ID + "','"
                    + INTERACTION_PERSON_PHONE + "','"
                    + INTERACTION_DATE + "','"
                    + INTERACTION_FOLLOWUP_DATE + "','"
                    + INTERACTION_TYPE + "','"
                    + INTERACTION_NOTE
                    + "']");

            JSONArray geolocationTableFields = new JSONArray("['"
                    + GEOLOCATION_ID + "','"
                    + GEOLOCATION_LAT + "','"
                    + GEOLOCATION_LONG + "','"
                    + GEOLOCATION_DEVICE_ID + "','"
                    + GEOLOCATION_TIMESTAMP + "','"
                    + GEOLOCATION_USERNAME + "','"
                    + GEOLOCATION_PASSWORD
                    + "']");

            JSONArray facilitatorTypeTableFields = new JSONArray("['"
                    + FACILITATOR_TYPE_ID + "','"
                    + FACILITATOR_TYPE_NAME
                    + "']");

            JSONArray procedureTypeTableFields = new JSONArray("['"
                    + PROCEDURE_TYPE_ID + "','"
                    + PROCEDURE_TYPE_NAME
                    + "']");

            JSONArray followupTableFields = new JSONArray("['"
                    + FOLLOWUP_ID + "','"
                    + FOLLOWUP_NAME
                    + "']");

            JSONArray interactionTypeTableFields = new JSONArray("['"
                    + INTERACTION_TYPE_ID + "','"
                    + INTERACTION_TYPE_NAME
                    + "']");

            JSONArray statusTypeTableFields = new JSONArray("['"
                    + STATUS_TYPE_ID + "','"
                    + STATUS_TYPE_NAME
                    + "']");

            JSONArray institutionTableFields = new JSONArray("['"
                    + INSTITUTION_ID + "','"
                    + INSTITUTION_NAME + "','"
                    + INSTITUTION_REGION_ID
                    + "']");

            JSONArray groupActivityTableFields = new JSONArray("['"
                    + GROUP_ACTIVITY_ID + "','"
                    + GROUP_ACTIVITY_NAME + "','"
                    + GROUP_ACTIVITY_TIMESTAMP + "','"
                    + GROUP_ACTIVITY_LOCATION_ID + "','"
                    + GROUP_ACTIVITY_ACTIVITY_DATE + "','"
                    + GROUP_ACTIVITY_GROUP_TYPE_ID + "','"
                    + GROUP_ACTIVITY_INSTITUTION_ID + "','"
                    + GROUP_ACTIVITY_MALES + "','"
                    + GROUP_ACTIVITY_FEMALES + "','"
                    + GROUP_ACTIVITY_MESSAGES + "','"
                    + GROUP_ACTIVITY_LATITUDE + "','"
                    + GROUP_ACTIVITY_LONGITUDE
                    + "']");

            JSONArray groupTypeTableFields = new JSONArray("['"
                    + GROUP_TYPE_ID + "','"
                    + GROUP_TYPE_NAME
                    + "']");

            personTableInfo.put("dataTable", TABLE_PERSON);
            userTableInfo.put("dataTable", TABLE_USER);
            userTypeTableInfo.put("dataTable", TABLE_USER_TYPE);
            userToAclTableInfo.put("dataTable", TABLE_USER_TO_ACL);
            aclTableInfo.put("dataTable", TABLE_ACL);
            clientTableInfo.put("dataTable", TABLE_CLIENT);
            facilitatorTableInfo.put("dataTable", TABLE_FACILITATOR);
            locationTableInfo.put("dataTable", TABLE_LOCATION);
            addressTableInfo.put("dataTable", TABLE_ADDRESS);
            regionTableInfo.put("dataTable", TABLE_REGION);
            constituencyTableInfo.put("dataTable", TABLE_CONSTITUENCY);
            bookingTableInfo.put("dataTable", TABLE_BOOKING);
            interactionTableInfo.put("dataTable", TABLE_INTERACTION);
            geolocationTableInfo.put("dataTable", TABLE_GEOLOCATION);
            facilitatorTypeTableInfo.put("dataTable", TABLE_FACILITATOR_TYPE);
            procedureTypeTableInfo.put("dataTable", TABLE_PROCEDURE_TYPE);
            followupTableInfo.put("dataTable", TABLE_FOLLOWUP);
            interactionTypeTableInfo.put("dataTable", TABLE_INTERACTION_TYPE);
            statusTypeTableInfo.put("dataTable", TABLE_STATUS_TYPE);
            institutionTableInfo.put("dataTable", TABLE_INSTITUTION);
            groupActivityTableInfo.put("dataTable", TABLE_GROUP_ACTIVITY);
            groupTypeTableInfo.put("dataTable", TABLE_GROUP_TYPE);
            syncAuditTableInfo.put("dataTable", TABLE_SYNC_AUDIT);

            personTableInfo.put("fields", personTableFields);
            userTableInfo.put("fields", userTableFields);
            userTypeTableInfo.put("fields", userTypeTableFields);
            userToAclTableInfo.put("fields", userToAclTableFields);
            aclTableInfo.put("fields", aclTableFields);
            clientTableInfo.put("fields", clientTableFields);
            facilitatorTableInfo.put("fields", facilitatorTableFields);
            locationTableInfo.put("fields", locationTableFields);
            addressTableInfo.put("fields", addressTableFields);
            regionTableInfo.put("fields", regionTableFields);
            constituencyTableInfo.put("fields", constituencyTableFields);
            bookingTableInfo.put("fields", bookingTableFields);
            interactionTableInfo.put("fields", interactionTableFields);
            geolocationTableInfo.put("fields", geolocationTableFields);
            facilitatorTypeTableInfo.put("fields", facilitatorTypeTableFields);
            procedureTypeTableInfo.put("fields", procedureTypeTableFields);
            followupTableInfo.put("fields", followupTableFields);
            interactionTypeTableInfo.put("fields", interactionTypeTableFields);
            statusTypeTableInfo.put("fields", statusTypeTableFields);
            institutionTableInfo.put("fields", institutionTableFields);
            groupActivityTableInfo.put("fields", groupActivityTableFields);
            groupTypeTableInfo.put("fields", groupTypeTableFields);
            syncAuditTableInfo.put("fields", syncAuditTableFields);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

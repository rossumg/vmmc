package org.itech.vmmc;

/**
 * Created by rossumg on 4/11/2017.
 */

public class IndexParts {
    private String _index;
    private String parts[] = {};
    private String _name = "";
    private String firstName = "";
    private String lastName = "";
    private String nationalId = "";
    private String phoneNumber = "";
    private String projectedDate = "";

    public IndexParts( String sIndex ) {
        parts = sIndex.split(":");
        switch( parts.length)  {
            case 0: {
                // add
                break;
            }
            case 1: {
                _name = parts[0];
                break;
            }
            case 2: {
                _name = parts[0];
                nationalId = parts[1];
                break;
            }
            case 3: {
                _name = parts[0];
                nationalId = parts[1];
                phoneNumber = parts[2];
                break;
            }
            case 4: {
                _name = parts[0];
                nationalId = parts[1];
                phoneNumber = parts[2];
                projectedDate = parts[3];
                break;
            }
        }

        String nameParts[] = {};
        nameParts = _name.split(" ");
        switch( nameParts.length )  {
            case 0: {
                break;
            }
            case 1: {
                firstName = nameParts[0];
                break;
            }
            case 2: {
                firstName = nameParts[0];
                lastName = nameParts[1];
                break;
            }
        }
    }

    public String get_first_name () {
        return firstName.toString();
    }
    public String get_last_name () {
        return lastName;
    }
    public String get_national_id () {
        return nationalId;
    }
    public String get_phone () {
        return phoneNumber;
    }
    public String get_projected_date () {
        return projectedDate;
    }
}
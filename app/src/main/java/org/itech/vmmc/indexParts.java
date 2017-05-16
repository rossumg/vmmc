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

    public IndexParts() {}

    public IndexParts(DisplayParts displayParts) {
        firstName = displayParts.get_facFirstName();
        lastName = displayParts.get_facLastName();
        nationalId = displayParts.get_facNationalId();
        phoneNumber = displayParts.get_facPhone();
    }

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
                nationalId = parts[1].toString();
                break;
            }
            case 3: {
                _name = parts[0];
                nationalId = parts[1].toString();
                phoneNumber = parts[2].toString();
                break;
            }
            case 4: {
                _name = parts[0];
                nationalId = parts[1].toString();
                phoneNumber = parts[2].toString();
                projectedDate = parts[3].toString();
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
                firstName = nameParts[0].toString();
                lastName = nameParts[1].toString();
                break;
            }
        }
    }

    public String get_first_name () {
        return firstName.toString();
    }
    public String get_last_name () {
        return lastName.toString();
    }
    public String get_national_id () {
        return nationalId.toString();
    }
    public String get_phone () {
        return phoneNumber.toString();
    }
    public String get_projected_date () {
        return projectedDate.toString();
    }
}
package org.itech.vmmc;

/**
 * Created by rossumg on 4/11/2017.
 */

public class DisplayParts {
    private String _index;
    private String parts[] = {};
    private String name = "";
    private String firstName = "";
    private String lastName = "";
    private String nationalId = "";
    private String phoneNumber = "";

    private String facName = "";
    private String facFirstName = "";
    private String facLastName = "";
    private String facNationalId = "";
    private String facPhoneNumber = "";
    private String personName = "";
    private String personFirstName = "";
    private String personLastName = "";
    private String personNationalId = "";
    private String personPhoneNumber = "";
    private String interactionDate = "";
    private String followupDate = "";

    private String projectedDate = "";

    public DisplayParts(IndexParts indexParts) {
        firstName = indexParts.get_first_name();
        lastName = indexParts.get_last_name();
        nationalId = indexParts.get_national_id();
        phoneNumber = indexParts.get_phone();
    }

    public DisplayParts(String sIndex ) {
        parts = sIndex.split(", ");
        switch( parts.length)  {
            case 0: {
                // add
                break;
            }
            case 1: {
                name = parts[0];
                break;
            }
            case 2: {
                name = parts[0].trim();
                nationalId = parts[1];
                break;
            }
            case 3: {
                name = parts[0].trim();
                nationalId = parts[1];
                phoneNumber = parts[2].trim();
                break;
            }
            case 4: {
                name = parts[0];
                nationalId = parts[1];
                phoneNumber = parts[2];
                projectedDate = parts[3];
                break;
            }
            case 7: { //interaction
                facName = parts[0];
                facNationalId = parts[1];
                facPhoneNumber = parts[2];
                personName = parts[3];
                personNationalId = parts[4];
                personPhoneNumber = parts[5];
                interactionDate = parts[6];
//                followupDate = parts[7];
                break;
            }
            case 8: { //interaction
                facName = parts[0];
                facNationalId = parts[1];
                facPhoneNumber = parts[2];
                personName = parts[3];
                personNationalId = parts[4];
                personPhoneNumber = parts[5];
                interactionDate = parts[6];
                followupDate = parts[7];
                break;
            }
        }

        String nameParts[] = {};
        nameParts = name.split(" ");
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

        String facNameParts[] = {};
        facNameParts = facName.split(" ");
        switch( facNameParts.length )  {
            case 0: {
                break;
            }
            case 1: {
                facFirstName = facNameParts[0];
                break;
            }
            case 2: {
                facFirstName = facNameParts[0];
                facLastName = facNameParts[1];
                break;
            }
        }

        String personNameParts[] = {};
        personNameParts = personName.split(" ");
        switch( personNameParts.length )  {
            case 0: {
                break;
            }
            case 1: {
                personFirstName = personNameParts[0];
                break;
            }
            case 2: {
                personFirstName = personNameParts[0];
                personLastName = personNameParts[1];
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

    public String get_facFirstName () {
        return facFirstName.toString();
    }
    public String get_facLastName () {
        return facLastName;
    }
    public String get_facNationalId () {
        return facNationalId;
    }
    public String get_facPhone () {
        return facPhoneNumber;
    }

    public String get_personFirstName () {
        return personFirstName.toString();
    }
    public String get_personLastName () {
        return personLastName;
    }
    public String get_personNationalId () {
        return personNationalId;
    }
    public String get_personPhone () {
        return personPhoneNumber;
    }

    public String get_projected_date () {
        return projectedDate;
    }

    public String get_interactionDate () {
        return interactionDate;
    }

    public String get_followupDate () {
        return followupDate;
    }
}
package Dataverse.DataverseJSONFieldClasses.Fields.CitationCompoundFields;

import Dataverse.DataverseJSONFieldClasses.CompoundJSONField;
import org.json.JSONObject;

import static _Strings.DVFieldNameStrings.*;

public class Contributor extends CompoundJSONField {
    private String contributorType, contributorName;

    public Contributor() {
        this.contributorType = "";
        this.contributorName = "";
    }

    public String getContributorType() {
        return contributorType;
    }

    public void setContributorType(String contributorType) {
        this.contributorType = contributorType;
    }

    public String getContributorName() {
        return contributorName;
    }

    public void setContributorName(String contributorName) {
        this.contributorName = contributorName;
    }

    @Override
    public void setField(JSONObject field) {
        String title = field.getString(TYPE_NAME);
        String value = field.getString(VAL);
        switch(title){
            case CONTRIB_TYPE:
                setContributorType(value);
                break;
            case CONTRIB_NAME:
                setContributorName(value);
                break;
            default:
                errorParsing(this.getClass().getName(),title);
        }
    }

    @Override
    public String getField(String title) {
        switch(title){
            case CONTRIB_TYPE:
                return getContributorType();
            case CONTRIB_NAME:
                return getContributorName();
            default:
                errorGettingValue(this.getClass().getName(),title);
                return "Bad field Name";
        }
    }
}

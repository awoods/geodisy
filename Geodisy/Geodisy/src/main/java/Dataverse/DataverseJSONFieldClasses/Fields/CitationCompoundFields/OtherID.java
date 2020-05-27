package Dataverse.DataverseJSONFieldClasses.Fields.CitationCompoundFields;

import Dataverse.DataverseJSONFieldClasses.CompoundJSONField;
import org.json.JSONObject;

import static _Strings.DVFieldNameStrings.*;

public class OtherID extends CompoundJSONField {
    private String otherIdAgency, otherIdValue;

    public OtherID() {
        this.otherIdAgency = "";
        this.otherIdValue = "";
    }

    public String getOtherIdAgency() {
        return otherIdAgency;
    }

    public void setOtherIdAgency(String otherIdAgency) {
        this.otherIdAgency = otherIdAgency;
    }

    public String getOtherIdValue() {
        return otherIdValue;
    }

    public void setOtherIdValue(String otherIdValue) {
        this.otherIdValue = otherIdValue;
    }


    @Override
    public void setField(JSONObject field) {
        String title = field.getString(TYPE_NAME);
        String value = field.getString(VAL);
        switch (title) {
            case OTHER_ID_AGENCY:
                setOtherIdAgency(value);
                break;
            case OTHER_ID_VAL:
                setOtherIdValue(value);
                break;
            default:
                errorParsing(this.getClass().getName(),title);
        }
    }

    @Override
    public String getField(String title) {
        switch (title) {
            case OTHER_ID_AGENCY:
                return getOtherIdAgency();
            case OTHER_ID_VAL:
                return getOtherIdValue();
            default:
                errorGettingValue(this.getClass().getName(),title);
                return "Bad Field name";
        }
    }
}

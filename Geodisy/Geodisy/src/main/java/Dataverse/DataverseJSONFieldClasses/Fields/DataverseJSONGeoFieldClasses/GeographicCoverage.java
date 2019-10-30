package Dataverse.DataverseJSONFieldClasses.Fields.DataverseJSONGeoFieldClasses;

import Dataverse.DataverseJSONFieldClasses.CompoundJSONField;
import Dataverse.FindingBoundingBoxes.Countries;
import Dataverse.FindingBoundingBoxes.LocationTypes.BoundingBox;
import Dataverse.FindingBoundingBoxes.LocationTypes.City;
import Dataverse.FindingBoundingBoxes.LocationTypes.Country;
import Dataverse.FindingBoundingBoxes.LocationTypes.Province;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static Dataverse.DVFieldNameStrings.*;

public class GeographicCoverage extends CompoundJSONField {
    private String givenCountry, givenProvince, givenCity, otherGeographicCoverage, doi, commonCountry, commonProvince, commonCity;
    private Country countryObject;
    private Province provinceObject;
    private City cityObject;


    public GeographicCoverage(String doi) {
        this.doi = doi;
        this.givenCountry = "";
        this.givenProvince = "";
        this.givenCity = "";
        this.otherGeographicCoverage = "";
        this.commonCountry = "";
        this.commonProvince = "";
        this.commonCity = "";
        this.countryObject = new Country();

    }

    public Country getCountryObject(){
        return countryObject;
    }

    public Province getProvinceObject(){
        return provinceObject;
    }

    public City getCityObject(){
        return cityObject;
    }
    private List<String> getGeoCoverageField(String name, String altName){
        String[] array;
        if(name.equalsIgnoreCase(altName))
            array = new String[]{name};
        else
            array = new String[]{name,altName};
        List answer = Arrays.asList(array);
        return answer;
    }
    //TODO remove once givenCountry and commonCountry implemented
    public List<String> getCountryList() {
        return (givenCountry.isEmpty() ? new LinkedList<String>() : getGeoCoverageField(givenCountry, commonCountry));
    }

    public void setGivenCountry(String givenCountry) {
        this.givenCountry = givenCountry;
        countryObject = Countries.getCountry().getCountryByName(givenCountry);
        commonCountry = countryObject.getGivenName();
    }
    //TODO remove once givenProvince and commonProvince implemented
    public List<String> getProvinceList() {
        return (givenProvince.isEmpty() ? new LinkedList<String>() : getGeoCoverageField(givenProvince, commonProvince));
    }

    public void setGivenProvince(String givenProvince) {
        this.givenProvince = givenProvince;
        provinceObject =  new Province(this.givenProvince, givenCountry);
        commonProvince = provinceObject.getGivenName();
    }
    //TODO remove once givenCity and commonCity implemented
    public List<String> getCityList() {
        return (givenCity.isEmpty() ? new LinkedList<String>() : getGeoCoverageField(givenCity, commonCity));
    }

    public void setGivenCity(String givenCity) {
        this.givenCity = givenCity;
        cityObject = new City(this.givenCity, givenProvince, givenCountry);
        commonCity = cityObject.getGivenName();
    }
    //TODO remove once otherGeographicCoverage implemented
    public String getOtherGeographicCoverage() {
        return otherGeographicCoverage;
    }

    public void setOtherGeographicCoverage(String otherGeographicCoverage) {
        this.otherGeographicCoverage = otherGeographicCoverage;
    }


    @Override
    public void setField(JSONObject field) {
        for(String s:field.keySet()){
            JSONObject fieldTitle = (JSONObject) field.get(s);
            String title = fieldTitle.getString(TYPE_NAME);
            String value = fieldTitle.getString(VAL);
            switch (title) {
                case COUNTRY:
                    setGivenCountry(value);
                    break;
                case PROVINCE:
                    setGivenProvince(value);
                    break;
                case CITY:
                    setGivenCity(value);
                    break;
                case OTHER_GEO_COV:
                    setOtherGeographicCoverage(value);
                    break;
                default:
                    errorParsing(this.getClass().getName(), title);
            }
        }
    }

    @Override
    public String getField(String fieldName) {
        switch (fieldName) {
            case COUNTRY:
                return givenCountry;
            case PROVINCE:
                return givenProvince;
            case CITY:
                return givenCity;
            case OTHER_GEO_COV:
                return getOtherGeographicCoverage();
            case COMMON_COUNTRY:
                return (commonCountry.isEmpty()? givenCountry:commonCountry);
            case COMMON_PROVINCE:
                return (commonProvince.isEmpty()? givenProvince:commonProvince);
            case COMMON_CITY:
                return (commonCity.isEmpty()? givenCity:commonCity);
            default:
                errorParsing(this.getClass().getName(), fieldName);
                return "Bad FieldName";
        }
    }
    //TODO remove when non-list version completely implemented
    public List<String> getListField(String fieldName) {
        switch (fieldName) {
            case COUNTRY:
                return getCountryList();
            case PROVINCE:
                return getProvinceList();
            case CITY:
                return getCityList();
            //case OTHER_GEO_COV:
                //return getOtherGeographicCoverage();
            default:
                errorParsing(this.getClass().getName(), fieldName);
                return Arrays.asList("Bad FieldName");
        }
    }

    public BoundingBox getBoundingBox(){
        if(this.cityObject!=null){
            if(cityObject.hasBoundingBox())
                return cityObject.getBoundingBox();
        }else if(provinceObject!=null){
            if(provinceObject.hasBoundingBox())
                return provinceObject.getBoundingBox();
        }else if(countryObject!=null){
            if(countryObject.hasBoundingBox())
                return countryObject.getBoundingBox();
        }
        return new BoundingBox();
    }
}

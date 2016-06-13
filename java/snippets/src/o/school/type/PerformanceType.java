package o.school.type;

public enum PerformanceType {
    A("Outstanding"), 
    B("Good"), 
    C("Approved"), 
    F("Fail");


    PerformanceType(String description) {
        this._description = description;
    }

    private String _description;

}

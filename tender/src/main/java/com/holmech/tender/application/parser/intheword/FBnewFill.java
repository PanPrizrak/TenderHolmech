package com.holmech.tender.application.parser.intheword;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class FBnewFill {

    private String numberM;
    private String nameA;
    private String textM;
    private String signature;
    private String worker;

    public Map<String,Object> FBnewFilltoMap(){
        Map<String,Object> bufMap = new HashMap<>();
        bufMap.put("numberM",this.numberM);
        bufMap.put("nameA",this.nameA);
        bufMap.put("textM",this.textM);
        bufMap.put("signature",this.signature);
        bufMap.put("worker",this.worker);
        return bufMap;
    }

}

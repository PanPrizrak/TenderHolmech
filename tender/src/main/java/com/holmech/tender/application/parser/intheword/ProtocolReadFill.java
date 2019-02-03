package com.holmech.tender.application.parser.intheword;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class ProtocolReadFill {

    private String order;
    private String tender;
    private String dateAndNameTender;
    private String membersCommission;
    private String theChairman;
    private String presentMembersCommission;
    private String decided;

    public Map<String,Object> LetterheadFillToMap(){
        Map<String,Object> bufMap = new HashMap<>();
        bufMap.put("order",getOrder());
        bufMap.put("nameA",this.nameA);
        bufMap.put("textM",this.textM);
        bufMap.put("signature",this.signature);
        bufMap.put("worker",this.worker);
        return bufMap;
    }

}

package com.holmech.tender.application.parser.intheword;

import com.holmech.tender.application.entity.Subject;
import lombok.Builder;
import lombok.Data;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.HashMap;
import java.util.List;
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
    private TableModel tableModelProtocolRead;
    private String declaredInvalid;
    private String secretary;


    public Map<String,Object> protocolReadFillToMapForPR(){
        Map<String,Object> bufMap = new HashMap<>();
        bufMap.put("order",getOrder());
        bufMap.put("tender",getTender());
        bufMap.put("DateandNameTender",getDateAndNameTender());
        bufMap.put("memberscommission",getMembersCommission());
        bufMap.put("thechairman", getTheChairman());
        bufMap.put("presentmemberscommission", getPresentMembersCommission());
        bufMap.put("decided", getDecided());
        return bufMap;
    }

    public Map<String,Object> protocolReadFillToMapForPRSecondPage(){
        Map<String,Object> bufMap = new HashMap<>();
        bufMap.put("declaredinvalid",getDeclaredInvalid());
        bufMap.put("tender",getTender());
        bufMap.put("thechairman", getTheChairman());
        bufMap.put("secretary", getSecretary());
        bufMap.put("presentmemberscommission", getPresentMembersCommission());
        return bufMap;
    }

    public void fillTableModelProtocolRead(List<Subject> subjectListFromDB){
        String[] columnNames = {"Npp", "RegistrationNumber", "nameA", "addresA", "PAN", "nlot", "price", "paymentAndDelivery"};
        int amountSubjects = subjectListFromDB.size();
        String[][]  data;
        data = new String[amountSubjects][8];//[row][column]

        int j = 0;
        for(Subject subject : subjectListFromDB){

            for(int i = 0 ; i <8 ; i++){
                switch (i) {
                    case 0:
                      data[j][i] = String.valueOf(j+1);
                        break;
                    case 1:
                        data[j][i] = String.valueOf(subject.getIdS());
                        break;
                    case 2:
                        data[j][i] = subject.getApplicant().getNameA();
                        break;
                    case 3:
                        data[j][i] = subject.getApplicant().getAddress();
                        break;
                    case 4:
                        data[j][i] = subject.getApplicant().getPan();
                        break;
                    case 5:
                        data[j][i] = String.valueOf(subject.getNumberS());
                        break;
                    case 6:
                        data[j][i] = String.valueOf(subject.getPrice());
                        break;
                    case 7:
                        data[j][i] = "Условия оплаты: "+subject.getPayment() + "\n Условия поставки: " + subject.getDelivery();
                        break;
                }//switch
            }
        }
        tableModelProtocolRead = new DefaultTableModel(data, columnNames);
    }

}

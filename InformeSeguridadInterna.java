/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package informeseguridadinterna;

import java.util.*;
import javax.naming.directory.*;
import javax.naming.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/*
 * Main class
 */
public class InformeSeguridadInterna
{

    /*REDSYS PRE*/
    //private static final String CONN_USER = "Administrador";
    //private static final String CONN_PASSWD ="c4N2zJoF8W";
    //private static final String CONN_IP ="10.5.128.244:636";
    //private static final String CONN_BASEDN = "dc=gid-d,dc=redsys,dc=es";
    
    /*REDSYS PRO VPN*/
    private static final String CONN_USER = "Administrador";
    private static final String CONN_PASSWD ="0IrcpA_NVH";
    private static final String CONN_IP ="10.5.129.54:636";
    private static final String CONN_BASEDN = "dc=gid,dc=redsys,dc=es";
    

    /*REDSYS PRO RED INTERNA*/
    //private static final String CONN_USER = "Administrador";
    //private static final String CONN_PASSWD ="0IrcpA_NVH";
    //private static final String CONN_IP ="10.129.16.10:636";
    //private static final String CONN_BASEDN = "dc=gid,dc=redsys,dc=es";
    

    private LDAPConnection connection;  //Connection var
    private GUIInterface GUIinterface;  //Interface var
       
    
    public InformeSeguridadInterna (){
        try{
            
            //We instantiate a connection
            connection = new LDAPConnection( CONN_USER, CONN_PASSWD,  CONN_IP, CONN_BASEDN);
        }catch(Exception e){
        }
            //We instantiate an interface
        GUIinterface = new GUIInterface(this);    
    }
    
    
    
    /*Method to find transactions.*/
    private ArrayList<Transaction> findTransaction(int year, Integer month) throws NamingException {
        //searchFilter, containing flowID reference is built
        String monthS = "";
                if (month < 10) monthS = "0"+month.toString();
                else{monthS = month.toString();}
        
        ArrayList<Transaction> transactions = new ArrayList<>();
        ///Map<String, String> segInResults = new HashMap<>();
        Map<String, Transaction> segInResults = new HashMap<>();
        int lastDayOfMonth = DateHandling.LastDayOfMonth(year, month);
        String searchFilter = "(&(objectClass=decisionPointTransaction)"
                + "(|(decisionPointCn=E-0.0-PRC-Modificaciones)(decisionPointCn=E-0.1-PRC-Altas)"
                + "(decisionPointCn=C-0.0-PRC-Modificaciones)"
                + "(decisionPointCn=E-0.1-PRC-Reactivacion)"
                 + "(decisionPointCn=E-1.2-DES-Externos-Informativo))"
                + "(createTimeStamp>="+year+monthS+"01000000Z)(createTimeStamp<="+year+monthS+lastDayOfMonth+"235959Z))";
            //System.out.println(searchFilter);

        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setReturningAttributes( new String[]{"*","+"}); //get operational attributes
        //search method on connection object is invoked
        //try{
        //NamingEnumeration results1 = connection.searchL("", searchFilter, searchControls);
        //}
 //catch (  AuthenticationException e) {
   //         System.out.println("LDAP Ex "+e.getMessage());
  //}
//catch (  NamingException e) {
  //          System.out.println("LDAP Ex "+e.getMessage());
  //}        
        NamingEnumeration results = connection.search("", searchFilter, searchControls);
        String flowID = null;
        String dateB = null;
        String accionSI = null;
        
        boolean hayResultados = false;
        // we loop thru search results
        String flowIDToSearchFor = null;
        //
        //String subStringQuery = "(&(objectClass=decisionPointTransaction)(decisionPointCn="
          //          + "E-1.2-APR-SeguridadInterna)(|";
        
        String subStringQuery = "(&(objectClass=decisionPointTransaction)(|(decisionPointCn="
                    + "E-1.2-APR-SeguridadInterna)(decisionPointCn=E-1.1-APR-SeguridadInterna-Reactivacion))(|";
        while(results.hasMore()) {
            SearchResult sr = (SearchResult) results.next(); //each result is cast into a SearchResult
            Attributes ar = sr.getAttributes(); //Attributes are obtained from each SeachResult
            flowIDToSearchFor = ar.get("flowID").get().toString().trim();
            subStringQuery = subStringQuery+"(flowID="+flowIDToSearchFor+")";
            //System.out.println(flowIDToSearchFor);
            
            //////////////////
            //System.out.println(ar.get("decisionPointCn").get().toString());
            //System.out.println(ar.get("flowID").get().toString());
            ///////////////////
            //String decisionBranch = ar.get("decisionBranch").get().toString().substring(4, 8).trim();
            //if (decisionBranch.isEmpty() || decisionBranch ==null) System.out.println("VACIO");
            //System.out.println(ar.get("flowID").get().toString());
            //System.out.println(ar.get("decisionBranch").get().toString());
            
            transactions.add(new Transaction(
                    ar.get("decisionPointCn").get().toString().trim(),
                    ar.get("flowID").get().toString().trim(), 
                    ar.get("createTimestamp").get().toString().trim(), 
                    "",
                    "",
                    ar.get("decisionBranch").get().toString().substring(4, 8).trim(), 
                    ar.get("modifierUser").get().toString().trim(), 
                    ar.get("decisionUserMember").get().toString().trim()));
            
            hayResultados = true;
            //System.out.println(subStringQuery);
        }
        
        subStringQuery = subStringQuery+"))";
        //System.out.println(hayResultados);
        //System.out.println(subStringQuery);
        
        NamingEnumeration results2 = connection.search("", subStringQuery,searchControls);
        List<String> l = new LinkedList<>();
        while (results2.hasMoreElements()){
            //System.out.println("found second record");
            SearchResult sr = (SearchResult)results2.nextElement();
            flowID = sr.getAttributes().get("flowID").get().toString().trim();
            dateB = sr.getAttributes().get("lastModified").get().toString().trim();
            Enumeration valoresMailConfirm = sr.getAttributes().get("emailConfirmation").getAll();
            while (valoresMailConfirm.hasMoreElements()){
                String valor = valoresMailConfirm.nextElement().toString();
                if(valor.toString().endsWith("=>2")){accionSI = "Aprobar";}
                else if(valor.toString().endsWith("=>3")){accionSI = "Rechazar";}
            }
            //accionSI = sr.getAttributes().get("lastModified").getAll();
            ////---segInResults.put(flowID, dateB);
            //System.out.println("found second record");
            segInResults.put(flowID, new Transaction(flowID, dateB, accionSI));
            //System.out.println(flowID);
        }
       
        //System.out.println(segInResults.keySet().size());
        for (Transaction tr : transactions){
                    if(segInResults.containsKey(tr.getFlowID())){
                        //System.out.println("found match");
                        ///---tr.setFechaAprobacionSI(segInResults.get(tr.getFlowID()));
                        tr.setFechaAprobacionSI(segInResults.get(tr.getFlowID()).getFechaAprobacionSI());
                        tr.setAccionSI(segInResults.get(tr.getFlowID()).getAccionSI());
                    }
        }
        if (!hayResultados){
            throw new NamingException("No hay resultados. Compruebe la referencia introducida\n");
        }
 
        return transactions;    //List containing transactions is returned
    }    
    
    private ArrayList<Transaction> findTransaction_OLD(int year, int month) throws NamingException {
        //searchFilter, containing flowID reference is built
        int lastDayOfMonth = DateHandling.LastDayOfMonth(year, month);
        String searchFilter = "(&(objectClass=decisionPointTransaction)"
                + "(|(decisionPointCn=E-0.0-PRC-Modificaciones)(decisionPointCn=E-0.1-PRC-Altas)"
                + "(decisionPointCn=E-1.2-DES-Externos-Informativo))"
                + "(createTimeStamp>="+year+month+"01000000Z)(createTimeStamp<="+year+month+lastDayOfMonth+"000000Z))";
        //String searchFilter = "(&(objectClass=decisionPointTransaction)(|(decisionPointCn=01 Alta RRHH)(decisionPointCn=02.1 Modificacion))(createTimeStamp>="
        //        +year+month+"11000000Z)(createTimeStamp<="+year+month+"13000000Z))";

        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        //search method on connection object is invoked
        NamingEnumeration results = connection.search("", searchFilter, searchControls);
        ArrayList<Transaction> transactions = new ArrayList<>();
        
        boolean hayResultados = false;
        // we loop thru search results
        String flowIDToSearchFor = null;
        String valDate = null;
        while(results.hasMore()) {
            SearchResult sr = (SearchResult) results.next(); //each result is cast into a SearchResult
            Attributes ar = sr.getAttributes(); //Attributes are obtained from each SeachResult
            flowIDToSearchFor = ar.get("flowID").get().toString();
            
            //searchFilter para buscar transacción de Seguridad Interna
            String searchFilter2 = "(&(objectClass=decisionPointTransaction)(decisionPointCn="
                    + "E-1.2-APR-SeguridadInterna)(flowID="+flowIDToSearchFor+"))";
            NamingEnumeration results2 = connection.search("", searchFilter2, searchControls);
            
            if (!results2.hasMore()){
                valDate = "";}
            while (results2.hasMore()){
                valDate = ((SearchResult)results2.next()).getAttributes().get("lastModified").get().toString();
            }
            //añadir los datos de la transacción a la lista de transacciones
            transactions.add(new Transaction(
                    ar.get("decisionPointCn").get().toString().trim(),
                    ar.get("flowID").get().toString().trim(), 
                    ar.get("createTimestamp").get().toString().trim(), 
                    valDate,
                    "",
                    ar.get("decisionBranch").get().toString().substring(4, 8).trim(), 
                    ar.get("modifierUser").get().toString().trim(), 
                    ar.get("decisionUserMember").get().toString().trim()));
            hayResultados = true;
        }
        if (!hayResultados){
            throw new NamingException("No hay resultados. Compruebe la referencia introducida\n");
        }
        return transactions;    //List containing transactions is returned
    }    
    
    public void launchInterface(){
            GUIinterface.mostrarMenu();
    }

    
    public ArrayList<Transaction> launchSearch(int year, int month) throws NamingException{

        return findTransaction(year,month);
    }
    
    
    public static void main (String[] args){
        InformeSeguridadInterna buscador = new InformeSeguridadInterna();
        
        buscador.launchInterface();
        
    }
    
    
}

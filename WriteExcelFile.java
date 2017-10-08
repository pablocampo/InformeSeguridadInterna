/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package informeseguridadinterna;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import java.text.DateFormat;


import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.Attribute;
import javax.swing.text.BadLocationException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableStyleInfo;


public class WriteExcelFile {

    private InformeSeguridadInterna buscador;
    
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS'Z'");

    
    private static String MY_ATTRS_OLD[] = { "cn", "decisionPointCn", "emailConfirmation", "decisionUserMember", 
                                        "flowID", "modifierUser", "createTimestamp", "lastModifiedFlow", 
                                        "modifyTimestamp", "causeOfRejection", "actualDecisionPointFlow", 
                                    "canceled", "closed", "inProcessFlow", "closedFlow" };
    
    
    private static String MY_ATTRS[] = { "cn", "createTimestamp", "lastModifiedFlow",  
                                            "decisionBranch", "modifierUser", "decisionUserMember", "lastModified" 
                                         };
    
    private static String COLUMN_NAMES[] = { "ID", "Fecha Creación", "Fecha Acción SI", "Tipo Acción",  
                                            "Entidad", "Peticionario", "Usuario Afectado", "Nº de Usuarios", 
                                            "Alta de Usuarios", "Baja de Usuarios", "Modificación de Usuarios",
                                            "Reactivación de Usuarios"};
    
    public static void writeXLSXFile(String year, String month, ArrayList<Transaction> transList) throws IOException {
                //////////
                //for (Transaction trs : transList){
                 //   System.out.println(trs.getDecisionPointCn().toString());
               // }
        ///////////////////
		String excelFileName = "./Informe-"+year+"_"+month+".xlsx";//name of excel file

		String sheetName = month+"-"+year;//name of sheet

		XSSFWorkbook wb = new XSSFWorkbook();

                //Styles
                CellStyle style1 = wb.createCellStyle();
                style1.setFillForegroundColor(IndexedColors.BLUE.getIndex());
                style1.setFillPattern(CellStyle.SOLID_FOREGROUND);

                CellStyle style2 = wb.createCellStyle();
                style2.setFillForegroundColor(IndexedColors.WHITE.getIndex());
                style2.setFillPattern(CellStyle.SOLID_FOREGROUND);

                //Fonts
                Font font1 = wb.createFont();
                font1.setColor(IndexedColors.WHITE.getIndex());
                font1.setBold(true);
                font1.setFontHeightInPoints((short)14);
                style1.setFont(font1);

                Font font2 = wb.createFont();
                font2.setColor(IndexedColors.BLACK.getIndex());
                font2.setBold(false);

                Font font2b = wb.createFont();
                font2b.setColor(IndexedColors.BLACK.getIndex());
                font2b.setBold(true);
                font2b.setFontHeightInPoints((short)12);
                style2.setFont(font2);
                
		XSSFSheet sheet = wb.createSheet(sheetName) ;

        	XSSFRow row = sheet.createRow(0);
        	XSSFCell cell = row.createCell(1);
                
                
                cell.setCellValue("Peticiones "+month+"-"+year);
                row = sheet.createRow(2);
                for (int i = 0; i < COLUMN_NAMES.length; i++) {
                    
                    
                    cell = row.createCell(i+1);
                    cell.setCellValue(COLUMN_NAMES[i]);
                    cell.setCellStyle(style1);
                }
                try{
                int rowCounter = 3;
                for (Transaction tr : transList){
                    row = sheet.createRow(rowCounter);
                        
                        cell = row.createCell(1);
                        cell.setCellValue(tr.getFlowID());
                        cell.setCellStyle(style2);
                        cell.getCellStyle().setFont(font2b);
                        //cell.setCellStyle(style2.setFont(font2b));

                        cell = row.createCell(2);
                        cell.setCellValue(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new SimpleDateFormat("yyyyMMddHHmmss'Z'").parse(tr.getFechaLanzamiento())));
                        cell.setCellStyle(style2);
                        cell.getCellStyle().setFont(font2);

                        cell = row.createCell(3);
                        if(tr.getFechaAprobacionSI().equals("")){cell.setCellValue("");}
                        else{
                            cell.setCellValue(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new SimpleDateFormat("yyyyMMddHHmmss'Z'").parse(tr.getFechaAprobacionSI())));
                        }
                        cell.setCellStyle(style2);
                        cell = row.createCell(4);
                        cell.setCellValue(tr.getAccionSI());
                        cell.setCellStyle(style2);

                        cell.setCellStyle(style2);
                        cell = row.createCell(5);
                        cell.setCellValue(convertirNBREntidad(tr.getEntidad()));
                        cell.setCellStyle(style2);

                        cell = row.createCell(6);
                        if (tr.getPeticionario().equals("E1465940")){cell.setCellValue("PRUEBAS");} 
                        else {cell.setCellValue(tr.getPeticionario());}
                        cell.setCellStyle(style2);

                        cell = row.createCell(7);
                        cell.setCellValue(tr.getUsuarioAfectado());
                        cell.setCellStyle(style2);
        
                        cell = row.createCell(8);
                        cell.setCellType(cell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(1);
                        cell.setCellStyle(style2);
        
                        cell = row.createCell(9);
                        cell.setCellType(cell.CELL_TYPE_NUMERIC);
                        if (tr.getDecisionPointCn().equals("E-0.1-PRC-Altas")){
                            cell.setCellValue(1);
                        }else{
                            cell.setCellValue(0);
                        }
                        cell = row.createCell(10);
                        cell.setCellType(cell.CELL_TYPE_NUMERIC);
                        if (tr.getDecisionPointCn().equals("E-1.2-DES-Externos-Informativo")){
                            cell.setCellValue(1);
                        }else{
                            cell.setCellValue(0);
                        }
                        cell = row.createCell(11);
                        cell.setCellType(cell.CELL_TYPE_NUMERIC);
                        if (tr.getDecisionPointCn().equals("E-0.0-PRC-Modificaciones")){
                            cell.setCellValue(1);
                        }else{
                            cell.setCellValue(0);
                        }
                        cell = row.createCell(12);
                        cell.setCellType(cell.CELL_TYPE_NUMERIC);
                        if (tr.getDecisionPointCn().equals("E-0.1-PRC-Reactivacion")){
                            cell.setCellValue(1);
                        }else{
                            cell.setCellValue(0);
                        }
                        cell.setCellStyle(style2);
        
                        rowCounter++;
                }
                }catch(ParseException e){
                
                }
		
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                sheet.autoSizeColumn(6);
                sheet.autoSizeColumn(7);
                sheet.autoSizeColumn(8);
                sheet.autoSizeColumn(9);
                sheet.autoSizeColumn(10);
                sheet.autoSizeColumn(11);
                sheet.autoSizeColumn(12);
                FileOutputStream fileOut = new FileOutputStream(excelFileName);

		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
    
    private static String convertirNBREntidad(String nrb){
        String nombreEntidad = null;
        switch(nrb){
            case "0019"  : nombreEntidad = "DEUTSCHEBANK"; break;
            case "0049"  : nombreEntidad = "BANCO SANTANDER"; break;
            case "0061"  : nombreEntidad = "BANCAMARCH"; break;
            case "0073"  : nombreEntidad = "OPEN BANK"; break;
            case "0075"  : nombreEntidad = "Popular"; break;
            case "0078"  : nombreEntidad = "BANCA PUEYO"; break;
            case "0081"  : nombreEntidad = "Sabadell"; break;
            case "0128"  : nombreEntidad = "BANKINTER"; break;
            case "0130"  : nombreEntidad = "BANCO CAIXA XERAL"; break;
            case "0131"  : nombreEntidad = "BES"; break;
            case "0138"  : nombreEntidad = "BANKOA"; break;
            case "0152"  : nombreEntidad = "BARCLAYCARD PLC"; break;
            case "0182"  : nombreEntidad = "BBVA"; break;
            case "0186"  : nombreEntidad = "BANCO MEDIOLANUM"; break;
            case "0198"  : nombreEntidad = "BANCO COOPERATIVO"; break;
            case "0216"  : nombreEntidad = "TARGOBANK"; break;
            case "0224"  : nombreEntidad = "Santander Consumer Finance"; break;
            case "0225"  : nombreEntidad = "BANCO CETELEM"; break;
            case "0229"  : nombreEntidad = "WIZINK"; break;
            case "0234"  : nombreEntidad = "BANCO CAMINOS"; break;
            case "0235"  : nombreEntidad = "BANCO PICHINCHA"; break;
            case "0237"  : nombreEntidad = "CAJASUR BANCO"; break;
            case "0239"  : nombreEntidad = "EVO Banco"; break;
            case "1465"  : nombreEntidad = "ING BANK"; break;
            case "1491"  : nombreEntidad = "TRIODOSBANK"; break;
            case "1525"  : nombreEntidad = "BANQUE CHAABI DU MAROC"; break;
            case "1550"  : nombreEntidad = "FIARE BANCA ETICA"; break;
            case "2038"  : nombreEntidad = "Bankia"; break;
            case "2056"  : nombreEntidad = "CAIXA POLLENSA"; break;
            case "2095"  : nombreEntidad = "KUTXABANK"; break;
            case "2100"  : nombreEntidad = "Caixabank"; break;
            case "3001"  : nombreEntidad = "C.R. ALMENDRALEJO"; break;
            case "3005"  : nombreEntidad = "C.R. CENTRAL"; break;
            case "3007"  : nombreEntidad = "C.R. GIJON"; break;
            case "3008"  : nombreEntidad = "C.R. NAVARRA"; break;
            case "3009"  : nombreEntidad = "C.R. EXTREMADURA"; break;
            case "3016"  : nombreEntidad = "C.R. SALAMANCA"; break;
            case "3017"  : nombreEntidad = "C. R. SORIA"; break;
            case "3018"  : nombreEntidad = "C. R. REGIONAL"; break;
            case "3020"  : nombreEntidad = "C. R. UTRERA"; break;
            case "3023"  : nombreEntidad = "C. R. GRANADA"; break;
            case "3025"  : nombreEntidad = "CR INGENIEROS"; break;
            case "3035"  : nombreEntidad = "CAJALABORAL"; break;
            case "3058"  : nombreEntidad = "CAJAMAR"; break;
            case "3059"  : nombreEntidad = "C. R. ASTURIAS"; break;
            case "3060"  : nombreEntidad = "C. R. BURGOS"; break;
            case "3067"  : nombreEntidad = "C. R. JAEN"; break;
            case "3070"  : nombreEntidad = "C. R. GALEGA"; break;
            case "3076"  : nombreEntidad = "C. R. TENERIFE"; break;
            case "3080"  : nombreEntidad = "C. R. TERUEL"; break;
            case "3081"  : nombreEntidad = "C. R. TOLEDO"; break;
            case "3085"  : nombreEntidad = "C. R. ZAMORA"; break;
            case "3089"  : nombreEntidad = "C. R. DE BAENA"; break;
            case "3096"  : nombreEntidad = "C. R. LALCUDIA"; break;
            case "3098"  : nombreEntidad = "C. R. NUEVA CARTEYA"; break;
            case "3104"  : nombreEntidad = "C. R. CAÑETE DE LAS TORRES"; break;
            case "3111"  : nombreEntidad = "C. R. S. ISIDRO"; break;
            case "3113"  : nombreEntidad = "C. R. ALCORA"; break;
            case "3115"  : nombreEntidad = "C. R. ADAMUZ"; break;
            case "3117"  : nombreEntidad = "C. R. ALGEMESI"; break;
            case "3127"  : nombreEntidad = "C. R. CASAS IBAÑEZ"; break;
            case "3130"  : nombreEntidad = "C. R. SAN JOSE DE ALMASSORA"; break;
            case "3134"  : nombreEntidad = "C. R. ONDA"; break;
            case "3138"  : nombreEntidad = "C. R. BETXI"; break;
            case "3144"  : nombreEntidad = "C. R. VILLAMALEA"; break;
            case "3150"  : nombreEntidad = "C. R. ALBAL"; break;
            case "3159"  : nombreEntidad = "CAJA POPULAR VALENCIANA"; break;
            case "3162"  : nombreEntidad = "C. R. BENICARLO"; break;
            case "3166"  : nombreEntidad = "C. R. COVES VINROMA"; break;
            case "3174"  : nombreEntidad = "C. R. VINAROZ"; break;
            case "3187"  : nombreEntidad = "C. R. DEL SUR"; break;
            case "3190"  : nombreEntidad = "GLOBALCAJA"; break;
            case "3191"  : nombreEntidad = "BANTIERRA"; break;
            case "6100"  : nombreEntidad = "Comercia GPBrasil"; break;
            case "6707"  : nombreEntidad = "PECUNPAY"; break;
            case "6812"  : nombreEntidad = "MONEY EXCHANGE"; break;
            case "6858"  : nombreEntidad = "SEMS"; break;
            case "8795"  : nombreEntidad = "CARREFOUR"; break;
            case "8814"  : nombreEntidad = "Oney"; break;
            case "8834"  : nombreEntidad = "EVO FINANCE"; break;
            case "9920"  : nombreEntidad = "CREDITANDORRA"; break;
            case "9922"  : nombreEntidad = "MORABANC"; break;
            case "9923"  : nombreEntidad = "VALLBANC"; break;
            case "9924"  : nombreEntidad = "ANDBANC"; break;            
            default: nombreEntidad = nrb; break;
        }
        return nombreEntidad;
    }
}

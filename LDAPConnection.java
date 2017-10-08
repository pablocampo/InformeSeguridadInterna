/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package informeseguridadinterna;

import java.util.*;
import java.security.*;
import javax.naming.Context;
import javax.naming.directory.*;
import javax.naming.*;
import javax.security.auth.login.*;
import javax.naming.ldap.LdapContext;
//import org.jboss.security.auth.callback.UsernamePasswordHandler;
import com.whitebearsolutions.directory.ldap.ssl.*;
import javax.naming.ldap.InitialLdapContext;
/**
 * Write a description of class LDAPConnection here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LDAPConnection
{
    // instance variables - replace the example below with your own
    private DirContext ctx;
    private LdapContext ctcx;
    Hashtable<String, String> env = new Hashtable<>();
    
    /**
     * Constructor for objects of class LDAPConnection
     */
    public LDAPConnection (String user, String passwd, String ip, String baseDN) throws Exception
    {
        // initialise instance variables
        //Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldaps://"+ip+"/"+baseDN);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        //env.put("com.sun.jndi.ldap.read.timeout", "0");
        env.put(Context.SECURITY_PRINCIPAL, "uid="+user+",ou=personas,"+baseDN);
        env.put(Context.SECURITY_CREDENTIALS, passwd);
        env.put("java.naming.ldap.factory.socket", "com.whitebearsolutions.directory.ldap.ssl.LDAPSSocketFactory");
        //env.put("java.naming.ldap.factory.socket", "MySSLSocketFactory");
        env.put(Context.SECURITY_PROTOCOL, "ssl");

        /**
        ///Se deshablita esto de momento
        try{
            testLogin();
        }catch(AuthenticationException ex){}
        * */
            ctx = new InitialDirContext(env);
            ctcx = new InitialLdapContext(env,null );
            
    }

        /**
        ///Se deshablita esto de momento
    public void testLogin () throws Exception{
        ////////////////////////
        Object principal = env.get(Context.SECURITY_PRINCIPAL);        
        Object credentials = env.get(Context.SECURITY_CREDENTIALS);        

        String protocol = "other";
        Object prop = env.get(Context.SECURITY_PROTOCOL);
        if( prop != null )
           protocol = prop.toString();

        try
        {
           // Get the principal username
           String username;

           if( principal instanceof Principal )
           {
              Principal p = (Principal) principal;
              username = p.getName();
           }
           else
           {
              username = principal.toString();
           }
           UsernamePasswordHandler handler = new UsernamePasswordHandler(username,
              credentials);
           // Do the JAAS login
           LoginContext lc = new LoginContext(protocol, handler);
           lc.login();
        }
        catch(LoginException e)
        {
           AuthenticationException ex = new AuthenticationException("Failed to login using protocol="+protocol);
           ex.setRootCause(e);
           System.out.println("NO login");
           throw ex;
        }    
        
    }
*/    
    
    public DirContext getDirContext () throws Exception{
        return ctx;
        
    }
    
    public LdapContext getLdapContext () throws Exception{
        return ctcx;
        
    }
    
    public NamingEnumeration searchL(String ldapSearchBase, String filtro, SearchControls searchControls) throws NamingException{
        //String searchFilter = filtro;
        String ldapSearchBase_="";
        //searchControls.setTimeLimit(60000);
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setReturningAttributes( new String[]{"*","+"}); //get operational attributes
        NamingEnumeration results = ctcx.search(ldapSearchBase, filtro, searchControls);

        return results;
    }   

    public NamingEnumeration search(String ldapSearchBase, String filtro, SearchControls searchControls) throws NamingException{
        //String searchFilter = filtro;
        String ldapSearchBase_="";
        //searchControls.setTimeLimit(60000);
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setReturningAttributes( new String[]{"*","+"}); //get operational attributes
        NamingEnumeration results = ctx.search(ldapSearchBase, filtro, searchControls);

        return results;
    }   

    public NamingEnumeration search_OLD(String ldapSearchBase, String filtro) throws NamingException{
        //String searchFilter = filtro;
        String ldapSearchBase_="";
        SearchControls searchControls = new SearchControls();
        //searchControls.setTimeLimit(60000);
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setReturningAttributes( new String[]{"*","+"}); //get operational attributes
        NamingEnumeration results = ctx.search(ldapSearchBase, filtro, searchControls);

        return results;
    }   

}

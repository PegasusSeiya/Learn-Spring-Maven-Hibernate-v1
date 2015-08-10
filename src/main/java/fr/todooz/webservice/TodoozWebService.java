package fr.todooz.webservice;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.commons.lang.StringUtils;

@WebService
@SOAPBinding( style = SOAPBinding.Style.RPC )
public class TodoozWebService {
    public String hi( String name ) {
        return "Hello " + StringUtils.defaultIfEmpty( name, "unknown" );
    }
}

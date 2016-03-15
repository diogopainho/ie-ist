package transporterzws;

import java.util.Random;

import javax.jws.WebService;

import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

@WebService(serviceName = "TransporterZService", portName = "TransporterZServiceSoap12HttpPort")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class TransporterZService {
    public TransporterZService() {
        super();
    }
    
    //Return distance * random double between 0.1 and 0.4 and divide by 2
    public Double getCotationTransporterZ(String distance){
            Random generator = new Random();
        return (Integer.parseInt(distance) * (generator.nextInt((int)((0.4-0.1)*10+1))+0.4*10) / 10.0) / 4;
    }
}

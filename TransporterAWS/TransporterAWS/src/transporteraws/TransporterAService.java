package transporteraws;

import java.util.Random;

import javax.jws.WebService;

import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

@WebService(serviceName = "TransporterAService", portName = "TransporterAServiceSoap12HttpPort")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class TransporterAService {
    public TransporterAService() {
        super();
    }
    
    //Return distance * random double between 0.1 and 0.3 and divide by 2
    public Double getCotationTransporterA(String distance){
            Random generator = new Random();
        return (Integer.parseInt(distance) * (generator.nextInt((int)((0.3-0.1)*10+1))+0.1*10) / 10.0) / 4;
    }
}

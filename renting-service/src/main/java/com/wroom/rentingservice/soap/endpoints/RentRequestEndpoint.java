package com.wroom.rentingservice.soap.endpoints;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.wroom.rentingservice.converter.RentConverter;
import com.wroom.rentingservice.domain.BundledRequests;
import com.wroom.rentingservice.domain.RentRequest;
import com.wroom.rentingservice.domain.enums.RequestStatus;
import com.wroom.rentingservice.repository.AdRepository;
import com.wroom.rentingservice.repository.BundleRepository;
import com.wroom.rentingservice.repository.RentRequestRepository;
import com.wroom.rentingservice.service.RentsService;
import com.wroom.rentingservice.soap.converters.BundledRequestsSoapConverter;
import com.wroom.rentingservice.soap.converters.RentRequestSoapConverter;
import com.wroom.rentingservice.soap.xsd.OperationRents;
import com.wroom.rentingservice.soap.xsd.RentRequestSoap;
import com.wroom.rentingservice.soap.xsd.SendBundleRequest;
import com.wroom.rentingservice.soap.xsd.SendBundleResponse;
import com.wroom.rentingservice.soap.xsd.SendRentRequest;
import com.wroom.rentingservice.soap.xsd.SendRentResponse;

@Endpoint
public class RentRequestEndpoint {

	private static final String NAMESPACE_URI ="http://ftn.com/wroom-agent/xsd";
	
	@Autowired
	private RentRequestRepository rentsRepository;
	@Autowired
	private RentsService rentsService;
	@Autowired 
	private AdRepository adsRepository;
	@Autowired
	private BundleRepository bundleRepository;
	
	@Autowired
	public RentRequestEndpoint(RentRequestRepository rentsRepository,
			AdRepository adsRepository,
			BundleRepository bundleRepository,
			RentsService rentsService) {
		this.rentsRepository = rentsRepository;
		this.adsRepository = adsRepository;
		this.bundleRepository = bundleRepository;
		this.rentsService = rentsService;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "SendRentRequest")
	@ResponsePayload
	public SendRentResponse sendRent(@RequestPayload SendRentRequest request) {
		System.out.println(">>>>>>> Received a RentRequest");
		SendRentResponse response = new SendRentResponse();
		
		RentRequest entity = RentRequestSoapConverter.fromSoapRequest(request.getRentRequest());
		entity.setAd(this.adsRepository.findById(request.getRentRequest().getAd()).get());

		// TODO
//		entity.setRentReport(rentReport);
		
		if(request.getOperation() == OperationRents.OCCUPY) {
			try {
				this.rentsService.occupy(entity);
				System.out.println(">>>>>>> Occupied");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(request.getOperation() == OperationRents.ACCEPT) {
			try {
				this.rentsService.accept(entity); 
				System.out.println(">>>>>>> Accepted");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if(request.getOperation() == OperationRents.DECLINE) {
			try {
				this.rentsService.decline(entity);
				System.out.println(">>>>>>> Declined");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		

		response.setRentRequest(RentRequestSoapConverter.toSoapRequest(entity));
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "SendBundleRequest")
	@ResponsePayload
	public SendBundleResponse sendBundle(@RequestPayload SendBundleRequest request) {
		System.out.println(">>>>>>> Received a Bundle");
		SendBundleResponse response = new SendBundleResponse();
		
		BundledRequests entity = new BundledRequests();
		entity.setRequests(new HashSet<RentRequest>());
		BundledRequests saved = this.bundleRepository.save(entity);
		
		Set<RentRequest> requests = new HashSet<RentRequest>();
		for(RentRequestSoap r : request.getBundle().getRequests()) {
			RentRequest rent = RentRequestSoapConverter.fromSoapRequest(r);
			rent.setAd(this.adsRepository.findById(r.getAd()).get());
			rent.setBundle(saved);
			rent.setStatus(RequestStatus.PENDING);
			// TODO: report dodati, izmeniti requested user
			requests.add(rent);
		}
		
		saved.setRequests(requests);
		this.bundleRepository.save(saved);
		System.out.println(">>>>>>> Saved a Bundle");
		
		response.setBundle(BundledRequestsSoapConverter.toSoapBundle(entity));
		return response;
	}
	
}

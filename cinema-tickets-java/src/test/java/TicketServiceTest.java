import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationService;
import org.junit.jupiter.api.BeforeAll;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.TktServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.mockito.Mockito.mock;

public class TicketServiceTest {

    TicketPaymentServiceImpl ticketPaymentService = new TicketPaymentServiceImpl();
    SeatReservationServiceImpl seatReservationService = new SeatReservationServiceImpl();

    TktServiceImpl tktService = new TktServiceImpl();


//    @BeforeAll
//    void setUp(){
//
//        instance = new TktServiceImpl();
//
//    }
    //Test Account Id exceptions

    @Test(expected = InvalidPurchaseException.class)
    public void testIdIsNotZeroOrLess() {

        tktService.purchaseTickets(-1L);

    }

    @Test
    public void testIdHappyPath(){
       tktService.purchaseTickets(2L);
    }

    @Test
    public void calculateTotalCostOfAllTickets(){

        TicketTypeRequest adultRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        TicketTypeRequest adultRequest1 = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        TicketTypeRequest adultRequest2 = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        TicketTypeRequest childRequest = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);

        int adultReq = adultRequest.getNoOfTickets();
        int childReq = childRequest.getNoOfTickets();


        tktService.purchaseTickets(2L, adultRequest, adultRequest1, adultRequest, childRequest);

    }

    @Test(expected = InvalidPurchaseException.class)
    public void twoChildrenOneAdultSeat(){

        TicketTypeRequest adultRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        TicketTypeRequest childRequest = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);
        TicketTypeRequest childRequest1 = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);

        tktService.purchaseTickets(2L, adultRequest, childRequest, childRequest1);


    }

    @Test(expected = InvalidPurchaseException.class)
    public void twoInfantOneAdultSeat(){

        TicketTypeRequest adultRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        TicketTypeRequest infantRequest = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);
        TicketTypeRequest infantRequest1 = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);

        tktService.purchaseTickets(2L, adultRequest, infantRequest, infantRequest1);


    }

    @Test
    public void twoInfantTwoAdultSeat(){

        TicketTypeRequest adultRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        TicketTypeRequest adultRequest1 = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        TicketTypeRequest infantRequest = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);
        TicketTypeRequest infantRequest1 = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);

        tktService.purchaseTickets(2L, adultRequest, adultRequest1, infantRequest, infantRequest1);


    }

    @Test
    public void twoChildTwoAdultSeat(){

        TicketTypeRequest adultRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        TicketTypeRequest adultRequest1 = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        TicketTypeRequest childRequest = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);
        TicketTypeRequest childRequest1 = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);

        tktService.purchaseTickets(2L, adultRequest, adultRequest1, childRequest, childRequest1);


    }

    @Test
    public void oneChildOneInfantTwoAdultSeat(){

        TicketTypeRequest adultRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        TicketTypeRequest adultRequest1 = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        TicketTypeRequest childRequest = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);
        TicketTypeRequest infantRequest = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);

        tktService.purchaseTickets(2L, adultRequest, adultRequest1, childRequest, infantRequest);

    }

    @Test
    public void orderTwentyTickets(){

        int numTickets = 20;
        TicketTypeRequest[] request = new TicketTypeRequest[numTickets];


        for(int i = 0; i < numTickets; i++){
            request[i] = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        }

        tktService.purchaseTickets(2L, request);


    }

    @Test(expected = InvalidPurchaseException.class)
    public void orderTooManyTickets(){

        int numTickets = 21;
        TicketTypeRequest[] request = new TicketTypeRequest[numTickets];


        for(int i = 0; i < numTickets; i++){
            request[i] = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        }

        tktService.purchaseTickets(2L, request);


    }








}

import org.junit.Test;
import uk.gov.dwp.uc.pairtest.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class TicketServiceTest {


    TicketServiceImpl tktService = new TicketServiceImpl();

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

    @Test(expected = InvalidPurchaseException.class)
    public void oneInfantOneChildOneAdult(){

        TicketTypeRequest adultRequest = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1);
        TicketTypeRequest childRequest = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);
        TicketTypeRequest infantRequest = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);

        tktService.purchaseTickets(2L, adultRequest, childRequest, infantRequest);


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

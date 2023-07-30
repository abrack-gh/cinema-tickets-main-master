package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationService;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class TicketServiceImpl implements TicketService {

    /**
     * Should only have private methods other than the one below.
     */


    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {

        TicketPaymentServiceImpl ticketPaymentService = new TicketPaymentServiceImpl();
        SeatReservationServiceImpl seatReservationService = new SeatReservationServiceImpl();

        final int ADULT_TICKET_PRICE = 20;
        final int CHILD_TICKET_PRICE = 10;


        int adultSeats = 0;
        int childSeats = 0;
        int infantSeats = 0;

        int totalSeats = ticketTypeRequests.length;


        //For Loop to determine how many seats are required.

        for(int i = 0; i < totalSeats; i++){
            if(ticketTypeRequests[i].getTicketType() == TicketTypeRequest.Type.ADULT){
                adultSeats++;
            }
            if(ticketTypeRequests[i].getTicketType() == TicketTypeRequest.Type.CHILD) {
                childSeats++;
            }
            if(ticketTypeRequests[i].getTicketType() == TicketTypeRequest.Type.INFANT){
                infantSeats++;
            }

        }


        int adultTicket = 0;
        int childTicket = 0;
        int infantTicket = 0;

        int totalTickets = ticketTypeRequests.length;

        for(int j = 0; j < totalTickets; j++){
            if(ticketTypeRequests[j].getTicketType() == TicketTypeRequest.Type.ADULT){
                adultTicket++;
            }
            if(ticketTypeRequests[j].getTicketType() == TicketTypeRequest.Type.CHILD){
                childTicket++;
            }
            if(ticketTypeRequests[j].getTicketType() == TicketTypeRequest.Type.INFANT){
                infantTicket++;
            }

        }


        int totalCostAdult = adultSeats * ADULT_TICKET_PRICE;
        int totalCostChild = childSeats * CHILD_TICKET_PRICE;
        //Infant seat is 0. So no additional cost.

        int totalCost = totalCostAdult + totalCostChild;
        int totalTicketOrder = adultTicket + childTicket + infantTicket;
        int totalSeatOrder = adultSeats + childSeats;

        int childSeatsAndInfantSeats = childTicket + infantTicket;

        //Exceptions
        if(childSeats > adultSeats){
            System.out.println("Cannot request more child seats than adult seats");
            throw new InvalidPurchaseException();
        }
        if(infantTicket > adultSeats){
            System.out.println("Cannot request more infant seats than adult seats");
            throw new InvalidPurchaseException();
        }
        if(childSeatsAndInfantSeats > adultTicket){
            System.out.println("Cannot request more infant or child seats than adult seats");
            throw new InvalidPurchaseException();
        }
        if(accountId <= 0) {
            System.out.println("Account ID must be greater than 0.");
            throw new InvalidPurchaseException();
        }
        if(totalTickets > 20){
            System.out.println("Maximum purchase of 20 tickets.");
            throw new InvalidPurchaseException();
        }



        seatReservationService.reserveSeat(accountId, totalSeats);
        ticketPaymentService.makePayment(accountId, totalCost);
        System.out.println("Total seats reserved: " + totalSeatOrder + " Total tickets for: " + totalTicketOrder + " At a cost of: £" + totalCost);

//        int totalPrice = ticketTypeRequests.length;
//
//        for(int j = 0; j < totalPrice; j+=20 ){
//            if(ticketTypeRequests[j].getTicketType() == TicketTypeRequest.Type.ADULT){
//                totalPrice++;
//            }
//        }
//        for(int k = 0; k < totalPrice; k += 10){
//            if(ticketTypeRequests[k].getTicketType() == TicketTypeRequest.Type.CHILD){
//                totalPrice++;
//            }
//        }





    }


}

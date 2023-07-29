package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationService;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class TktServiceImpl implements TicketService {

    /**
     * Should only have private methods other than the one below.
     */

//            - There are 3 types of tickets i.e. Infant, Child, and Adult.
//
//            - The ticket prices are based on the type of ticket (see table below).
//
//            - The ticket purchaser declares how many and what type of tickets they want to buy.
//
//            - Multiple tickets can be purchased at any given time.
//
//            - Only a maximum of 20 tickets that can be purchased at a time.
//
//            - Infants do not pay for a ticket and are not allocated a seat. They will be sitting on an Adult's lap.
//
//            - Child and Infant tickets cannot be purchased without purchasing an Adult ticket.
//
//            |   Ticket Type    |     Price   |
//
//            | ---------------- | ----------- |
//
//            |    INFANT        |    £0       |
//
//            |    CHILD         |    £10      |
//
//            |    ADULT         |    £20      |
//
//            - There is an existing `TicketPaymentService` responsible for taking payments.
//
//            - There is an existing `SeatReservationService` responsible for reserving seats.

//
//## Constraints
//
//- The TicketService interface CANNOT be modified. (For Java solution only)
//
//            - The code in the thirdparty.* packages CANNOT be modified.
//
//            - The `TicketTypeRequest` SHOULD be an immutable object.
//
//## Assumptions
//
//    You can assume:
//
//            - All accounts with an id greater than zero are valid. They also have sufficient funds to pay for any no of tickets.
//
//            - The `TicketPaymentService` implementation is an external provider with no defects. You do not need to worry about how the actual payment happens.
//
//            - The payment will always go through once a payment request has been made to the `TicketPaymentService`.
//
//            - The `SeatReservationService` implementation is an external provider with no defects. You do not need to worry about how the seat reservation algorithm works.
//
//            -The seat will always be reserved once a reservation request has been made to the `SeatReservationService`.
//
//## Your Task
//
//    Provide a working implementation of a `TicketService` that:
//
//            - Considers the above objective, business rules, constraints & assumptions.
//
//            - Calculates the correct amount for the requested tickets and makes a payment request to the `TicketPaymentService`.
//
//            - Calculates the correct no of seats to reserve and makes a seat reservation request to the `SeatReservationService`.
//
//            - Rejects any invalid ticket purchase requests. It is up to you to identify what should be deemed as an invalid purchase request.”

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

        //Exceptions
        if(childSeats > adultSeats){
            System.out.println("Cannot request more child seats than adult seats");
            throw new InvalidPurchaseException();
        }
        if(infantTicket > adultSeats){
            System.out.println("Cannot request more infant seats than adult seats");
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

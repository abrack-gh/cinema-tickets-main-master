package uk.gov.dwp.uc.pairtest;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.HashMap;

public class TicketServiceImpl implements TicketService {
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

    public static final int MAX_NUMBER_OF_TICKET = 20;

    private final TicketPaymentService ticketPaymentService;
    private final SeatReservationService seatReservationService;

    public TicketServiceImpl(TicketPaymentService ticketPaymentService, SeatReservationService seatReservationService) {
        this.ticketPaymentService = ticketPaymentService;
        this.seatReservationService = seatReservationService;
    }

    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {


        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("Infant", 0);
        hashMap.put("Child", 10);
        hashMap.put("Adult", 20);

        //Seats initialized to 0

        int infantSeats = 0;
        int childSeats = 0;
        int adultSeats = 0;

        int noOfTickets = ticketTypeRequests.length;

        //Enhance for loops through ticket-request to determine total number of corresponding seats.

        for (int i = 0; i < noOfTickets; i++) {

            if (ticketTypeRequests[i].getTicketType() == TicketTypeRequest.Type.INFANT) {
                infantSeats++;
            }
            if (ticketTypeRequests[i].getTicketType() == TicketTypeRequest.Type.CHILD) {
                childSeats++;
            }
            if (ticketTypeRequests[i].getTicketType() == TicketTypeRequest.Type.ADULT) {
                adultSeats++;
            }
        }


            if (accountId <= 0) {
                System.out.println("Invalid Customer id");
                throw new InvalidPurchaseException();
            }

            //Check maximum limit

            if (noOfTickets > MAX_NUMBER_OF_TICKET) {
                System.out.println("Maximum of 20 tickets per order");
                throw new InvalidPurchaseException();
            }

            //Checks no child or infant seat without adult seat

            if (((infantSeats > 0) || (childSeats > 0)) && (adultSeats < 1)) {
                System.out.println("1 adult per child/infant");
                throw new InvalidPurchaseException();
            }


            int totalAmountToPay = (adultSeats * hashMap.get("Adult")) + (childSeats * hashMap.get("Child")) + (infantSeats * hashMap.get("Infant"));
            int numberOfSeats = adultSeats + childSeats;


            ticketPaymentService.makePayment(accountId, totalAmountToPay);
            seatReservationService.reserveSeat(accountId, numberOfSeats);
            System.out.println("Total seats reserved: " + noOfTickets + " At a cost of: " + totalAmountToPay + " GPB");

        }


}

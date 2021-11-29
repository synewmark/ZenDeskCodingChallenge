package frontend;

import java.io.IOException;
import java.util.Scanner;

import backend.TicketHandler;
import backend.TicketHandler.SortBy;
import backend.TicketHandler.SortOrder;
import model.Ticket;

public class TicketPrinter {
	public static final String instructionMessage = "Press n to print next page of results or a ticket number to get a detailed view";
	public static Scanner scanner = new Scanner(System.in);

	public static void main(String... args) throws IOException {
		String subdomain = args[0];
		String emailAddress = args[1];
		String pw = args[2];

		TicketHandler.SortBy sortBy = null;
		TicketHandler.SortOrder sortOrder = null;
		if (args.length > 3) {
			sortBy = SortBy.valueOf(args[3].toUpperCase());
		}
		if (args.length > 4) {
			sortOrder = SortOrder.valueOf(args[4].toUpperCase());
		}
		TicketHandler handler = new TicketHandler(subdomain, emailAddress, pw, sortBy, sortOrder);
		while (handler.hasMore()) {
			StringBuilder sb = new StringBuilder();
			Ticket[] ticketArray = handler.pageTicketResults();
			for (int i = 0; i < ticketArray.length; i++) {
				if (ticketArray[i] == null) {
					break;
				}

				sb.append("Ticket #: " + (i + 1) + '\n');
				sb.append(ticketArray[i].toSimplifiedString() + "\n\n");
			}
			System.out.println(sb.toString());
			System.out.println(instructionMessage);

			while (true) {
				String returnMessage = getUserInput();
				if (returnMessage.toLowerCase().equals("n")) {
					break;
				} else if (isNumberLess25(returnMessage)) {
					System.out.println(ticketArray[Integer.parseInt(returnMessage)]);
					System.out.println(instructionMessage);
				}
			}

		}
	}

	public static String getUserInput() throws IOException {
		return scanner.nextLine();
	}

	public static boolean isNumberLess25(String s) {
		int n = 0;
		try {
			n = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		if (n < 1 || n > 25) {
			return false;
		}
		return true;
	}

}

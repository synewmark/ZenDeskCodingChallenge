# ZenDeskCodingChallenge

Code is written in Java 16 and makes use of Maven for package management. To generate a runnable jar call mvn clean compile assembly:single or use the packaged jar artifact in artifacts/compiledjars. Entry point for code is frontend.TicketPrinter.main(). It’s main method accepts a series of space separated strings. There are 3 mandatory arguments and 2 optional arguments in that order. The 3 mandatory arguments are: 1. The subdomain of the Zen account. 2. The email address associated with the account 3. The pw of the account. The two optional arguments are the SortOrder and SortBy of the tickets. The two optional params are not case sensitive See https://developer.zendesk.com/api-reference/ticketing/tickets/tickets/#sorting for acceptable sorting orders.


Example run: java -jar ZenDeskCodingChallenge.jar {subdomain} {email} {pw} created_at desc


The code is centered around a value object model.Ticket which represents the json returned from the get requests. However, actually constructing the Ticket object is the responsibility of model.TicketParser which uses Google GSON for deserialization under the hood. Requests to the server are managed by backend.TicketHandler which uses cursor pagination to generate arrays of Ticket instances in batches of 25. Actually displaying the data and showing user inputs is done by frontend.TicketPrinter. 


Integral to my design choices was the simple encapsulation of how the data should actually be displayed. To that end, there are just two methods responsible for converting model.Ticket to a String .toString and ..toSimpleString. Restructuring how the data is presented is as simple as changing those two methods.

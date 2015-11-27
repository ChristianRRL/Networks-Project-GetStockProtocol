# Networks-Project-GetStockProtocol

##Project

This page describes the project for [Computer Networks](http://www.cse.usf.edu/~christen/class2/class2.html) for fall 2015.
**The class standards page is [here](http://www.cse.usf.edu/~christen/class2/standard2.html). Note that there may be posted requirements on the class standards page that supersede the requirements listed below. Some content on the class standards page is password protected, the id and password will be shared in class.**


Retrieving data from a server is typically done using HTTP. For just accessing a few bytes of data (say, a stock quote) this is very high in overhead for bandwidth used. HTTP is flexible in allowing for different types of data (HTML, image, etc.) to be downloaded. What if data type and format are predefined? Can you come-up with a light weight protocol that would consume less bandwidth (than HTTP)?
For your semester project you will develop a standard for an application layer protocol for get stock values from a server using UDP and the least possible overhead. You will implement the protocol in a simple server and client implementation using sockets.

For this document, we will refer to this new service as GetStock and the new application layer protocol as GetStock protocol.

###Definitions

Key definitions for GetStock are:
**Deliver** - The condition of a message sent being received by the intended recipient.
**Registered User** - A user that has registered with the GetStock server. Only registered users can use the GetStock service (that is, receive stock values).
**Stock name** - A string upton 16 character containing the NASDAQ stock symbol for a publicly traded company (for example, AAPL for Apple, CSCO for Ciso, and so on).
**Stock value** - The current share value in dollars and cents (for example $28.42 for CSCO).
**User** - A human user of GetStock.
**User name** - An alphanumeric (ASCII) identifier upto 32 characters for a user associated with a given User.

###Assumptions

The assumptions for GetStock are:
1. May only use UDP (TCP and HTTP may not be used).
2. The server and client are reachable from each other.
3. The GetStock server contains a table of stock names and values. An example table is [stockfile.txt](http://www.cse.usf.edu/~christen/class2/stockfile.txt).

###Requirements

The requirements that GetStock must meet are:
1. A user must be able to register with the server using a user name.
  a. If a user attempts to register with a user name already registered, then a suitable error message must be returned.
2. A user must be able to unregister with the server using their user name.
3. A registered user must be able to reliably get a single or multiple stock values from the server.
4. If a registered user requests a stock value for a stock name that does not exist in the server stock table then a suitable error message must be returned.
5. If an unregistered user requests a stock value then a suitable error message must be returned.
6. The GetStock protocol must use the least possible bandwidth within reasonable means.

###Developing the standard

The first step is to develop a protocol standard for the GetStock protocol. We will do this as an entire class. One class session will be devoted to a "standards meeting". Students can volunteer to present their ideas for the standard. One pair of students will be selected as the standard editors. Out of this effort will come an RFC-like document that describes the GetStock protocol that everyone in the class will implement. At the end of the semester, submitted implementations will be graded against the standard. Thus, it will be in everyone's interest that the standard be "good" (i.e., correct, complete, concise, unambiguous, and as **simple as possible to meet all requirements**). In the process of developing the standard, the requirements may be updated if they are incorrect, incomplete, or ambiguous in any way.
We will discuss standards creation in class. We (that is, the entire class) will also establish the deadline for the completion of the standard.

###Project deliverables

Each team of two students will deliver the following:
1. A working and standards compliant GetStock server and client that can run on Windows and/or Linux and/or Android.
2. Full source code in buildable for for their GetStock server and client.
3. A report containing:
  *Wireshark (or other) traces showing compliance with the GetStock protocol standard
  *A description of deficiencies found in the standard and suggestions for improvements. Two kinds of deficiencies may be noted, 1) those related to the class standard, and 2) those related to the requirements.
  *A discussion on how the GetStock protocol could be improved to consume even less bandwidth.

###Project grading

Project grading is described below. The base grade of 100 is as follows:
*90 points - Meets all requirements to be demonstrated "live" to the TA as described below.
*10 points - Report as described above.

Additional grade points can be earned as follows:
*5 points extra - Presentation in the class "standards meeting" on October 22nd.
*5 points extra - Serving as standards editor
*5 points - Showing client interoperability with at least 5 other server implementations (interoperability testing to be defined)

###Notes on project grading

1. You will demo your implementation to the TA at the end of the semester for a grade. The TA will have a scripted sequence of stock values to get and other actions (e.g., temporary disconnect) to perform. All stock values will come from this table -- stockfile.txt (thus it is suggested you use this table in your server implementation). The TA may wireshark your messages to ensure correct (to the standard) message syntax and semantics.
2. You will be allowed three attempts for your demo in a single maximum 30 minute period. A program "crash" (a situation where the program needs to be re-started) will result in 10 points off the possible maximum. Any violations of the standard will cost points off. Finally, failure to comply with class code style guidelines (see here) may result in points off.
3. A test script will be defined as the project progresses. The script will be made available for everyone to test their own implementation before a final test for a grade.
4. Early ship bonus is 110% multiplier for delivery on 11/30/15, 105% multiplier for 12/02/15, and 100% multiplier for 12/04/15 (due date). The last time of delivery on each date is 5pm. **No late submissions will be accepted - the TA doing the grading will "go home" at 5pm on my instructions**. A sign-up sheet for grading slots will be made available in class.

###Remarks

Some remarks for the project are:
1. If the requirements are incomplete or inconsistent, fix them. Send the fixed requirements to me and I will post them for the entire class to review.
2. This is not a user interface class, so the simplest possible command prompt interface is sufficient. The requirements, nor the protocol standard to be developed in class, will make any statements on the user interface.
3. This is not a database class, so the simplest possible linear search in the stock table is sufficient. The requirement, nor the protocol standard to be developed in class, will make any statements on table look-up.
4. You may use open source code if appropriate. Any code that is not yours must be clearly marked as such and all open source licence agreements must be followed.
5. You may choose to work in any language that you think is best suited for this project. The use of "C" is recommended.
6. You may work together within your team of two students. Submitted code (between teams) will be compared for copied code. Copying code is cheating - cheaters will earn (at minimum) a zero in the project and (at most) an FF in the class.
7. If and when in doubt, ask a question.
8. Have fun! In past semesters students who have been actively involved in the full process have had a great time, and have been able to list their experience on their resumes -- leadership, team work, design, and etc. All "good stuff". As with all things, this project is what you make of it .
Last update on October 15, 2015

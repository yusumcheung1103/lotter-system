It is a demo project to build a simple lotter system, and now is set to draw in every 5 seconds

API Structure:

1. /lotter/purchase/{nameOfBuyer}
  it is a post request, which requires buyer name as input and returns a ticket with unique ticket number

2. /lotter/draw
  it is scheduled to be called in every x seconds, and it will return the winner of each round. After a round, all tickets will be reset

3. /lotter/checkContestantStatus/{ticketNumber}
  it is a post request, which requires contestant tickectNumber, and it will check contestant's ticket win or lose this round

4. /lotter/winner
  it returns the winner of each round

5. /lotter/reset
  it manually reset the round of lottery 

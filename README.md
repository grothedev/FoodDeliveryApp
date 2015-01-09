FoodDeliveryApp
===============

see <a href = "http://grothetr.no-ip.org/android/food/food_delivery_app.pdf">this pdf</a> for info
<br>
<a href = "http://grothetr.no-ip.org/android/food/auth.png">this flowchart diagram</a> describes the authentication process

<h2>Current Status</h2>

  Initial setup activity adds a new user to the database after recieving google auth token and asking the user permission to access basic google profile info. 

<h3>Security Issues</h3>

  Currently the user is added to the database by making an http post request to add_user.php, giving just the name and email. there needs to be a way to verify the request is coming from a valid client. 
  A possible solution: the php script gets an auth token to compare with the one from client. 
  
  Need to figure out best way to sanitize input
  
  

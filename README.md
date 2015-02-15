FoodDeliveryApp
===============

see <a href = "http://grothetr.no-ip.org/android/food/food_delivery_app.pdf">this pdf</a> for info
<br>
<a href = "http://grothetr.no-ip.org/android/food/auth.png">this flowchart diagram</a> describes the authentication process

<h2>Current Status</h2>

  working on the business list screen

<h3>Security Issues</h3>

  Currently the user is added to the database by making an http post request to add_user.php, giving just the name and email. there needs to be a way to verify the request is coming from a valid client. 
  A possible solution: the php script gets an auth token to compare with the one from client. 
  Also need to use https or ssl instead of http
  
  
<h3>PHP Calls</h3>
  (running on home server for now)
  
  http://96.42.75.21/android/food/db/
  
  add_user.php POST name,email  (increments id)
  
  make_deliverer.php POST id (makes user with id a deliverer)
  
  get_user_email.php GET id (returns email of user id)
  
  num_users.php (returns num users)
  
  get_id_of_email.php GET email (returns id of email)
    
  update_user_name.php POST id,name 
  
  update_user_location.php POST id, latitude, longitude, token 
<h3><a href = "http://grothetr.no-ip.org/android/food/builds/">APKs</a></h3>
  

  

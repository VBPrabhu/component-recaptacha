<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <script src='https://www.google.com/recaptcha/api.js'></script>
	<title>Reusable Recaptach Component</title>
</head>
<body>
	<h1>${title}</h1>
         
     <form method="POST" action="scenario-1" class="form-signin">
        <h2 class="form-heading">#1- Recaptach Success Scenario</h2>

         <div class="g-recaptcha" data-sitekey="6LfUFGEUAAAAAOhFKcCONTqsbT5Pk2M-kKn6nVho"> </div>
  		<button type="submit" class="btn btn-primary">Submit</button>

    </form> </br></br>
    
     <form method="POST" action="scenario-2" class="form-signin">
        <h2 class="form-heading">#2- Recaptach Failure Scenario --> "error-codes":["missing-input-response","missing-input-secret"]</h2>
        
         <div class="g-recaptcha" data-sitekey="6LfUFGEUAAAAAOhFKcCONTqsbT5Pk2M-kKn6nVho"> </div>
  		<button type="submit" class="btn btn-primary">Submit</button>

    </form>
</body>
</html>
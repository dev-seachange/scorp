# scorp
Repo for next version of Sea Change Online Roleplay software

To create the database and run and test the web service:

1.) Create a MySQL database named "scorp" to connect to.
	Note that the default username is root and password is blank.
	It is suggested that you change these.
1B.) Download a local copy of this repo to your computer.
One method, if you wish to modify the code, is to first fork this repo. 
Then use a command prompt (or egit) to clone the forked repo to your local drive: git clone forked-repo-name. 
1C.) Then import the project into Eclipse. 
One method is File->Import->General->ProjectsFromFolderOrArchive->Next. 
Then select Directory, and choose the scorp directory. 

2.) Copy application.properties.Template to application.properties

3.) Edit application.properties to have the username and password for your MySQL database.

4.) If using Eclipse, run the SCorpApplication.java file as a Spring Boot App (or a Java Application). 
Expect to see output in the console window.  
This may take over 30 seconds to begin.  
After the console output stops, check the scorp database in MySQL and verify that various tables, such as actor, have been created. If these tables exist the application has sucessfully started. 

5.) Test the web service. The web service may be tested most easily by issuing a get request at localhost:8080/rest/{pojo-name} where pojo is any implemented domain class such as "actor" or "phase".  
	a.) Using an external web browser.  This is the easiest way to test the service but does not allow PUT or POST 
	requests.  Type localhost:8080/rest/actor in the browser.  If you get a pair of braces with no body ("[]") the
	request has sucessfully indicated that their are no actors. You may most easily create a new actor by typing 
	a new record directly into MySQL.  After creating a new record you should see the json for the actor in 
	the web browser after resubmitting the localhost:8080/rest/actor request.
	b.) Using an Eclipse internal web browser requires that http:// be added to the front of the URL.
	c.) You may alternatively use a web traffic viewing tool such as Postman. This will allow you to issue 
	any type of http request (PUT, POST, etc.), set headers values and view web traffic. 
	d.) Instead of running the SCorpApplication.java file as a Spring Boot App, or a Java Application, you may 
	configure it as a Web Application on a server (such as Tomcat), then start the server.  After this /scorp 
	must be included in the URL (aka http://localhost:8080/scorp/rest/actor) to access a web service. 

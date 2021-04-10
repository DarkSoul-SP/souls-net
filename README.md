<h1><strong>SoulsNet — it is a <b> my demo RESTful</b> java project with <b>websockets</b></strong></h1><br>

<strong>Beta version has already deployed on AWS beanstalk and you can try it <br>
    <a href="http://soulsnet-prod.eba-vueny8jp.eu-central-1.elasticbeanstalk.com" target="_blank" style="text-decoration:none;">
	    <em>http://soulsnet-prod.eba-vueny8jp.eu-central-1.elasticbeanstalk.com</em> 
    </a> 
</strong> <br>
For test you need to have your own google account.

**Client-side** — Vue.js, <b>Thymeleaf</b> as templating engine + SockJS for work with WebSockets.
 
<b>Yarn</b> as package manager and <b>Webpack</b> as build system.

**Server-side** — <b>Spring (Boot, Data, MVC, Security)</b> + STOMP for work with WebSockets and oAuth2 protocols.

<b>Gradle</b> for work with dependencies and packaging.

Database <b>Postgres</b>.  

<h2><strong>Screenshots</strong></h2>
<div>
	<h3>Login page with a request to authorize through google</h3>
	<img src="/screenshots/auth.PNG" alt="login_page_picture"/>
	<h3>Google authorization</h3>
	<img src="/screenshots/googleAuth.PNG" alt="googleAuth_page_picture"/>
</div>
<div>
	<h3>Main page (wall) with all posts (own + from subscriptions)</h3>
	<img src="/screenshots/main.PNG" alt="main_page_picture"/>
	<h3>Navbar with links to wall, profile, logout and field for searching new contacts in center</h3>
	<img src="/screenshots/navbar.PNG" alt="navbar_page_picture"/>
</div>
<div>
	<h3>Profile page</h3>
	<img src="/screenshots/profile.PNG" alt="profile_page_picture"/>
</div>
<div>
	<h3>Page with subscribers where you can approve/dismiss their requests</h3>
	<img src="/screenshots/subscribers.PNG" alt="subscribers_page_picture"/>
	<h3>Page of other people's subscribers</h3>
	<img src="/screenshots/notMySubscribers.PNG" alt="not_own_subscribers_page_picture"/>
</div>
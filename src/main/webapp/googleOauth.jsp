<%@page
	import="io.robusta.classify.testingOauth.google.api.GoogleAuthHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
    
<%-- <jsp:include page="/servlet/TestServlet" flush="true" > --%>
<%--    <jsp:param name="username" value="Smith" /> --%>
<%--    <jsp:param name="userempno" value="9876" /> --%>
<%-- </jsp:include> --%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Google OAuth 2.0 v1 Demo</title>
</head>
<body>
	<div class="oauthDemo">
		<%
			/*
			 * The GoogleAuthHelper handles all the heavy lifting, and contains all "secrets"
			 * required for constructing a google login url.
			 */
			final GoogleAuthHelper helper = new GoogleAuthHelper();

			if (request.getParameter("code") == null || request.getParameter("state") == null) {

				/*
				 * initial visit to the page
				 */
				out.println("<a href='" + helper.buildLoginUrl() + "'>log in with google</a>");

				/*
				 * set the secure state token in session to be able to track what we sent to google
				 */
				session.setAttribute("state", helper.getStateToken());

			} else if (request.getParameter("code") != null && request.getParameter("state") != null
					&& request.getParameter("state").equals(session.getAttribute("state"))) {

				session.removeAttribute("state");

				out.println("<pre>");
				/*
				 * Executes after google redirects to the callback url.
				 * Please note that the state request parameter is for convenience to differentiate
				 * between authentication methods (ex. facebook oauth, google oauth, twitter, in-house).
				 * 
				 * GoogleAuthHelper()#getUserInfoJson(String) method returns a String containing
				 * the json representation of the authenticated user's information. 
				 * At this point you should parse and persist the info.
				 */

				out.println(helper.getUserInfoJson(request.getParameter("code")));
                
				out.println("</pre>");
			}
		%>
	</div>
</body>
</html>

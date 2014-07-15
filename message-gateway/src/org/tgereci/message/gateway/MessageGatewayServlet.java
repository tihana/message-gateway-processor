package org.tgereci.message.gateway;

import java.io.IOException;

import javax.jms.JMSException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.tgereci.message.model.base.MessageBase;

/**
 * Servlet implementation class MessageGatewayServlet.
 */
@WebServlet(description = "Message Gateway", urlPatterns = { "/submitMessage" })
public class MessageGatewayServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -844665497622941944L;

	/** The Constant log. */
	static final Logger log = LogManager.getLogger(MessageGatewayServlet.class);

	/**
	 * The Constructor.
	 */
	public MessageGatewayServlet() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String json = request.getParameter("message");
		boolean redirect = Boolean.parseBoolean(request
				.getParameter("redirect"));
		MessageBase message = MessageParser.deserializeMessage(json);
		if (message != null) {
			// add message to queue
			try {
				MessageSender.getInstance().addMessageToQueue(message);
				log.debug("Message added to queue: " + json);
				response.setStatus(HttpServletResponse.SC_OK);
			} catch (JMSException e) {
				log.debug("Exception while adding message to queue", e);
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		if (redirect) {
			// redirect if such parameter is received in request
			response.sendRedirect("");
		}
	}

}

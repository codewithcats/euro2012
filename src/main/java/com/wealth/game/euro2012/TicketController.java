package com.wealth.game.euro2012;



import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.wealth.game.euro2012.data.Ticket;
import com.wealth.game.euro2012.data.TicketRepository;

@Controller
public class TicketController {

	@Autowired private TicketRepository ticketRepository;
	
	@RequestMapping(value="/tickets.json", method=RequestMethod.GET)
	@ResponseBody
	public String all(){
		Iterator<Ticket> it = ticketRepository.findAll().iterator();
		List<Ticket> tickets = new LinkedList<Ticket>();
		while(it.hasNext()){
			tickets.add(it.next());
		}
		return (new Gson()).toJson(tickets);
	}
	
	@RequestMapping(value="/ticket.json",method=RequestMethod.POST)
	@ResponseBody
	public String addTicket(@ModelAttribute Ticket ticket){
		ticket = this.ticketRepository.save(ticket);
		return (new Gson()).toJson(ticket);
	}
	
	@RequestMapping(value="/ticket.json",method=RequestMethod.PUT)
	@ResponseBody
	public String saveTicket(@ModelAttribute Ticket ticket){
		ticket = this.ticketRepository.save(ticket);
		return (new Gson()).toJson(ticket);
	}
	
	@RequestMapping(value="/ticket.json",method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteTicket(@ModelAttribute Ticket ticket){
		this.ticketRepository.delete(ticket);
		return (new Gson()).toJson(ticket);
	}
	
	
	
}

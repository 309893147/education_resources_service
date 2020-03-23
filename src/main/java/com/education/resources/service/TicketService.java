package com.education.resources.service;

import com.education.resources.bean.entity.Ticket;
import com.education.resources.bean.entity.config.DingConfig;
import com.education.resources.bean.entity.config.SystemConfig;
import com.education.resources.bean.from.PageForm;
import com.education.resources.bean.pojo.event.DingMessageEvent;
import com.education.resources.datasource.repository.TicketRepository;
import com.education.resources.service.config.ConfigService;
import com.education.resources.util.jpa.SpecificationUtil;
import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Specifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TicketService extends BaseService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    ConfigService configService;

    public Page<Ticket> getTicketPage(Ticket ticket,PageForm pageForm){
        PredicateBuilder<Ticket> spec = SpecificationUtil.filter(ticket, pageForm);
        return ticketRepository.findAll(spec.build(),pageForm.pageRequest());
    }

    public Page<Ticket> getTickets(Ticket.Status status, PageForm pageForm) {
        PredicateBuilder<Ticket> spec = Specifications.<Ticket>and().eq("status", status);
        spec.eq("userId", getCurrentUser().getId());
        return ticketRepository.findAll(spec.build(), pageForm.pageRequest());
    }

    public Ticket getTicketDetail(Integer id) {
        return ticketRepository.findItemById(id);
    }

    public Ticket saveTicket(Ticket ticket) {
        Integer id = getCurrentUser().getId();
        ticket.setUserId(id);
        ticket.setStatus(Ticket.Status.CREATED);
        sendEvent(DingMessageEvent.builder().content(ticket.getUser().getNickName()+"发起了资源求助："+ticket.getContent()).build());
        return ticketRepository.save(ticket);

    }

    public Ticket  updateTicket(Ticket ticket){
        ticket.setManagerId(getCurrentManager().getId());
        return ticketRepository.save(ticket);
    }

}

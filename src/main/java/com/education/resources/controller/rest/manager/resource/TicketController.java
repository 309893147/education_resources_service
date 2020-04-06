//package com.education.resources.controller.rest.manager.resource;
//
//import com.education.resources.bean.entity.Ticket;
//import com.education.resources.bean.from.PageForm;
//import com.education.resources.service.TicketService;
//import com.education.resources.util.rest.API;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/manage/ticket")
//@Api(tags = "Manager 求助")
//public class TicketController {
//
//    @Autowired
//    TicketService ticketService;
//
//    @GetMapping
//    @ApiOperation("获取求助列表")
//    public API<Page<Ticket>> getTicket(Ticket ticket, PageForm pageForm){
//        return API.ok(ticketService.getTicketPage(ticket,pageForm));
//    }
//
//    @GetMapping(value = "detail")
//    @ApiOperation("求助详情")
//    public API<Ticket>  getTicketDetail(Integer id){
//        return API.ok(ticketService.getTicketDetail(id));
//    }
//
//    @PutMapping
//    @ApiOperation("反馈求助")
//    public API<Ticket>  saveTicket(@RequestBody Ticket ticket){
//        return API.ok(ticketService.updateTicket(ticket));
//    }
//
//
//
//}
